package com.example.animationdemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.imageView1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void setAlpha(View v) {
		Animation animation = AnimationUtils.loadAnimation(MainActivity.this,
				R.anim.alpha_demo);
		imageView.startAnimation(animation);
	}

	public void setScale(View v) {
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.scale_demo);
		imageView.startAnimation(animation);
	}

	public void setTranslate(View view) {
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.translate_demo);
		imageView.startAnimation(animation);
	}

	public void setRotate(View view) {
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.rotate_demo);
		imageView.startAnimation(animation);
	}

	public void setAll(View view) {
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.setall_demo);
		imageView.startAnimation(animation);
	}
}
