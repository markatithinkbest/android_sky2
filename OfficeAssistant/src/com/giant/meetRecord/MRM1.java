package com.giant.meetRecord;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

import com.giant.Jobpaper.JBP;
import com.giant.Jobpaper.JBPT;
import com.giant.Jobpaper.JobcontentActivity;
import com.giant.MrAction.R;

import com.special.ResideMenu.ResideMenu;

public class MRM1 extends Fragment { 
	
	  ImageView  IV1US;
		    private View parentView;
		  
		    Button mrbuttonsetting;
		    @Override
		    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		        parentView = inflater.inflate(R.layout.mrm01, container, false);
		        
		        
		        mrbuttonsetting= (Button) parentView.findViewById(R.id.mrbuttonsetting);
		        mrbuttonsetting.setOnClickListener(new OnClickListener() {   
		         @Override
		         public void onClick(View v) {	 
		        	// Intent intent = new Intent(getActivity(),NewsActivity.class);
		        	// intent.putExtra("sex", sex);
		        	// intent.putExtra("selectType", selectType);
		        	 //startActivity(intent);
		         }

		        });
		        
		        
		        return parentView;
 		
	}


			
	
	

}

