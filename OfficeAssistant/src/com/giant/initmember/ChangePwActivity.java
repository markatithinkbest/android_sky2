package com.giant.initmember;

import com.giant.MrAction.BaseActivity;
import com.giant.MrAction.R;

import Tool.EmailValidator;
import Tool.ShowDialog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePwActivity extends BaseActivity implements OnClickListener {
	private Button btnCancel;
	private TextView tvAccount;
	private EditText etPassowrd;
	private EditText etPassowrd2;
	private Button btnLogin;
	private EditText etOrgPassowrd;
	final private String prefName = "ShoesParty";
	SharedPreferences settings;
//	String getResult;
	private String account;
	private String passowrd;
	private String passowrd2;
	private String orgPassowrd;
	private boolean showString;
	private TextView tvInfo;
	private Button btnGetPassword;
	// final private String prefName = "ShoesParty";
//	private String login;
	
	private MyDownAsyncTask myDownTask;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
//		Window window = this.getWindow();
//		window.setGravity(Gravity.TOP);
		setContentView(R.layout.dialog_change_pw);
		setTitle("更改密碼");
		settings = getSharedPreferences(prefName, Context.MODE_PRIVATE);
		findViews();
	}
	
	private void findViews() {
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		tvAccount = (TextView) findViewById(R.id.tvAccount);
		etPassowrd = (EditText) findViewById(R.id.etPassowrd);
		etPassowrd2 = (EditText) findViewById(R.id.etPassowrd2);
		etOrgPassowrd = (EditText) findViewById(R.id.etOrgPassowrd);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		tvInfo = (TextView) findViewById(R.id.tvInfo);
		try{
			showString = getIntent().getExtras().getBoolean("showString");
			if(showString == true){
				tvInfo.setVisibility(View.VISIBLE);
			}else{
				tvInfo.setVisibility(View.INVISIBLE);
			}				
		}catch (Exception e){
			showString = false;
			tvInfo.setVisibility(View.INVISIBLE);
		}
//		etAccount.setText("ianchen@global-trade.com.tw");
//		etPassowrd.setText("111111");
		
	}

	@Override
	public void onClick(View v) {
		if (v == btnLogin) {
			 account = tvAccount.getText().toString().trim();
			 passowrd = etPassowrd.getText().toString().trim();
			 passowrd2 = etPassowrd2.getText().toString().trim();
			 orgPassowrd = etOrgPassowrd.getText().toString().trim();
			EmailValidator email = new EmailValidator();
			boolean result = email.validate(account);

			if (orgPassowrd.length() <6) {
				Toast.makeText(ChangePwActivity.this, "原始密碼少於六個字", Toast.LENGTH_SHORT).show();
			}else if (passowrd.length() <6) {
				Toast.makeText(ChangePwActivity.this, "密碼少於六個字", Toast.LENGTH_SHORT).show();
			}else if (passowrd2.equals(passowrd) == false) {
				Toast.makeText(ChangePwActivity.this, "密碼比對錯誤", Toast.LENGTH_SHORT).show();
			}else if (orgPassowrd.equals(passowrd) == true) {
				Toast.makeText(ChangePwActivity.this, "新密碼不能與原始密碼一樣", Toast.LENGTH_SHORT).show();
			} else if (true == result && passowrd.length() >= 6) {	
				myDownTask = new MyDownAsyncTask();
				myDownTask.execute();
			}
		} else if (v == btnCancel) {
			onBackPressed();
			
		}
	}
	private class MyDownAsyncTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected String doInBackground(Void... arg0) {
			ChangePwDAO  dao = new ChangePwDAO();
//			int  index = account.indexOf("@");		
//			System.out.println(account.substring(0, index));
//			String u_id= account.substring(0, index);
//			Log.e("!!!!!!!!!!!!!!", MD5.MD5(u_id) + "     " + account.substring(0, index));
			
			
			return dao.getJsonContent(account, MD5.MD5(passowrd), MD5.MD5(orgPassowrd));
		}
		
		@Override
		protected void onPreExecute() {
			ShowDialog.showDialog(ChangePwActivity.this);
		}

		@Override
		protected void onPostExecute(String result) {
			ShowDialog.dismissDialog();
			if(result.equals("修改成功")){
//				int  index = account.indexOf("@");
//				System.out.println(account.substring(0, index));
//				String u_id= account.substring(0, index);
				settings.edit().putString("login", "Yes").commit();
				settings.edit().putString("u_id", tvAccount.getText().toString().trim()).commit();
				settings.edit().putString("u_pw", passowrd).commit();
				
//				System.out.println("已登入      " + account.substring(0, index));				
				Toast.makeText(ChangePwActivity.this, result, Toast.LENGTH_SHORT).show();
				//ChangePwActivity.this.finish();
			}else{
				Toast.makeText(ChangePwActivity.this, result, Toast.LENGTH_SHORT).show();
//				settings.edit().putString("login", "No").commit();
//				settings.edit().putString("ShoesParty1", "").commit();
//				settings.edit().putString("ShoesParty2", "").commit();
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			Log.e("ddd", progress.toString());
		}
	}
		
	@Override
	protected void onResume() {
		super.onResume();
		String login = settings.getString("login", "No");
		if(login.equals("No")){
			tvAccount.setText("");
			//this.finish();
		}else if(login.equals("Yes")){
			tvAccount.setText(settings.getString("u_id", " "));
		}
		Log.e("gg", settings.getString("u_id", " ") + "  ");
	}

	@Override
	public void onBackPressed() {
		if(showString == true){
			Log.e("true", "true");
			return;
		}else{
			Log.e("false", "false");
			//this.finish();
		}
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		ShowDialog.dismissDialog();
		if(myDownTask!=null)
			myDownTask.cancel(true);
		super.onPause();
	}
	
}