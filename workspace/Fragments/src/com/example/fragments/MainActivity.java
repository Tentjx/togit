package com.example.fragments;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity  implements OnClickListener{

	private Button btn;
	private TextView txet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btn=(Button)findViewById(R.id.button1);
		btn.setOnClickListener(this);
		txet=(TextView)findViewById(R.id.tv_result);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
         int id=arg0.getId();
         if (id==R.id.button1) {
			Intent intent=new Intent(MainActivity.this,ResultActivity.class);
			Bundle bundle=new Bundle();
			String str1="aaaaaa";
			bundle.putString("str1", str1);
			intent.putExtras(bundle);
			startActivityForResult(intent, 0);
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		System.out.println("1111111111111111");
       switch (resultCode) {
	case RESULT_OK:
		Bundle bundle=data.getExtras();
		String backResult1=bundle.getString("str1");

		String backResult=bundle.getString("str2");
		txet.setText(backResult);
		txet.append(backResult1);
		System.out.println("222222222");

		break;

	default:
		break;
	}
       
		System.out.println("3333333");

	}

}
