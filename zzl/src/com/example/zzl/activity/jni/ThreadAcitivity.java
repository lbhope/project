package com.example.zzl.activity.jni;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zzl.R;
import com.example.zzl.jni.NativeThread;
import com.example.zzl.jni.ThreadCallback;

public class ThreadAcitivity extends Activity implements ThreadCallback{
	
	private EditText et_thread_num;
	private EditText et_exec_num;
	private Button btn_android_thread;
	private Button btn_ndk_thread;
	private TextView tv_log;
	private NativeThread nativeThread = new NativeThread(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jni_native_thread_main);
		et_thread_num = (EditText) findViewById(R.id.et_thread_num);
		et_exec_num = (EditText) findViewById(R.id.et_exec_num);
		btn_android_thread = (Button) findViewById(R.id.btn_android_thread);
		btn_ndk_thread = (Button) findViewById(R.id.btn_ndk_thread);
		tv_log = (TextView) findViewById(R.id.tv_log);
		
		nativeThread.nativeInit();
		
		btn_android_thread.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int threadnums = getNumber(et_thread_num, 0);
				int iterations = getNumber(et_exec_num, 0);
				if (threadnums>0&& iterations>0) {
					tv_log.setText("");
					startThreads(threadnums,iterations);
				}
			}
		});
		btn_ndk_thread.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int threadnums = getNumber(et_thread_num, 0);
				int iterations = getNumber(et_exec_num, 0);
				if (threadnums>0&& iterations>0) {
					tv_log.setText("");
					nativeThread.posixThreads(threadnums,iterations);
				}
			}
		});
		
		
	}
	
	
	
	
	private void startThreads(int threadnums, int iterations) {
		javaThread(threadnums,iterations);
	}
	
	/**
	 * 原生消息回调
	 * @param msg
	 */
	@Override
	public void onCallbackMsg(final String msg){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				tv_log.append(msg);
				tv_log.append("\n");
			}
		});
	}
	
	private void javaThread(int threads,final int iterations){
		for (int i = 0; i < threads; i++) {
			final int id = i;
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("threadid: "+Thread.currentThread().getId());
					nativeThread.nativeWorker(id, iterations);
				}
			});
			thread.start();
		}
	}
	
	@Override
	protected void onDestroy() {
		nativeThread.nativeFree();
		super.onDestroy();
	}
	
	private int getNumber(EditText et,int defaultVal){
		int value=0;
		try {
			value = Integer.parseInt(et.getText().toString().trim());
		} catch (Exception e) {
			value = defaultVal;
		}
		return value;
	}
	
}
