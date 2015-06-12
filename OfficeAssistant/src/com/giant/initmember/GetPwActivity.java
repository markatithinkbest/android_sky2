package com.giant.initmember;

import com.giant.MrAction.BaseActivity;
import com.giant.MrAction.R;

import Tool.ChnageM;
import Tool.EmailValidator;
import Tool.ShowDialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class GetPwActivity extends Activity {
	private EditText etAccount;
	final private String prefName = "Shoes";
	protected SharedPreferences settings;
	private Button btnSent;
	private SentAsyncTask task;	
	
	private String s_account;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_pw);
		 settings = getSharedPreferences(prefName, Context.MODE_PRIVATE);
 		setTitle("忘記密碼");
// 		ShowDialog.showDialog(ContantActivity.this, true);
 		findViews();
	}
	
	private void findViews(){
		
		etAccount = (EditText) findViewById(R.id.etAccount); 
		btnSent= (Button) findViewById(R.id.btnSent); 

		btnSent.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				s_account = etAccount.getText().toString().trim();
				
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(etAccount.getWindowToken(), 0);
								
				//驗證Email
				EmailValidator validator = new EmailValidator();		
				if(validator.validate(s_account) == false){
					etAccount.setBackgroundResource(R.drawable.et_shape_red);
					Toast.makeText(GetPwActivity.this,"Email格式錯誤", Toast.LENGTH_SHORT).show();
					//return;
				}
				etAccount.setBackgroundResource(R.drawable.et_shape_);
					
				task = new SentAsyncTask();
				task.execute();
			}
		});
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
	    switch (itemId) {	    
//	    	//左上角Home按鈕
		    case android.R.id.home:
				finish();	
		    	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private class SentAsyncTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			ShowDialog.showDialog(GetPwActivity.this, true); 
		}
				
		@Override
		protected String doInBackground(Void... arg0) {
			GetPwDAO dao = new GetPwDAO();
			String password = "";
			for (int i=0 ;i<=8 ;i++){
				password += (int)(Math.random()*9+1);
			}
			return dao.getJsonContent(s_account, ChnageM.MMM(password),password);
		}		
		//ChnageM.MMM(s_password01)
		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ShowDialog.dismissDialog();			
			if (result.isEmpty() == true || result.equals("x") ) {
				Toast.makeText(GetPwActivity.this, "無法連線", Toast.LENGTH_SHORT).show();
			}else if (result.equals("OK")){
				settings.edit().putBoolean("login", false).commit();
				settings.edit().putString("u_id", "").commit();
				settings.edit().putString("u_pw", "").commit();
				Toast.makeText(GetPwActivity.this, "密碼已寄到EMAIL", Toast.LENGTH_SHORT).show();
				GetPwActivity.this.finish();
			}else if (result.equals("無此帳號")){
				Toast.makeText(GetPwActivity.this, "無此帳號", Toast.LENGTH_SHORT).show();
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
}
