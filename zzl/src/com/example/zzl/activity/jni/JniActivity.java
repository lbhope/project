package com.example.zzl.activity.jni;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.zzl.R;
import com.example.zzl.jni.JChild;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
/**
 * ndk开发
 * @author libin
 *
 */
public class JniActivity extends Activity {
	
	private static final String TAG = "JniActivity";
	private JChild jChild;
 
	static{
		 System.loadLibrary("jni");
	}
	
	static void log(String msg){
		Log.i(TAG, msg+"");
	}
	
	@ViewInject(R.id.btn_simple)
	Button btn_simple;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jni_main);
		ViewUtils.inject(this);
		
		jChild = new JChild();
		
		 

		btn_simple.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				jChild.print(jChild);
				btn_simple.setEnabled(false);
			}
		});
	}
	
	
	
	
	

}
