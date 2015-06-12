package com.giant.MrAction;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class GCMMessageView extends BaseActivity {
	String message;
	TextView txtmsg;
	WebView myBrowser;
	//String myURL = "http://www.netech.net.tw/DEMO/caiyuApp/cn_home.html";
	String myURL = "http://www.edufairnetwork.com/mobile/cn_home.asp";
	//String myURL = "file:///android_asset/cn_home.asp";
	
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messageview);
		
		Intent i = getIntent();
		myURL = i.getStringExtra("message");
		
		if(i.getStringExtra("subject") != null ){
			setTitle(i.getStringExtra("subject"));
		}
		
	 Log.e("myURL",myURL);
	 Log.e("myURL","GG");
		myBrowser=(WebView)findViewById(R.id.webView1home);  
	       WebSettings websettings = myBrowser.getSettings();  
	       websettings.setSupportZoom(true);  
	       websettings.setBuiltInZoomControls(true);   
	       websettings.setJavaScriptEnabled(true);  
	       websettings.setDisplayZoomControls(false);
	       websettings.setUseWideViewPort(true); 
	       websettings.setLoadWithOverviewMode(true);
	       websettings.setDomStorageEnabled(true);
	       
	       
	       myBrowser.setWebViewClient(new WebViewClient());  
	 
	       myBrowser.loadUrl(myURL);  
			
	       
	       
	       
	       
	       
	       
	       
	       
	       
	       
	       
	    	
	       
	       
	       
	       
	       
	       
	       
	       
	       
	       
	       
		}
		
		
		@Override
		public void onResume() {
			super.onResume();
			 
		}	
		
		
		
		 @Override  
		 public boolean onKeyDown(int keyCode, KeyEvent event) {  
		     if ((keyCode == KeyEvent.KEYCODE_BACK) && myBrowser.canGoBack())    
		     {  
		    	 myBrowser.goBack();  
		          return true;  
		     }else{
		    	 ConfirmExit(); 
		     }  
		     
		     return super.onKeyDown(keyCode, event);  
		 }  
		
		
		
		 public void ConfirmExit(){//退出確認
		        AlertDialog.Builder ad=new AlertDialog.Builder(this);
		        ad.setTitle("離開");
		        ad.setMessage("確定要離開?");
		        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
		            public void onClick(DialogInterface dialog, int i) {
		                // TODO Auto-generated method stub
		            	finish();//關閉activity
		  
		            }
		        });
		        ad.setNegativeButton("否",new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int i) {
		                //不退出不用執行任何操作
		            }
		        });
		        ad.show();//示對話框
		    }
}