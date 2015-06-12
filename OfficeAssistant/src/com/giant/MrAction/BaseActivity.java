package com.giant.MrAction;

import java.util.UUID;
import com.giant.MrAction.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class BaseActivity extends FragmentActivity {
	protected ActionBar actionBar;
	final private String prefName = "Shoes";
	protected SharedPreferences settings;
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logininit);
		
		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.ic_stub)
//		.showImageForEmptyUri(R.drawable.ic_empty)
//		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
//		ActionBar actionBar = getActionBar();
		actionBar = getActionBar();
//	    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);
	    //上方Menu Bar 底色
		BitmapDrawable background = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.id_bg)); 
	    background.setTileModeX(android.graphics.Shader.TileMode.REPEAT);
	    actionBar.setBackgroundDrawable(background);	  
	    
	    //左上角是否有返回圖示-----------------------------  
	    //true有返回圖示 , false無返回圖示
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    settings = getSharedPreferences(prefName, Context.MODE_PRIVATE);
	    String UUID__ = settings.getString("UUID", "");
	    if(UUID__.isEmpty() == true || UUID__ == null){
	    	settings.edit().putString("UUID", UUID.randomUUID().toString()).commit();
	    }
	    ///////////////////////////////////////////
	}
    
	public ImageLoader getImageLoader(){
		return imageLoader;
	}
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
//		return true;
//	}
//
//	
//	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
	    switch (itemId) {
	    
//	    	//左上角Home按鈕
		    case android.R.id.home:
		    		    	this.finish();
		    
		    		    	return true;
	       
	    
	    
//		    case android.R.id.home:
//		    	//詢問題否離開
//		    	new AlertDialog.Builder(BaseActivity.this)
//				.setTitle("主頁面")
//				.setMessage("你確定要返回主頁面？")
//				.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						//Do nothing
//					}
//				}).setNegativeButton("確定", new DialogInterface.OnClickListener() {	
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						startActivity(new Intent(BaseActivity.this,MainActivity02.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//					}
//				}).show();
//		    	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	    

	    
	    //return true;
//	    switch (item.getItemId()) {	    
//	        case R.id.action_member:
//	        	Log.e("SSS", "action_settings");
////	            openSearch();
//	            return true;
//	        case R.id.action_aboutus:
//	        	Intent intent = new Intent(BaseActivity.this, AboutUsctivity.class);
//	        	startActivity(intent);
//	        	Log.e("SSS", "action_settings1");
////	            openSettings();
//	            return true;
//	        case R.id.action_contant:
//	        	Log.e("SSS", "action_contant");
////	            openSettings();
//	            return true;
//	        default:
//	            return super.onOptionsItemSelected(item);
//	    }
	}
	
}
