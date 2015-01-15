package com.example.entity;




import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;

public class ExitAlertDialog  extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Exitactivity.getInstance().addActivity(this);//退出用应
		
	}
	
	public void simple(View v){
//		AlertDialog.Builder builder = new AlertDialog.Builder(this)			
//		.setTitle("退出")	// 设置对话框标题		-
//		.setMessage("你确定退出吗？");  //提示内容
//		new ExitAlertDialog().setPositiveButton(builder);   // 为AlertDialog.Builder添加【确定】按钮
//		new ExitAlertDialog().setNegativeButton(builder)   // 为AlertDialog.Builder添加【取消】按钮
//		.create()
//		.show();
	}

	public AlertDialog.Builder setNegativeButton(AlertDialog.Builder exitApp) {		
		return exitApp.setPositiveButton("是", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//确定退出应用程序
				Exitactivity.getInstance().exit();
			}
		});
		
	}

	public AlertDialog.Builder  setPositiveButton(AlertDialog.Builder exitApp) {
		
		return exitApp.setNegativeButton("否", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				ExitAlertDialog.this.finish();    //关闭对话框
			}
		});
		
	}
}

