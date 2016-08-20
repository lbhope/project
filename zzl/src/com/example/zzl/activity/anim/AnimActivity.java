package com.example.zzl.activity.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zzl.CustomViewActivity;
import com.example.zzl.R;
import com.example.zzl.utils.ToastUtils;
import com.example.zzl.utils.ToolUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AnimActivity extends Activity{

	@ViewInject(R.id.btn_anim)
	Button btn_anim;
	@ViewInject(R.id.btn_value_anim)
	Button btn_value_anim;
	@ViewInject(R.id.iv_img)
	ImageView iv_img;
	private int width;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anim_main);
		ViewUtils.inject(this);
		width = ToolUtils.getDisplayMetrics(this).widthPixels;
		
		btn_anim.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				translate();
//				roate();
//				scale();
//				alpha();
			}
		});
		
		btn_value_anim.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(AnimActivity.this,CustomViewActivity.class);
				mIntent.putExtra("layout", R.layout.anim_view_move_circle);
				startActivity(mIntent);
			}
		});
	}
	
	
	
	/**
	 * 图片向右移动，移动距离大于0向右，小于0向左移动
	 * 效果：向右移动到边界
	 */
	public void translate(){
		float currentX = iv_img.getTranslationX();
		int imgWidth = iv_img.getWidth();
		int distance = width/2-imgWidth/2;
		ObjectAnimator animator = ObjectAnimator.ofFloat(iv_img, "translationX", currentX,distance,currentX,distance/2,currentX);
		animator.setDuration(3000);
		animator.setInterpolator(new BounceInterpolator());//设置插值器，具有反弹效果
		animator.start();
	}
	
	/**
	 * 先Y轴从180度到0，在X轴从0到180度
	 */
	public void roate(){
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator anim = ObjectAnimator.ofFloat(iv_img, "rotationX", 0f,180f);
		anim.setDuration(1000);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(iv_img, "rotationY",180f, 0f);
		anim2.setDuration(1000);
		set.play(anim).before(anim2);  
		set.start();
	}
	/**
	 * 缩放动画，X和Y同时缩放
	 */
	public void scale(){
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator animator = ObjectAnimator.ofFloat(iv_img, "scaleX",1f, 0f, 1f);
		animator.setDuration(3000);
		ObjectAnimator animator2 = ObjectAnimator.ofFloat(iv_img, "scaleY",1f, 0f, 1f);
		animator2.setDuration(3000);
		set.play(animator).with(animator2);
		set.start();
	}
	/**
	 * alpha动画
	 */
	public void alpha(){
		ObjectAnimator animator = ObjectAnimator.ofFloat(iv_img, "alpha",1f, 0f, 1f);
		animator.setDuration(3000);
		animator.start();
	}
	
	/**
	 * 自定义插值器
	 * 抛物线，抛物口向上
	 */
	public class ParabolaInterpolator implements Interpolator {

		@Override
		public float getInterpolation(float input) {
			return (2 * input - 1) * (2 * input - 1);
		}

	}
	 
}
