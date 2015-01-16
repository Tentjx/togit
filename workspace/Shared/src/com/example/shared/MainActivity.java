package com.example.shared;



import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Editable;
import android.text.method.CharacterPickerDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private ImageView img_repeat;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		img_repeat=(ImageView)findViewById(R.id.imageView1);
		img_repeat.setOnClickListener(this);
		 textView=(TextView) findViewById(R.id.textView1);
		Button button=(Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		
	final	TextView textView2=(TextView) findViewById(R.id.textView2);
	textView2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			String tagString=	textView2.getTag().toString();
			if (tagString.equals("up")) {
				textView2.setTextColor(Color.BLACK);
				textView2.setTag("down");
			}else if (tagString.equals("down")) {
				textView2.setTextColor(Color.GREEN);
				textView2.setTag("up");
			}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
       		int id=view.getId();
       		if (id==R.id.imageView1) {
    			String repeat_tag=img_repeat.getTag().toString();
    	        SharedPreferences   repeatSharedPreferences=getSharedPreferences("repeat_state", Activity.MODE_PRIVATE);

    			SharedPreferences.Editor editor=repeatSharedPreferences.edit();
    			if ("循环全部".equals(repeat_tag)) {
    				img_repeat.setImageResource(R.drawable.ic_mp_repeat_once_btn);
    				img_repeat.setTag("单曲循环");
    				editor.putString("repeat_state", "单曲循环");
    				Toast.makeText(MainActivity.this, "单曲循环", 0).show();
        			editor.commit();

    			}else if ("单曲循环".equals(repeat_tag)) {
    				img_repeat.setImageResource(R.drawable.ic_mp_shuffle_on_btn);
    				img_repeat.setTag("随机播放");
    				editor.putString("repeat_state", "随机播放");
    				Toast.makeText(MainActivity.this, "随机播放", 0).show();
        			editor.commit();

    			}else if ("随机播放".equals(repeat_tag)) {
    				img_repeat.setImageResource(R.drawable.ic_mp_repeat_all_btn);
    				img_repeat.setTag("循环全部");  
    				editor.putString("repeat_state", "循环全部");
    				Toast.makeText(MainActivity.this, "循环全部", 0).show();
        			editor.commit();

    			}
    		}else if (id==R.id.button1) {
    			//自定义对话框
/*  	       AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	         builder.setTitle("输入框");
    	       View view2=View.inflate(this, R.layout.diaolog, null);
    	     //  builder.setMessage("你确定要输入？");
    	       builder.setView(view2);
    	     final  EditText editText=(EditText) view2.findViewById(R.id.editText1);
    	     String string=textView.getText().toString();
    	     editText.setText(string);
    	    
               builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
                 
					String  textString=editText.getText().toString();
					textView.setText(textString);
					
				}   
			});
              builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
              builder.create().show();  */
    			
    			//时间选择对话框
    /*			DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this, new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int datOfMonth) {
                    String timesString =String.format("%04d-%02d-%02d",year, monthOfYear+1,datOfMonth);
					//String time=year+"-"+timesString;
					textView.setText(timesString);

					}
				}, 2000, 9, 1);  
    			datePickerDialog.show();  */
    			//View view2=View.inflate(MainActivity.this, R.layout.chars, null);
    			CharacterPickerDialog characterPickerDialog=new CharacterPickerDialog(MainActivity.this, new View(this), null, "01234567890", false){
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					String s1=""+((Button)v).getText().toString();
    					textView.setText(s1);
                          dismiss();
    				}
    				
    			@Override
    			public void onItemClick(AdapterView parent, View view,
    					int position, long id) {
    				// TODO Auto-generated method stub
                   Toast.makeText(MainActivity.this, position+"----"+id, 0).show();
    			}
    			};
    			
    			characterPickerDialog.show();
    		}
	}

}
