package com.android.pet.view;

import java.util.ArrayList;
import java.util.List;

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

import com.android.pet.view.MercuryFragment.CLCodeDAO;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.BroadcastReceiver;  
import android.content.Context;  
import android.content.Intent;  
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
  
public class AlarmBroadcastReceiver extends BroadcastReceiver {  
    SharedPreferences settings ; 
	
	@Override 
    public void onReceive(Context context, Intent intent) {
		
		
		settings= context.getSharedPreferences(Tool.shareclass.prefName, Context.MODE_PRIVATE);
		
		ConnectivityManager CM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo info = CM.getActiveNetworkInfo();
		  if (info == null || !info.isAvailable()){ //判斷是否有網路
		
		   }else{
			   
		   }
		  
		
        if ("testalarm0".equals(intent.getAction())) {  
        	new CLCodeQQDAOTask().execute();
        }  
          
        if ("testalarm1".equals(intent.getAction())) {  
        	new CLCodeQQDAOTask().execute();
        }      
    }  
	
	
	
	
	
	
	
	public class CLCodeQQDAO {
		@SuppressWarnings("deprecation")
		public String getJsonContent(){
			BasicHttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
			HttpConnectionParams.setSoTimeout(httpParameters, 30000);
			
			HttpPost httpPost = new HttpPost("http://www.mr-action.com/app/getclass.asp");
		
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("u_id", settings.getString("u_id", "")));
			params1.add(new BasicNameValuePair("Latitude", settings.getString("getLatitude", "")));
			params1.add(new BasicNameValuePair("Longitude", settings.getString("getLongitude", "")));
			params1.add(new BasicNameValuePair("places", settings.getString("places", "")));
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
	
	private class CLCodeQQDAOTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			
			//ShowDialog.showDialog(updataService.this, true); 
		}
				
		@Override
		protected String doInBackground(Void... arg0) {
			
			CLCodeQQDAO dao = new CLCodeQQDAO();
			return dao.getJsonContent();
		}		
		
		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//ShowDialog.dismissDialog();	
			Log.e("resultQQ",result);
		
		
		}
		@Override
		protected void onProgressUpdate(Integer... progress) {
		}
	}
	
	
	
	
	
	
	
	
	
}
