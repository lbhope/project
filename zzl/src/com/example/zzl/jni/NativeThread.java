package com.example.zzl.jni;

public class NativeThread {
	
	private ThreadCallback callback;
	public NativeThread(ThreadCallback callback){
		this.callback = callback;
	}
	
	/** 
	 * 初始化原生代码
	 */
	public native void nativeInit();
	/**
	 * 是否资源
	 */
	public native void nativeFree();
	
	/**
	 * 
	 * @param id 工作线程id
	 * @param iterations 工作线程循环次数 
	 */
	public native void nativeWorker(int id,int iterations);
	
	/**
	 * native回调
	 * @param msg
	 * @param callback
	 */
	public void onCallbackMsg(final String msg){
		callback.onCallbackMsg(msg);
	}
	
	/**
	 * 在native层使用POSIX线程
	 * @param id
	 * @param iterations
	 */
	public native void posixThreads(int id,int iterations);
	
	
	
	static{
		System.loadLibrary("nativethread");
	}
	
}
