package com.example.zzl.font;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zzl.R;

public class FontMainActivity extends  Activity implements MyRelativeLayout.MyRelativeTouchCallBack {
    private MyRelativeLayout rela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.font_roate);
        initUI();
    }

    public void initUI(){
        rela = (MyRelativeLayout) findViewById(R.id.id_rela);
        rela.setMyRelativeTouchCallBack(this);
    }

    @Override
    public void touchMoveCallBack(int direction) {
        if(direction == AppConstants.MOVE_LEFT){
            Toast.makeText(FontMainActivity.this, "你在向左滑动！", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(FontMainActivity.this, "你在向右滑动！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTextViewMoving(TextView textView) {

    }

    @Override
    public void onTextViewMovingDone() {
        Toast.makeText(FontMainActivity.this, "标签TextView滑动完毕！", Toast.LENGTH_SHORT).show();
    }

    public void btnClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注意:");
        builder.setMessage("一、点击自定义View可以创建标签，点击标签滑动可以改变标签位置；\n二、在View上进行两指缩放旋转可以改变标签的方向和大小，当有多个标签TextView存在时，两指缩放和旋转会自动寻找离两指中心点最近的TextView标签进行操作！\n三、在自定义上可识别左滑和右滑操作；\n四、有需要的伙伴可以自定义添加新功能！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
