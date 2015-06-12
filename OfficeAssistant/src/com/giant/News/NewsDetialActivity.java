package com.giant.News;

import org.apache.http.util.EncodingUtils;







import com.giant.MrAction.R;

import Tool.GetPhotoSize;
import Tool.ShowDialog;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

public class NewsDetialActivity extends Activity {
	private WebView webView;
	private ImageButton btnBack;
//	private TextView tvTitle;
//	private ProgressDialog progressBar; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detial);
		//Log.e("NewsActivity", SplashActivity.lang + " .");
		findViews();
	}	
	
	private void findViews(){
		webView = (WebView) findViewById(R.id.webView);
		 
		
//		webView.setFocusableInTouchMode(false);		
//		WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        ShowDialog.showDialog(NewsDetialActivity.this);

        webView.setWebViewClient(new WebViewClient() {
            @Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("loading", "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            @Override
			public void onPageFinished(WebView view, String url) {
                Log.e("finish", "Finished loading URL: " +url);
                ShowDialog.dismissDialog();
            }

            @Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            	ShowDialog.dismissDialog();
                Log.e("error", "Error: " + description);
                Toast.makeText(NewsDetialActivity.this, description, Toast.LENGTH_SHORT).show();
            }
        });
//        tvTitle = (TextView) findViewById(R.id.tvTitle);
        
        Bundle bData = this.getIntent().getExtras();
        
//        String getPublishDate = bData.getString( "getPublishDate" );
//        String getSubject = bData.getString( "getSubject" );
//        String getSubject_TW = bData.getString( "getSubject_TW" );
        int rowID = bData.getInt( "rowID" );
//        Log.e("1", getPublishDate);
//        Log.e("2", getSubject);
//        Log.e("3", getSubject_TW);
//        Log.e("4", rowID + " ");
//        String show_getSubject = "";
//        if(SplashActivity.lang.equals("TW")){//TITLE
//			if (getSubject_TW.equals("null")  == false && getSubject_TW.trim().length() >= 1) {
////				tvTitle.setText(getSubject_TW);
//				show_getSubject = getSubject_TW;
//			}else{
////				tvTitle.setText(getSubject);
//				show_getSubject = getSubject;
//			}
//		}else{
//			show_getSubject = getSubject;
//		}
        
        
        String aa = "";	
     
        	aa = "rowID=" + rowID + " &lang=" + "TW" + " &swidth=" + GetPhotoSize.getWidth(NewsDetialActivity.this) + 
					" &phonetype=" + "android";
		
//        Log.e("GetPhotoSize.getWidth(NewsDetialActivity.this)", " .show_getSubject " +show_getSubject);
        byte[] post = EncodingUtils.getBytes(aa, "BASE64");
        webView.postUrl("http://www.comfort-mobility.com/app/NewsShow.php", post);
        WebSettings webSettings = webView.getSettings();  
        // 支援JavaScript
//        webSettings.setJavaScriptEnabled(true);             
        // 支援Zoom
        webSettings.setSupportZoom(true);                    
        webSettings.setBuiltInZoomControls(true);
	}	
	
	@Override
	protected void onPause() {
		ShowDialog.dismissDialog();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public void onBackPressed() {
		NewsDetialActivity.this.finish();
		ShowDialog.dismissDialog();
		super.onBackPressed();
	}
}
