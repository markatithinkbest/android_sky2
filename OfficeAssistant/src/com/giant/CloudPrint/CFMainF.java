package com.giant.CloudPrint;

import it.sauronsoftware.ftp4j.FTPClient;

import java.io.BufferedInputStream;
import java.io.File;











import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.android.pet.view.NavigationActivity;
import com.giant.MrAction.R;
import com.giant.initmember.LoginActivity;
import com.giant.initmember.LoginActivity.check_smsCodeDAO;
import com.special.ResideMenu.ResideMenu;

import Tool.ShowDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CFMainF extends Fragment implements View.OnClickListener {
	
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
	    
	    
	    final String FTP_HOST = "www.mr-action.com";
	    final String FTP_USER = "MrActionFax";
	    final String FTP_PASS = "b3vXWRuR";
	    
		final private String prefName = "Shoes";
		protected SharedPreferences settings;
	    
	    
	    EditText 		cfmeditText1   ; 
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	 parentView = inflater.inflate(R.layout.cfmmain, container, false);
	 cfmeditText1=(EditText)parentView.findViewById(R.id.cfmeditText1);
		filepath001=(TextView)parentView.findViewById(R.id.cfmfilepath001);
		TextViewtype=(TextView)parentView.findViewById(R.id.cfmTextViewtype);
		textviewnamegg=(TextView)parentView.findViewById(R.id.cfmtextviewnamegg);
		mBtOpenFile = (Button)parentView.findViewById(R.id.cfmbtOpenFile);
		mBtOpenFile.setOnClickListener(this);
	
		fileChooserIntent =  new Intent(getActivity() , 
					FileChooserActivity.class);
				
		btnPrint = (Button) parentView.findViewById(R.id.cfmbuttonprint001);
		btnPrint.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isNetworkAvailable() == false) {
					Toast.makeText(
							getActivity(),
							"請檢查連線或稍後重試",
							Toast.LENGTH_SHORT).show();
				} else {
					if(cfmeditText1.getText().toString().equals("")){
					
					}else{
						CFMDAOTask gg=new CFMDAOTask();
						gg.execute();
						Log.e("RD","RD");
					}
					
					}
			}
		});
		settings =getActivity().getSharedPreferences(prefName, Context.MODE_PRIVATE);
		 return parentView;
	}

	 
	void FTPUPDATA(){
		
		Log.e("HH","HH");
	    FTPClient client = new FTPClient();

	    try {

	        client.connect(FTP_HOST);
	        client.login(FTP_USER, FTP_PASS);
	        client.setType(FTPClient.TYPE_BINARY);
	       // client.changeDirectory("/Faxrecord/");
	        File uploadFile = new File(filepath001.getText().toString());
	        client.upload(uploadFile);

	    } catch (Exception e) {
	        e.printStackTrace();
	        try {
	            client.disconnect(true);
	        } catch (Exception e2) {
	            e2.printStackTrace();
	            Log.e("Exception",e2.toString());
	        }
	        Log.e("Exception",e.toString());
	    }
		
		
		
		
		
	}
	 
	 
	 
	 
	 
	 
	 
	 
	public void onClick(View v){
		
		Log.e("GG","FF");
		
		/*switch(v.getId()){
		    case R.id.btOpenFile :
		    	if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				    startActivityForResult(fileChooserIntent , REQUEST_CODE);
		    	else
		    		toast(getText(R.string.sdcard_unmonted_hint));
				break ;
	        default :
	    	    break ;
		}*/
		
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		    startActivityForResult(fileChooserIntent , REQUEST_CODE);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class CFMDAO {
		@SuppressWarnings("deprecation")
		public String getJsonContent(String s_account, String u_password,String SMScode){
			BasicHttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
			HttpConnectionParams.setSoTimeout(httpParameters, 30000);
			                         
			HttpPost httpPost = new HttpPost("http://www.mr-action.com/app/nfx1.asp");
			Log.e("QQ","QQ");
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("account", s_account));
			params1.add(new BasicNameValuePair("password", u_password));
			params1.add(new BasicNameValuePair("fax", SMScode));	
			params1.add(new BasicNameValuePair("file", textviewnamegg.getText().toString()));
			
			Log.e("params1",params1.toString());
		
					
			String result = "";
			try { 
				httpPost.setEntity(new UrlEncodedFormEntity(params1, HTTP.UTF_8));
	            DefaultHttpClient  defaultHttpClient = new DefaultHttpClient(httpParameters);		            
	            HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
	            if (httpResponse.getStatusLine().getStatusCode() == 200) {
	                result= EntityUtils.toString(httpResponse .getEntity());
	            }
			}catch (Exception e) {
				e.printStackTrace();
				 return "x";
			}
			return result;
		}
	}
	
	private class CFMDAOTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			ShowDialog.showDialog(getActivity(), true); 
		}
				
		@Override
		protected String doInBackground(Void... arg0) {
			FTPUPDATA();
			CFMDAO dao = new CFMDAO();
			
			Log.e("QQQ","QQQ");
			return dao.getJsonContent(settings.getString("u_id", ""),
					settings.getString("u_pw", ""),
					cfmeditText1.getText().toString());
			
			
		}		
		
		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ShowDialog.dismissDialog();	
			Log.e("QQQQ","QQQQ");
			Log.e("result",result+"   ");
			if (result.isEmpty() == true || result.equals("x") ) {
				Toast.makeText(getActivity(), "無法連線", Toast.LENGTH_SHORT).show();
			}else if(result.equals("OK")){
				
				Toast.makeText(getActivity(), "寄送成功", Toast.LENGTH_SHORT).show();
				
			}else{
				Toast.makeText(getActivity(), "無法連線", Toast.LENGTH_SHORT).show();
			}
		}
		@Override
		protected void onProgressUpdate(Integer... progress) {
		}
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}