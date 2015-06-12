package com.giant.News;

import java.util.ArrayList;







import com.giant.MrAction.BaseActivity;
import com.giant.MrAction.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import Tool.ShowDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class NewsActivity extends Activity {
	private NewsDAO dao;
	private ListView lv;
	private DownNewsAsyncTask task;
	private ArrayList<NewsItem> newData;
	@SuppressWarnings("unused")
	 DisplayImageOptions options;
	 ImageLoader imageLoader = ImageLoader.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		findViews();
	}
	
	private void findViews(){
		dao = new NewsDAO();
		lv = (ListView) findViewById(R.id.lv);		
		
		task = new DownNewsAsyncTask();
		task.execute();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
//				TextView tv = (TextView) arg1.findViewById(R.id.tv1);
//				Log.e("getSubject", tv.getText().toString());
				Intent intent = new Intent();
				
				intent.putExtra("getPublishDate", newData.get(arg2).getPublishDate() +"");
				intent.putExtra("rowID", newData.get(arg2).getRowId());	
				intent.putExtra("getSubject", newData.get(arg2).getSubject());
//				intent.putExtra("getSubject_TW", newData.get(arg2).getSubject_TW());				
				
				intent.setClass(NewsActivity.this, NewsDetialActivity.class);
				startActivity(intent);
			}
		});
		
		
	}
	
	private class DownNewsAsyncTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected String doInBackground(Void... arg0) {
			return dao.getJsonContent();
		}

		@Override
		protected void onPreExecute() {
			ShowDialog.showDialog(NewsActivity.this, true);
		}

		@Override
		protected void onPostExecute(String result) {	
//			Log.e("NewsActivity", "..result QQ"  + result);
			if (result.equals("x") == false && result.equals("") == false) {				
				newData = dao.getNewsListFromJson(result);
//				Log.e("NewsActivity", "..NewsActivity QQ"  + newData.size());
				
				if (newData.size() >= 1) {
					NewsAdapter adapter = new NewsAdapter(NewsActivity.this, 0, newData);
					lv.setAdapter(adapter);					
				}

			} else {
				Toast.makeText(NewsActivity.this, "無法連線", Toast.LENGTH_SHORT).show();
			}
			ShowDialog.dismissDialog();
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
		}
	}
	
	@Override
	protected void onPause() {
		backPress();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void backPress(){
		if (task != null) {
			task.cancel(true);			
		}
	}

	@Override
	public void onBackPressed() {
		backPress();
		super.onBackPressed();
	}
}
