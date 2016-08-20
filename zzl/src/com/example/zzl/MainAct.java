package com.example.zzl;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zzl.activity.anim.AnimActivity;
import com.example.zzl.activity.scroll.ZoomActivity;
import com.example.zzl.font.FontMainActivity;

public class MainAct extends Activity{
	
	public ArrayList<Class<?>> mActivities = new ArrayList<Class<?>>();
	private ArrayList<String> mArrayList = new ArrayList<String>();
	private HashMap<String, Integer> mLayoutHashMap = new HashMap<String, Integer>();
	private ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_act_main);
		mListView = (ListView) findViewById(R.id.listview);
		
		initData();
		
		mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, mArrayList));
		
		setEvent();
	}

	

	private void initData() {
		mActivities.add(MainActivity.class);
		mArrayList.add("ViewPager 3D 画廊");
		mActivities.add(FontMainActivity.class);
		mArrayList.add("文字旋转、缩放");
		mActivities.add(CustomViewActivity.class);
		mArrayList.add("百分比布局");
		mLayoutHashMap.put("百分比布局", R.layout.parcent_layout);
		mActivities.add(ZoomActivity.class);
		mArrayList.add("仿QQ空间下拉背景图片放大效果");
		mActivities.add(AnimActivity.class);
		mArrayList.add("属性动画介绍");
		
		
	}
	
	
	private void setEvent() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					System.out.println(" pos : "+position);
					Class<Activity> clazz = (Class<Activity>) mActivities.get(position);
					if (clazz.getSimpleName().contains("CustomViewActivity")) {
						Intent mIntent = new Intent(MainAct.this,CustomViewActivity.class);
						mIntent.putExtra("layout", mLayoutHashMap.get(mArrayList.get(position)));
						startActivity(mIntent);
					}else {
						Intent mIntent = new Intent(MainAct.this,mActivities.get(position));
						startActivity(mIntent);
					}
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
	}
	
}
