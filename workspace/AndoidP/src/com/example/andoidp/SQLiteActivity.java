package com.example.andoidp;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SQLiteActivity extends Activity implements OnClickListener {
	private Button createDB;
	private Button insert;
	private Button search;
	private Button delete;
	private TextView tv1;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.sqlite);
    	createDB=(Button) findViewById(R.id.button1);
    	createDB.setOnClickListener(this);
    	insert=(Button) findViewById(R.id.button2);
    	insert.setOnClickListener(this);
    	search=(Button) findViewById(R.id.button3);
    	search.setOnClickListener(this);
    	delete=(Button) findViewById(R.id.button4);
    	delete.setOnClickListener(this);
    	tv1=(TextView) findViewById(R.id.textView1);

    	
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		 int id=arg0.getId();
		 if(id==R.id.button1){
			 DatebaseHelper dbHelper=new DatebaseHelper(SQLiteActivity.this, "newsqlite.db");
			 SQLiteDatabase sqldb=dbHelper.getReadableDatabase();
			 
		 }else if (id==R.id.button2) {
			ContentValues values=new ContentValues();
			values.put("id", 1);
			values.put("name", "jec");
			DatebaseHelper datebaseHelper=new DatebaseHelper(SQLiteActivity.this,"newsqlite.db" );
			SQLiteDatabase sqLiteDatabase=datebaseHelper.getWritableDatabase();
			sqLiteDatabase.insert("User", null, values);
		}else if (id==R.id.button3) {
			DatebaseHelper datebaseHelper=new DatebaseHelper(SQLiteActivity.this,"newsqlite.db" );
			SQLiteDatabase sqLiteDatabase=datebaseHelper.getWritableDatabase();
            Cursor cursor=sqLiteDatabase.query( "User", new String[]{"id","name"}, "id=?", new String[]{"1"}, null, null, null);
	    int ids=0;
		boolean flag=cursor.moveToNext();
        if(flag){
        int count=cursor.getCount();
		ids=cursor.getInt(cursor.getColumnIndex("id"));
		String names=cursor.getString(cursor.getColumnIndex("name"));
		System.out.println("query---------"+names);
        	tv1.setText(ids+":"+names+":"+count);
		}else{
			tv1.setText("无数据");
		}

	}else if (id==R.id.button4) {
		DatebaseHelper datebaseHelper=new DatebaseHelper(SQLiteActivity.this,"newsqlite.db" );
		SQLiteDatabase sqLiteDatabase=datebaseHelper.getWritableDatabase();
		sqLiteDatabase.delete("User", "id=?", new String[]{"1"});
		tv1.setText("已删除");
	}
}
}