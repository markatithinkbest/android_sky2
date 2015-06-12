package com.giant.CloudPrint;

import java.io.File;









import com.giant.MrAction.R;
import com.special.ResideMenu.ResideMenu;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CPMainF extends Fragment implements View.OnClickListener {
	
	private static String TAG = "MainActivity";
	private Button mBtOpenFile ;
	private Intent fileChooserIntent ;
	private static final int REQUEST_CODE = 1;   //??��?����?
	public static final String EXTRA_FILE_CHOOSER = "file_chooser";
	TextView filepath001;
	Button btnPrint;
	TextView TextViewtype,textviewnamegg;
	String mimeType ;
	WebView web001;
	 private View parentView;
	    private ResideMenu resideMenu;
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	 parentView = inflater.inflate(R.layout.cloudprintmain, container, false);

		filepath001=(TextView)parentView.findViewById(R.id.filepath001);
		TextViewtype=(TextView)parentView.findViewById(R.id.TextViewtype);
		textviewnamegg=(TextView)parentView.findViewById(R.id.textviewnamegg);
		mBtOpenFile = (Button)parentView.findViewById(R.id.btOpenFile);
		mBtOpenFile.setOnClickListener(this);
		web001=(WebView)parentView.findViewById(R.id.cloudprintweb001);
		fileChooserIntent =  new Intent(getActivity() , 
					FileChooserActivity.class);
				
		btnPrint = (Button) parentView.findViewById(R.id.buttonprint001);
		btnPrint.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (isNetworkAvailable() == false) {
					Toast.makeText(
							getActivity(),
							"Network connection not available, Please try later",
							Toast.LENGTH_SHORT).show();
				} else {
					File file = new File(filepath001.getText().toString());
					//File file = new File(Environment
					//		.getExternalStorageDirectory().getAbsolutePath()
					//		+ "/personal/calendar.pdf");
					
					Uri	url =Uri.fromFile(file);
 mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
		
		MimeTypeMap.getFileExtensionFromUrl(url.toString()));
					Log.e("GGG",Environment
							.getExternalStorageDirectory().getAbsolutePath());
					Log.e("mimeType",mimeType);
					
					Intent printIntent = new Intent(getActivity(),
							PrintDialogActivity.class);
					printIntent.setDataAndType(url,
							mimeType);
					printIntent.putExtra("title", "Android print demo");
					startActivity(printIntent);
				}
			}
		});
		
		 return parentView;
	}

	public void onClick(View v){
		int i = v.getId();
		if (i == R.id.btOpenFile) {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				startActivityForResult(fileChooserIntent, REQUEST_CODE);
			else
				toast(getText(R.string.sdcard_unmonted_hint));

		} else {
		}
	}
	
	private void toast(CharSequence hint){
	    Toast.makeText(getActivity(), hint , Toast.LENGTH_SHORT).show();
	}
	
	public void onActivityResult(int requestCode , int resultCode , Intent data){
		
		Log.v(TAG, "onActivityResult#requestCode:"+ requestCode  + "#resultCode:" +resultCode);
		if(resultCode == android.app.Activity.RESULT_CANCELED){
			toast(getText(R.string.open_file_none));
			return ;
		}
		if(resultCode == android.app.Activity.RESULT_OK && requestCode == REQUEST_CODE){
			//����?�敺??���?
			String pptPath = data.getStringExtra(EXTRA_FILE_CHOOSER);
			Log.v(TAG, "onActivityResult # pptPath : "+ pptPath );
			if(pptPath != null){
				toast("Choose File : " + pptPath);
				filepath001.setText(pptPath);
				File file = new File(pptPath);
				Uri	url =Uri.fromFile(file);
				 mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(						
						MimeTypeMap.getFileExtensionFromUrl(url.toString()));
	 			 TextViewtype.setText(mimeType);
				  textviewnamegg.setText(file.getName());
			}
			else
				toast("開啟資料夾失敗");
		}
	}
	public boolean isNetworkAvailable() {

		ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// if no network is available networkInfo will be null
		// otherwise check if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			Log.e("Network Testing", "***Available***");
			return true;
		}
		Log.e("Network Testing", "***Not Available***");
		return false;
	}
	
	
	
	
	

}