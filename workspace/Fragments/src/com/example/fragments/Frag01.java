package com.example.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Frag01 extends Fragment {
	
	@Override
	public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	} 
        @Override
        public void onCreate(Bundle savedInstanceState) {
        	// TODO Auto-generated method stub
        	super.onCreate(savedInstanceState);
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
        	Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.frag_01, container, false);
        }
}
