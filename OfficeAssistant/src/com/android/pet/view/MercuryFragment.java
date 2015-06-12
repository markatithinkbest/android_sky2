package com.android.pet.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.location.Location;
//import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.geniuseoe2012.lazyloaderdemo.LoaderAdapter1;
import com.giant.MrAction.GCMMessageView;
import com.giant.MrAction.R;
//import com.giant.MrAction.IDCardFragment.check_smsCodeDAO;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MercuryFragment extends Fragment{

    SharedPreferences settings ;  
	
	TextView TTM1;
    TextView TTM2;
    TextView TTM3;
  ImageView  idphoto;
    ImageLoader imageLoader = ImageLoader.getInstance();
    
    
    
    
    private ListView item_list;

	// 換掉原來的字串陣列
    String subject[]   = null;
    
	private LoaderAdapter1 adapter;
	String data[]  = null;
    
    
	String content[]=null;
	
    
    
    
    
    
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.nmfirst, null);
		TTM1=(TextView) rootView.findViewById(R.id.TTM1);
		TTM2=(TextView) rootView.findViewById(R.id.TTM2);
		TTM3=(TextView) rootView.findViewById(R.id.TTM3);
		item_list = (ListView)rootView.findViewById(R.id.item_listqqq);
		initImageLoader(getActivity().getApplicationContext());
		settings= getActivity().getSharedPreferences(Tool.shareclass.prefName, Context.MODE_PRIVATE);
		idphoto=(ImageView) rootView.findViewById(R.id.idphotoTTM3);
		processControllers();
		updatainfoTask QQ=new updatainfoTask();
QQ.execute();
BulletinCodeDAOTask qa=new BulletinCodeDAOTask();
qa.execute();

