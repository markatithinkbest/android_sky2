
package com.giant.initmember;

import com.giant.MrAction.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;



public class MemberActivity extends Fragment {
	final private String prefName = "Shoes";
	protected SharedPreferences settings;
	private Button btnMemData;
	private Button btnGetPw;
	 private View parentView;
	private final static int RESULT = 0;
	View vvv;
	 
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        parentView = inflater.inflate(R.layout.activity_member, container, false);
	        settings = getActivity().getSharedPreferences(prefName, Context.MODE_PRIVATE);
	     
	        findViews();
	        return parentView;
	    }
	
	
	
	
	
	
	
	private void findViews(){
	
		btnMemData = (Button) parentView.findViewById(R.id.btnMemData);
		
		
		

		btnMemData.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				//Intent intent = new Intent(getActivity(), MemberInfoActivity.class);
				//startActivity(intent);
			}
		});
			
		
		btnGetPw = (Button) parentView.findViewById(R.id.btnGetPw);
		btnGetPw.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), GetPwActivity.class);
				startActivity(intent);
			}
		});
	}	
	
	
	
	@Override
	public void onResume() {
		
		super.onResume();
	}

	
}
