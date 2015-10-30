package com.example.customui.activity;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.customui.BaseActivity;
import com.example.customui.R;
import com.example.customui.adapter.CommonAdapter;
import com.example.customui.adapter.ViewHolder;
import com.example.customui.util.DensityUtil;
import com.example.customui.util.Logger;
import com.example.customui.view.SwipeListView;
import com.example.customui.view.SwipeListView.RemoveItemListViewListener;

public class SwipeListViewActivity extends BaseActivity {

	private SwipeListView mListView;
	private List<String> dataSourceList;
	private CommonAdapter<String> mAdapter;
	private int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.swipe_listview_main);
		mListView = (SwipeListView) findViewById(R.id.lv_data);
		screenWidth = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		dataSourceList = new ArrayList<String>();
		
		for (int i = 0; i < 20; i++) {
			dataSourceList.add("item "+i);
		}

		mAdapter = new CommonAdapter<String>(this, dataSourceList, R.layout.list_view_item) {
			@Override
			public void convert(ViewHolder helper, String item) {
				helper.setText(R.id.list_item, item);
			}
		};
		 
		mListView.setAdapter(mAdapter);
		
		mListView.setRemoveItemListViewListener(new RemoveItemListViewListener() {
			private TextView textView;

			@Override
			public void removeItem(int pos) {
				position = pos;
			}

			@Override
			public void scroll(float scrollX, View view) {
				if (textView==null) {
					textView = (TextView) view.findViewById(R.id.tv_show_info);
				} 
				int fontSize=1;
				float alpha=0.0f;
				
				if (scrollX>screenWidth/3) {
					alpha=1.0f;
					fontSize=maxFontSize;
				}else {
					alpha = scrollX/(screenWidth/3);
					fontSize = (int) (alpha * maxFontSize);
				}
				textView.setAlpha(alpha);
				textView.setTextSize(fontSize);
			}

			@Override
			public void scrollFinish() {
				textView=null;
				if (position>=0) {
					Logger.getLogger().i("----------------------->É¾³ýµÄÎ»ÖÃ£º "+position);
					dataSourceList.remove(position);
					mAdapter.notifyDataSetChanged();
					position= -1;
				}
			}
		 
		});
	}
	
	private int position=-1;
	 
	private int maxFontSize=22;
	
}
