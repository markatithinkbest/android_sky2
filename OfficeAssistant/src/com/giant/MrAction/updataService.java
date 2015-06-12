package com.giant.MrAction;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

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

import com.giant.initmember.LoginActivity;
import com.giant.initmember.LoginActivity.check_smsCodeDAO;

import Tool.ShowDialog;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
public class updataService  extends Service {  
    private static final String TAG = "Track";  
    private LocationManager locationManager;
    GPSListener   locationListenerNGPS ;
    GPSListener   locationListenerGPS ;
    Geocoder geocoder;    
    Double	longitude;
	Double latitude;
	Handler	handler=new Handler();
	String WIFIINFO;
	final private String prefName = "Shoes";
	protected SharedPreferences settings;
    @Override  
    public IBinder onBind(Intent arg0) {  
//      Log.d(TAG, "onBind.");  
        return null;  
    }  
      
    public void onStart(Intent intent, int startId) {    
        Log.d(TAG, "onStart.");  
       // Toast.makeText(getApplicationContext(), "启动服务",Toast.LENGTH_SHORT).show();  
        super.onStart(intent, startId);  
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListenerNGPS = new GPSListener();
        locationListenerGPS = new GPSListener();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNGPS); 
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);
        settings = getSharedPreferences(prefName, Context.MODE_PRIVATE);
        handler.postDelayed(showTime, 50000);
        super.onStart(intent, startId);
    }  
      
      
  
    public void onDestroy() {  
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        handler.removeCallbacks(showTime);
        
        
        super.onDestroy();  
     
    }  
  
  
    
    
    
    
    
    
    class GPSListener implements LocationListener {   
        @Override
        public void onLocationChanged(Location location) {
        		longitude=location.getLongitude();
        	 latitude=location.getLongitude();
        	Log.e("getLongitude",location.getLongitude()+"");   
        	Log.e("getLatitude",location.getLatitude()+"");  
        	
        	locationManager.removeUpdates(this);
        	  
        }     
        @Override
        public void onProviderDisabled(String provider) {}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras){}
     }
    
    
    
    
    

  
   
   
    
    
    private Runnable showTime = new Runnable() {
        public void run() {
            //log目前時間
        	Log.i("time:", new Date().toString());
            ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = CM.getActiveNetworkInfo();
            if (info == null || !info.isAvailable()){ //判斷是否有網路
              //沒有網路
            	WIFIINFO="無";
             }else{
            	
     		//	Double longitude = location.getLongitude();
     	      
     			//Double  latitude = location.getLatitude();
                
                  WIFIINFO= info.getExtraInfo();
                  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNGPS); 
                  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);
                  new updatainfoTask().execute();
            	
             }
    
            handler.postDelayed(this, 30000);
        }
    };

    
    
    
    
    
    
    public class check_smsCodeDAO {
		@SuppressWarnings("deprecation")
		public String getJsonContent(){
			BasicHttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
			HttpConnectionParams.setSoTimeout(httpParameters, 30000);
			
			HttpPost httpPost = new HttpPost("http://www.mr-action.com/app/updataloc.asp");
		
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("u_id", settings.getString("u_id", "")));
			params1.add(new BasicNameValuePair("giant", "giant"));
			params1.add(new BasicNameValuePair("giant1", "giant1"));
			params1.add(new BasicNameValuePair("giant2", "giant2"));
		params1.add(new BasicNameValuePair("longitude", longitude+""));	
		params1.add(new BasicNameValuePair("latitude", latitude+""));	
		params1.add(new BasicNameValuePair("WIFIINFO", WIFIINFO+""));
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
				
		}
		@Override
		protected void onProgressUpdate(Integer... progress) {
		}
	}
    
    
    
    
    
    
    
}
