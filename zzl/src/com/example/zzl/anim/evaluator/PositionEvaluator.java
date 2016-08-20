package com.example.zzl.anim.evaluator;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class PositionEvaluator implements TypeEvaluator<PointF>{
	
	private PointF pointF;
	
	public PositionEvaluator() {
		pointF = new PointF();
	}
	
	@Override
	public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
		
		//以正玄曲线从左边运行到右边
		
		//x轴：直线运动
		float x = 0;
		if (fraction == 0) {
			x = startValue.x;
		} else {
			x = fraction * endValue.x;
		}
		
		// sin(30x-π/2)+1  
		//y轴：正玄曲线，公式  (float) Math.sin((30 * fraction) - Math.PI/2)+1,其范围是[0,2]
		float range = (float) Math.sin((30 * fraction) - Math.PI/2)+1;
		
		//小球纵坐标+波动范围
		float y = endValue.y + range*30;
		
		pointF.x = x;
		pointF.y = y;
 
		return pointF;
	}
	
	 

	 

}
