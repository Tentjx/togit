package com.jk.activity;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ALLActivity extends Activity {
	public static List<Activity> activityList = new ArrayList<Activity>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activityList.add(this);
	}

	 
}
