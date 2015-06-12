package com.giant.initmember;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.android.pet.view.NavigationActivity;
import com.giant.MrAction.GCMIntentService;
import com.giant.MrAction.MenuActivity;
import com.giant.MrAction.R;









import com.google.android.gcm.GCMRegistrar;

import Tool.ChnageM;
import Tool.EmailValidator;
import Tool.ShowDialog;
import Tool.WebUrls;
import Tool.shareclass;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class LoginActivity extends Activity {
	private EditText etAccount;
	private EditText etPw;
	final private String prefName = "Shoes";
	protected SharedPreferences settings;
	private Button btnReg;
	private Button btnSent;
	private Button Buttonfog;
	private SentAsyncTask task;	
	
	private String s_account;
	private String s_password;
	private String state = "";
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logininit);
		   settings = getSharedPreferences(prefName, Context.MODE_PRIVATE);
 		
		
		  

	
		 
		

		
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
// 		ShowDialog.showDialog(ContantActivity.this, true);
 		findViews();
 		
 
 		
 		etAccount.setText(settings.getString("u_id", ""));
 	//	settings.edit().putString("u_id", etAccount.getText().toString()).commit();
		//settings.edit().putString("u_pw", etPw.getText().toString()).commit();
		
	}
	
	private void findViews(){
		try{
			state = getIntent().getExtras().getString("state");
		}catch(NullPointerException e){
			state = "";
		}
		etAccount = (EditText) findViewById(R.id.etAccountinit); //帳號
		etPw = (EditText) findViewById(R.id.etPw); //密碼
		btnSent= (Button) findViewById(R.id.btnSentinit); //登入
		btnReg = (Button) findViewById(R.id.btnReg); //註冊頁
		Buttonfog= (Button) findViewById(R.id.Buttonfog); //
		
		
		Buttonfog.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, GetPwActivity.class);
				//intent.putExtra("state", "car");
    			startActivity(intent); 
							}
		});
		
		
		
		
		
		
		
		btnSent.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				//取得  帳號、密碼1、密碼2
				s_account = etAccount.getText().toString().trim();
				s_password = etPw.getText().toString().trim();
				
				
				
			//	settings.edit().putString("u_id", etAccount.getText().toString()).commit();
				//settings.edit().putString("u_pw", etPw.getText().toString()).commit();
				task=new SentAsyncTask();
				task.execute();
				
				
				
				
				
				
				
				
				
				
				/*
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(etPw.getWindowToken(), 0);
								
				//驗證Email
				EmailValidator validator = new EmailValidator();		
				if(validator.validate(s_account) == false){
					etAccount.setBackgroundResource(R.drawable.et_shape_red);
					Toast.makeText(LoginActivity.this,"Email格式錯誤", Toast.LENGTH_SHORT).show();
					//return;
				}
				etAccount.setBackgroundResource(R.drawable.et_shape_);
	
				//密碼
				if(etPw.getText().toString().trim().length() >= 16){
					etPw.setBackgroundResource(R.drawable.et_shape_red);
					Toast.makeText(LoginActivity.this,"密碼錯誤超過16位數", Toast.LENGTH_SHORT).show();
					return;
				}else if(etPw.getText().toString().trim().length() <= 5){
					etPw.setBackgroundResource(R.drawable.et_shape_red);
					Toast.makeText(LoginActivity.this,"密碼錯誤少於6個位數", Toast.LENGTH_SHORT).show();
					return;
				}
				etPw.setBackgroundResource(R.drawable.et_shape_);
				
				task = new SentAsyncTask();
				task.execute();
				*/
				
			}
		});
		
		btnReg.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,Regedit.class);
				intent.putExtra("state", "car");
    			startActivity(intent); 
    			
							}
		});		
	}
	
	String codes="";
	
	private class SentAsyncTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			ShowDialog.showDialog(LoginActivity.this, true); 
		}
				
		@Override
		protected String doInBackground(Void... arg0) {
			LoginDAO dao = new LoginDAO();
			return dao.getJsonContent(s_account, s_password, settings.getString("UUID", ""));
		}		
		
		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ShowDialog.dismissDialog();			
			if (result.isEmpty() == true || result.equals("x") ) {
				Toast.makeText(LoginActivity.this, "無法連線", Toast.LENGTH_SHORT).show();
			}else if(result.equals("登入成功")){
				settings.edit().putBoolean("login", true).commit();
				settings.edit().putString("u_id", s_account).commit();
				settings.edit().putString("u_pw", s_password).commit();
				Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
		
				LoginActivity.this.finish();
				
			}else if(result.equals("密碼錯誤")){
				Toast.makeText(LoginActivity.this, "密碼錯誤", Toast.LENGTH_SHORT).show();
			}else if(result.equals("無此帳號")){
				Toast.makeText(LoginActivity.this, "無此帳號", Toast.LENGTH_SHORT).show();
			}else if(result.equals("請修改密碼")){
				Toast.makeText(LoginActivity.this, "請修改密碼", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LoginActivity.this, ChangePwActivity.class);
				startActivity(intent);
				settings.edit().putBoolean("login", true).commit();
			}else if(result.equals("請先完成驗證")){
				 AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
				    // Get the layout inflater
				    LayoutInflater inflater = getLayoutInflater();
				    final View gg=inflater.inflate(R.layout.dialog_aoth, null);
				    // Inflate and set the layout for the dialog
				    // Pass null as the parent view because its going in the dialog layout
				    builder.setView(gg)
				    // Add action buttons
				           .setPositiveButton("驗證", new DialogInterface.OnClickListener() {
				               @Override
				               public void onClick(DialogInterface dialog, int id) {
                   
				            	   TextView  usermembersms=(TextView) gg.findViewById(R.id.usermembersms);
				            	   codes=usermembersms.getText().toString();
				            	   if(usermembersms.getText().toString().equals("")){
	check_smsCodeDAOTask ggts=new check_smsCodeDAOTask();
	ggts.execute();
}
                      
				               }
				           })
				           .setNegativeButton("取消", new DialogInterface.OnClickListener() {
				               public void onClick(DialogInterface dialog, int id) {
				            	   
				               }
				           });      
				  builder.create();
				  builder.show();
				  Toast.makeText(LoginActivity.this, "請先完成驗證", Toast.LENGTH_SHORT).show();
			}else if(result.equals("需重設密碼")){
				Intent intent = new Intent(LoginActivity.this, ChangePwActivity.class);
				startActivity(intent);
				Toast.makeText(LoginActivity.this, "需重設密碼", Toast.LENGTH_SHORT).show();
			}
		}
		@Override
		protected void onProgressUpdate(Integer... progress) {
		}
	}
	
	@Override
	protected void onPause() {
		if (task != null) {
			task.cancel(true);			
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	//check_smsCode.asp
	
	public class check_smsCodeDAO {
		@SuppressWarnings("deprecation")
		public String getJsonContent(String s_account, String u_password,String SMScode){
			BasicHttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
			HttpConnectionParams.setSoTimeout(httpParameters, 30000);
			
			HttpPost httpPost = new HttpPost("http://www.mr-action.com/app/check_smsCode.asp");
			
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("u_id", s_account));
			params1.add(new BasicNameValuePair("u_password", u_password));
			params1.add(new BasicNameValuePair("giant", "giant"));
			params1.add(new BasicNameValuePair("giant1", "giant1"));
			params1.add(new BasicNameValuePair("giant2", "giant2"));
			params1.add(new BasicNameValuePair("SMScode", SMScode));	
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
	
	private class check_smsCodeDAOTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			ShowDialog.showDialog(LoginActivity.this, true); 
		}
				
		@Override
		protected String doInBackground(Void... arg0) {
			check_smsCodeDAO dao = new check_smsCodeDAO();
			return dao.getJsonContent(s_account, s_password,codes);
		}		
		
		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ShowDialog.dismissDialog();			
			if (result.isEmpty() == true || result.equals("x") ) {
				Toast.makeText(LoginActivity.this, "無法連線", Toast.LENGTH_SHORT).show();
			}else if(result.equals("認證成功")){
				settings.edit().putBoolean("login", true).commit();
				settings.edit().putString("u_id", s_account).commit();
				settings.edit().putString("u_pw", s_password).commit();
				Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
		
				LoginActivity.this.finish();
				Toast.makeText(LoginActivity.this, "認證成功", Toast.LENGTH_SHORT).show();
				
			}else if(result.equals("認證碼錯誤")){
				Toast.makeText(LoginActivity.this, "認證碼錯誤", Toast.LENGTH_SHORT).show();
			}else if(result.equals("密碼錯誤")){
				Toast.makeText(LoginActivity.this, "密碼錯誤", Toast.LENGTH_SHORT).show();
			}else if(result.equals("帳號不對")){
				Toast.makeText(LoginActivity.this, "帳號不對", Toast.LENGTH_SHORT).show();
			}
		}
		@Override
		protected void onProgressUpdate(Integer... progress) {
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
