package com.example.zzl.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.test.aidl.ProcessService;

public class RemoteService extends Service {
	private MyBinder binder;
	private MyConn conn;

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		binder = new MyBinder();
		if (conn == null) {
			conn = new MyConn();
		}
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		// 绑定RemoteService服务
		bindService(new Intent(RemoteService.this, LocalService.class), conn,
				Context.BIND_IMPORTANT);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	private class MyBinder extends ProcessService.Stub {

		@Override
		public String getServiceName() throws RemoteException {

			return "RemoteService";
		}

	}

	private class MyConn implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			System.out.println("connection local service success .. ");
			
		}

		//
		@Override
		public void onServiceDisconnected(ComponentName name) {
			System.out.println("disconnection local service success .. ");
			startService(new Intent(RemoteService.this,LocalService.class));
			bindService(new Intent(RemoteService.this, LocalService.class), conn,
					Context.BIND_IMPORTANT);
		}

	}
}
