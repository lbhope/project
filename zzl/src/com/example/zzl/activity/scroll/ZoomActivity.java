package com.example.zzl.activity.scroll;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.example.zzl.R;
import com.example.zzl.scroll.ZoomListView;
import com.example.zzl.scroll.ZoomListView.ScrollChangeList;

public class ZoomActivity extends Activity{

	private ArrayList<String> mArrayList = new ArrayList<String>();
	private ZoomListView mListView;
	private LinearLayout ll_title_bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.scroll_zoom_main);
		mListView = (ZoomListView) findViewById(R.id.listview);
		ll_title_bar = (LinearLayout) findViewById(R.id.ll_title_bar);
		ll_title_bar.setBackgroundColor(Color.parseColor("#003FB1F5"));
		
		initData();
		
		mListView.setHeaderView(R.layout.scroll_zoom_header);
		mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, mArrayList));
		
//		mListView.setScrollChangeList(new ScrollChangeList() {
//			@Override
//			public void onScroll(int y, int half) {
//				Log.i("tag", "y: "+y+
//						" half: "+half);
//				if (y<0) {//向上滑动
//					if(Math.abs(y)>half && Math.abs(y)<2*half){
//						int alpha = (int) ((Math.abs(y)-half)*255.0f/half);
//						if (alpha<=15) {
//							ll_title_bar.setBackgroundColor(Color.parseColor("#"+Integer.toHexString(alpha)+"00000ff"));
//						}else {
//							ll_title_bar.setBackgroundColor(Color.parseColor("#"+Integer.toHexString(alpha)+"0000ff"));
//						}
//					}
//				}else {
//					if (y>0) {
//						ll_title_bar.setBackgroundColor(Color.parseColor("#0000ff"));
//					}
//				}
//				
//				
//			}
//		});
		
	}

	

	private void initData() {
		for (int i = 0; i < 12; i++) {
			mArrayList.add("旋转木马"+i);
		}
	}
	 
}
