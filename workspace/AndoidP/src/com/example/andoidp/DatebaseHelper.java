package com.example.andoidp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatebaseHelper extends SQLiteOpenHelper {
private static final int VERSION=1;
	public DatebaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}
	
	public DatebaseHelper(Context context, String name,int version) {
		this(context, name, null,version);
		
	}
	
	public DatebaseHelper(Context context, String name) {
		this(context, name, VERSION );
		
	}
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		System.out.println("create a new sqlite datebase");
		arg0.execSQL("create table User(id int,name varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		System.out.println("");

	}

}
