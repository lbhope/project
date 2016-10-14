package com.example.zzl.activity.jni;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.zzl.R;
import com.example.zzl.jni.BsDiffPatch;
import com.example.zzl.jni.DynamicLoad;
import com.example.zzl.jni.JChild;
import com.example.zzl.utils.ApkUtils;
import com.example.zzl.utils.AssetsUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * ndk开发
 * 
 * @author libin
 * 
 */
public class JniActivity extends Activity implements OnClickListener{

	private static final String TAG = "JniActivity";
	private JChild jChild;
	static {
//		System.loadLibrary("bsdiff");
//		System.loadLibrary("bspatch");
		System.loadLibrary("jni");
	}

	static void log(String msg) {
		Log.i(TAG, msg + "");
	}

	@ViewInject(R.id.btn_simple)
	Button btn_simple;
	@ViewInject(R.id.btn_create_patch)
	Button btn_create_patch;
	@ViewInject(R.id.btn_unit)
	Button btn_unit;
	@ViewInject(R.id.btn_dynamic)
	Button btn_dynamic;
	@ViewInject(R.id.btn_thread)
	Button btn_thread;

	private static String SDCARD = "";

	private String oldFileName = "zbsdiff_v1.0.apk";
	private String newFileName = "zbsdiff_v3.0.apk";
	private String patchFileName = "zbsdiff.patch";
	private String newDiffFileName = "zbsdiff_v3.0_new.apk";

	private File oldFile;
	private File newFile;
	private File patchFile;
	private File newDiffFile;
	private boolean diffFile = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jni_main);
		ViewUtils.inject(this);

		jChild = new JChild();

		SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();

		System.out.println(SDCARD);
		
		

		oldFile = new File(SDCARD + File.separatorChar + oldFileName);
		newFile = new File(SDCARD + File.separatorChar + newFileName);
		patchFile = new File(SDCARD + File.separatorChar + patchFileName);
		newDiffFile = new File(SDCARD + File.separatorChar + newDiffFileName);
		
		deletes(new File[]{oldFile,newFile,patchFile,newDiffFile});

		copy();

		btn_simple.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				jChild.print(jChild);
				btn_simple.setEnabled(false);
			}
		});
		btn_create_patch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/**
				 * 
				 * @param oldfile
				 *            旧文件
				 * @param newfile
				 *            新文件
				 * @param patchfile
				 *            差分文件
				 */
				new Task().execute(true);
			}
		});
		// 合并
		btn_unit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Task().execute(false);

			}
		});
		
		btn_dynamic.setOnClickListener(this);
		btn_thread.setOnClickListener(this);
	}

	class Task extends AsyncTask<Boolean, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Boolean... params) {
			Log.i("jni", oldFile.getAbsolutePath());
			if (params[0]) {
				if (patchFile.exists()) {
					patchFile.delete();
				}
				try {
					patchFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Log.i("jni", patchFile.getAbsolutePath() + " 差分包");
				BsDiffPatch.diff(oldFile.getAbsolutePath(),
						newFile.getAbsolutePath(), patchFile.getAbsolutePath());
				Log.i("jni", patchFile.getAbsolutePath() + " 差分包11");
				diffFile = true;
				return true;
			} else {
				BsDiffPatch.patch(oldFile.getAbsolutePath(),
						newDiffFile.getAbsolutePath(),
						patchFile.getAbsolutePath());
				diffFile = false;
				return true;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				if (diffFile) {
					Toast.makeText(JniActivity.this, "差分包生成成功", 1).show();
				} else {
					//合并
					ApkUtils.installApk(JniActivity.this,newDiffFile.getAbsolutePath());
				}
			} else {
				Toast.makeText(JniActivity.this, "失败", 1).show();
			}
		}

	}

	private void copy() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("copy start ... ");
					AssetsUtils.copy(JniActivity.this, "zbsdiff_v1.0.apk",SDCARD);
					AssetsUtils.copy(JniActivity.this, "zbsdiff_v3.0.apk", SDCARD);
					System.out.println("copy finish ... ");
					
					
					Log.i("jni", SDCARD + " len: " + patchFile.length());
					Log.i("jni", "" + oldFile.exists() + " n:" + newFile.exists() + " p: "
							+ patchFile.exists());
					Log.i("jni", "" + oldFile.length() + " n:" + newFile.length() + " p: "
							+ patchFile.length());
					Log.i("jni", "" + oldFile.length() + " n:" + newDiffFile.length()
							+ " p: " + patchFile.length());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	
	private void deletes(File[] files){
		for (int i = 0; i < files.length; i++) {
			if (files[i].exists()) {
				files[i].delete();
			}
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_dynamic:
			DynamicLoad.init();
			DynamicLoad.dynamic();
			break;
		case R.id.btn_thread:
			startActivity(new Intent(this,ThreadAcitivity.class));
			break;
		default:
			break;
		}
	}
}
