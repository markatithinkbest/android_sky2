package com.giant.MrAction;















import org.apache.http.util.EncodingUtils;
















import Tool.shareclass;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;


import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.RenderPriority;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;








public class MainWebFragment extends Fragment {
	WebView myBrowser;
	//String myURL = "http://www.77shoes.com/app/1006/web/tw_index11.html";
	String myURL = "";
	String message;
	String pdgg="";
	
	

	
	final private String prefName = "Shoes";
	protected SharedPreferences settings;
	 private ProgressDialog progressBar;
	
	
	@SuppressWarnings("deprecation")
	@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.mainweb, container, false);
		
		settings = getActivity().getSharedPreferences(prefName, Context.MODE_PRIVATE);
		
		Bundle bundle = getArguments();
		  myURL = bundle.getString("URL");
	myBrowser=(WebView)rootView.findViewById(R.id.webViewmain);  
	     WebSettings websettings = myBrowser.getSettings();  
	        websettings.setSupportZoom(false);  
	       websettings.setBuiltInZoomControls(false);   
	       websettings.setJavaScriptEnabled(true);  
	       websettings.setDisplayZoomControls(false);
	       websettings.setUseWideViewPort(true); 
	       websettings.setJavaScriptCanOpenWindowsAutomatically(true);
	       websettings.setRenderPriority(RenderPriority.HIGH);  
	       WebSettings webseting = myBrowser.getSettings();  
	       webseting.setDomStorageEnabled(true);
	       webseting.setDatabaseEnabled(true);
	           webseting.setAppCacheMaxSize(1024*1024*10);//设置缓冲大小，我设的是8M  
	       String appCacheDir = getActivity().getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();      
	       String dir = getActivity().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();           
	       webseting.setDatabasePath(dir);
	       webseting.setAppCachePath(appCacheDir);  
	           webseting.setAllowFileAccess(true);  
	           webseting.setAppCacheEnabled(true);  
	           webseting.setCacheMode(WebSettings.LOAD_DEFAULT);        
	       myBrowser.setWebChromeClient(new WebChromeClient(){
	   
	    	   @Override  
	    	    public void onReachedMaxAppCacheSize(long spaceNeeded,    
	    	                long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {    
	    	            quotaUpdater.updateQuota(spaceNeeded * 2);    
	    	        }         
	    	 
	    	   @Override
	    		public boolean onJsAlert(final WebView view, final String url, final String message,
	                       final JsResult result)
	               {
	                   new AlertDialog.Builder(getActivity())
	                   .setMessage(message)
	                   .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	                       @Override
	    					public void onClick(DialogInterface dialog, int which) {
	                           result.confirm();
	                       }})
	                   .show();
	                   
	                   return true;
	               }

	               @Override
	    		public boolean onJsConfirm(WebView view, String url,
	                       String message, final JsResult result)
	               {
	                   new  AlertDialog.Builder(getActivity())
	       	        .setTitle("")
	       	        .setMessage(message)
	       	        .setPositiveButton(android.R.string.ok,
	       	                new DialogInterface.OnClickListener()
	       	        {
	       	            @Override
	    					public void onClick(DialogInterface dialog, int which)
	       	            {
	       	                result.confirm();
	       	            }
	       	        })
	       	        .setNegativeButton(android.R.string.cancel,
	       	                new DialogInterface.OnClickListener()
	       	        {
	       	            @Override
	    					public void onClick(DialogInterface dialog, int which)
	       	            {
	       	                result.cancel();
	       	            }
	       	        })
	       	        .create()
	       	        .show();

	       	        return true;
	       	    }
	           }); 
	       myBrowser.setWebViewClient(new WebViewClient(){
	            @SuppressWarnings("unused")
				public boolean onConsoleMessage(ConsoleMessage cm) {
	             
	                return true;
	            }
	            @Override
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            	progressBar.show();
	                view.loadUrl(url);
	                return true;                
	            }
	            @Override
	            public void onPageFinished(WebView view, final String url) {

	            	progressBar.dismiss();


	            }
	            @Override
	            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {

	            	progressBar = new ProgressDialog(view.getContext());
	                progressBar.setCancelable(true);
	                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);                  
	               progressBar.setMessage("載入中...");
	                progressBar.show();           
	            }
	            
	        });     
	     Boolean  Networkflag=false;
	       
	       ConnectivityManager CM = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	       NetworkInfo info = CM.getActiveNetworkInfo();
	       if (info == null || !info.isAvailable()){ //判斷是否有網路
	    	   Networkflag=false;
	        }else{
	        	   Networkflag=true;
	       }
	       String postDate =
	       "mpMobile="+shareclass.lineNumber+"&mpPhoneType="+shareclass.moblie
			  +"&mpPhoneType="+shareclass.moblie+"&mpDeviceID="+shareclass.registrationId   
			  +"&mpIMEI="+Tool.shareclass.mpIMEI
			  +"&os="+"Android"+"&mpStoreID="+"Mr000000"
			  +"&u_id="+settings.getString("u_id", "")
			  +"&u_password="+settings.getString("u_pw","");
	       myBrowser.postUrl(myURL, EncodingUtils.getBytes(postDate, "BASE64"));       
	       myBrowser.setOnKeyListener(new OnKeyListener() {           
               @Override
               public boolean onKey(View v, int keyCode, KeyEvent event) {
                       if (event.getAction() == KeyEvent.ACTION_DOWN) {    
           if (keyCode == KeyEvent.KEYCODE_BACK && myBrowser.canGoBack()) {  //表示按返回键 时的操作  
        	   myBrowser.goBack();   //后退    
               return true;    //已处理    
           }    
       }    
       return false;
               }
       });
	  
	       
	       
	       
	       
	       
	       
	       
	       return rootView;
	       	       
		}
	
	
	
	 
	
	
	
		
		
		@Override
		public void onResume() {
			super.onResume();
			 
		}	
		
		
		
		
		
		
		
		 public void ConfirmExit(){//退出確認
		        AlertDialog.Builder ad=new AlertDialog.Builder(getActivity());
		        ad.setTitle("離開");
		        ad.setMessage("確定要離開?");
		        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
		            public void onClick(DialogInterface dialog, int i) {
		                // TODO Auto-generated method stub
		            	getActivity().finish();//關閉activity
		  
		            }
		        });
		        ad.setNegativeButton("否",new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int i) {
		                //不退出不用執行任何操作
		            }
		        });
		        ad.show();//示對話框
		    }
		 
		 
		 
		   
		 
		 
		
//---------------------		
		  
		  
		  
		 
		 
}