CLCodeDAOTask FF=new CLCodeDAOTask();
FF.execute();




		return rootView;
	}
	
	   public static void initImageLoader(Context context) {
	        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
	                .threadPriority(Thread.NORM_PRIORITY - 2)
	                .denyCacheImageMultipleSizesInMemory()
	                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
	                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
	                .tasksProcessingOrder(QueueProcessingType.LIFO)
	                .writeDebugLogs() // Remove for release app
	                .build();
	        ImageLoader.getInstance().init(config);
	    }
	    
	   
	public class check_smsCodeDAO {
		@SuppressWarnings("deprecation")
		public String getJsonContent(){
			BasicHttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
			HttpConnectionParams.setSoTimeout(httpParameters, 30000);
			
			HttpPost httpPost = new HttpPost("http://www.mr-action.com/app/idcard.asp");
		
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("u_id", settings.getString("u_id", "")));
			params1.add(new BasicNameValuePair("giant", "giant"));
			params1.add(new BasicNameValuePair("giant1", "giant1"));
			params1.add(new BasicNameValuePair("giant2", "giant2"));
	
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
	
	private class updatainfoTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			
			//ShowDialog.showDialog(updataService.this, true); 
		}
				
		@Override
		protected String doInBackground(Void... arg0) {
			
			check_smsCodeDAO dao = new check_smsCodeDAO();
			return dao.getJsonContent();
		}		
		
		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//ShowDialog.dismissDialog();			
			if (result.isEmpty() == true || result.equals("x") ) {
				
			}
			Log.e("result",result);
			try {
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);	
					TTM1.setText("姓名"+jsonObject.getString("NAME"));
					 TTM2.setText("工號:"+jsonObject.getString("esno"));
				imageLoader.displayImage("http://www.mr-action.com/15Employee/images/pics/"+jsonObject.getString("picstype"), idphoto);
				
					 
					 
			}			
				
			} catch (JSONException e) {
							
			}
		
		}
		@Override
		protected void onProgressUpdate(Integer... progress) {
		}
	}
	
	
	
	
	
	
		public class BulletinCodeDAO {
		@SuppressWarnings("deprecation")
		public String getJsonContent(){
			BasicHttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
			HttpConnectionParams.setSoTimeout(httpParameters, 30000);
			
			HttpPost httpPost = new HttpPost("http://www.mr-action.com/app/Bulletin.asp");
		
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("giant", "giant"));
			params1.add(new BasicNameValuePair("giant1", "giant1"));
			params1.add(new BasicNameValuePair("giant2", "giant2"));
	
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
	
	private class BulletinCodeDAOTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			
			//ShowDialog.showDialog(updataService.this, true); 
		}
				
		@Override
		protected String doInBackground(Void... arg0) {
			
			BulletinCodeDAO dao = new BulletinCodeDAO();
			return dao.getJsonContent();
		}		
		
		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//ShowDialog.dismissDialog();			
			if (result.isEmpty() == true || result.equals("x") ) {
				
			}
			
			Log.e("result",result);
			try {
				JSONArray jsonArray = new JSONArray(result);
				data=new String [jsonArray.length()];
				content=new String [jsonArray.length()];
				subject=new String [jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);	
					data[i]=new String();
					data[i]=jsonObject.getString("bt_sdate");
					 
					content[i]=new String();
					content[i]=jsonObject.getString("bt_content");
					
					subject[i]=new String();
					subject[i]=jsonObject.getString("bt_subtitle");
			}			
				
			} catch (JSONException e) {
							
			}
			adapter = new LoaderAdapter1(data.length,getActivity(),data ,subject);

			item_list.setAdapter(adapter);  
		}
		@Override
		protected void onProgressUpdate(Integer... progress) {
		}
	}
	
	
	
	
	//------------------------------------
	private void processControllers() {
		// 建立選單項目點擊監聽物件
		OnItemClickListener itemListener = new OnItemClickListener() {
		    // 第一個參數是使用者操作的ListView物件
		    // 第二個參數是使用者選擇的項目
		    // 第三個參數是使用者選擇的項目編號，第一個是0
		    // 第四個參數在這裡沒有用途			
		    @Override
		    public void onItemClick(AdapterView<?> parent, View view, 
		            int position, long id) {							
				Intent intent = new Intent(getActivity(), GCMMessageView.class);		
				intent.putExtra("message", content[position]);			
		    }
		};
		
		
		item_list.setOnScrollListener(mScrollListener);
		OnItemClickListener itemListener0 = new OnItemClickListener() {
				
   		    @Override
   		    public void onItemClick(AdapterView<?> parent, View view, 
   		            int position, long id) {		
   		    	Intent intent = new Intent(getActivity(), GCMMessageView.class);
   		    	intent.putExtra("message", content[position]);
   		   startActivity(intent);
   		    }
   		};
	   		
	   		// 註冊選單項目點擊監聽物件
	   		item_list.setOnItemClickListener(itemListener0);
		
		
    }	
	
	// 載入選單資源

	
	
	
    OnScrollListener mScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_FLING:
				adapter.setFlagBusy(true);
				break;
			case OnScrollListener.SCROLL_STATE_IDLE:
				adapter.setFlagBusy(false);
				break;
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				adapter.setFlagBusy(false);
				break;
			default:
				break;
			}
			adapter.notifyDataSetChanged();
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

		}		
	};
	
	//-----------班表
	public class CLCodeDAO {
		@SuppressWarnings("deprecation")
		public String getJsonContent(){
			BasicHttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
			HttpConnectionParams.setSoTimeout(httpParameters, 30000);
			
			HttpPost httpPost = new HttpPost("http://www.mr-action.com/app/getclass.asp");
		
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("u_id", settings.getString("u_id", "")));
			params1.add(new BasicNameValuePair("giant", "giant"));
			params1.add(new BasicNameValuePair("giant1", "giant1"));
			params1.add(new BasicNameValuePair("giant2", "giant2"));
	
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
	
	private class CLCodeDAOTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			
			//ShowDialog.showDialog(updataService.this, true); 
		}
				
		@Override
		protected String doInBackground(Void... arg0) {
			
			CLCodeDAO dao = new CLCodeDAO();
			return dao.getJsonContent();
		}		
		
		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//ShowDialog.dismissDialog();	
			Log.e("resultQQ",result);
			if (result.isEmpty() == true || result.equals("x") ) {
				
			}
			Log.e("result",result);
			try {
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);	
					TTM3.setText("班表"+jsonObject.getString("STime")
							+"~"+
							jsonObject.getString("ETime")
							);
					settings.edit().putString("STime", jsonObject.getString("STime"));
					settings.edit().putString("ETime", jsonObject.getString("ETime"));
					settings.edit().putString("RestHr", jsonObject.getString("RestHr"));
					sendAlarmSTime(getActivity());
					sendAlarmETime(getActivity());
			}			
				
			} catch (JSONException e) {
							
			}
		
		}
		@Override
		protected void onProgressUpdate(Integer... progress) {
		}
	}
	
	 private void sendAlarmSTime(Context context){  
	        AlarmManager  alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);   
	        Calendar calendar =Calendar.getInstance(Locale.getDefault());  
	        calendar.setTimeInMillis(System.currentTimeMillis());  
	        calendar.set(Calendar.HOUR_OF_DAY, 8);  
	        calendar.set(Calendar.MINUTE, 30);  
	        calendar.set(Calendar.SECOND, 0);  
	        calendar.set(Calendar.MILLISECOND, 0);  
	  
	        Intent intent = new Intent();  
	        intent.setAction("testalarm0");  
	        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0, intent,PendingIntent.FLAG_CANCEL_CURRENT);  
	        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);  
	    }  
	      
	    private void sendAlarmETime(Context context){  
	        AlarmManager  alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);   
	        Calendar calendar =Calendar.getInstance(Locale.getDefault());  
	        calendar.setTimeInMillis(System.currentTimeMillis());  
	        calendar.set(Calendar.HOUR_OF_DAY, 20);  
	        calendar.set(Calendar.MINUTE, 30);  
	        calendar.set(Calendar.SECOND, 0);  
	        calendar.set(Calendar.MILLISECOND, 0);  
	  
	        Intent intent = new Intent();  
	        intent.setAction("testalarm1");  
	        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0, intent,PendingIntent.FLAG_CANCEL_CURRENT);  
	        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);  
	    }  
	
	
	
	
	
	
	
}
