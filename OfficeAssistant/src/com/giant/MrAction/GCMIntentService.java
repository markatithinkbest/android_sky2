package com.giant.MrAction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

import Tool.ChnageM;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;



@TargetApi(Build.VERSION_CODES.JELLY_BEAN) @SuppressLint("NewApi")
public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCM Tutorial::Service";
	private static  int ggg=1;
	// Use your PROJECT ID from Google API into SENDER_ID
	public static final String SENDER_ID = "158163249025";
	protected SharedPreferences settings;
	public GCMIntentService() {
		super(SENDER_ID);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "onRegistered: registrationId=" + registrationId);
		Tool.shareclass.registrationId=registrationId;
		String prefName = "Shoes";
		 settings = getSharedPreferences(prefName, Context.MODE_PRIVATE);
		settings.edit().putString("registrationId", registrationId).commit();
		
		
		
		
		
		Log.e("AsyncTaskRESms","SS");
		AsyncTaskRESms ATRESMS=new AsyncTaskRESms();
   	 ATRESMS.execute();
   	Log.e("AsyncTaskRESms","GG");
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {

		Log.i(TAG, "onUnregistered: registrationId=" + registrationId);
	}


	@TargetApi(Build.VERSION_CODES.JELLY_BEAN) @SuppressLint("NewApi") protected void onMessage(Context context, Intent data) {
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		String message;
		// Message from PHP server
		message = data.getStringExtra("message");
		//message = data.getStringExtra("http");
		// Open a new activity called GCMMessageView
		Intent intent = new Intent(this, GCMMessageView.class);
		// Pass data to the new activity
			Log.e("message",message);	
		String[] datainfo=message.split(";");
		intent.putExtra("message", datainfo[8]);
		// Starts the activity on notification click
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		
		
		// Create the notification with a notification builder
	/*	Notification notification = new Notification.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setWhen(System.currentTimeMillis())
				.setContentTitle(message)
				.setTicker("MUFFY時尚") 
				.setLargeIcon(getBitmapFromURL("http://muffy.com.tw/image/cache/data/Clothes/US10/US10-1-700x700.jpg"))
				.setContentText(message).setContentIntent(pIntent)
				.getNotification();
		
		*/
		/*
		Notification notification = new Notification.BigPictureStyle(
				 new Notification.Builder(this)
			        .setContentTitle("ContentTitle")
			        .setSmallIcon(R.drawable.ic_launcher)
			        			.setWhen(System.currentTimeMillis())
			        			.setTicker("MUFFY時尚") 
			        .setLargeIcon(getBitmapFromURL("http://muffy.com.tw/image/cache/data/Clothes/US10/US10-1-700x700.jpg"))
			       .setContentText("HHHHHH")
			        .setContentIntent(pIntent)
			      // .getNotification()
			     )
			//  .bigLargeIcon(largeIconBitmap) // Notification.Builder#setLargeIcon() を上書き
			    .bigPicture(getBitmapFromURL("http://muffy.com.tw/image/cache/data/Clothes/US10/US10-1-700x700.jpg"))
			  .setBigContentTitle("BigContentTitle") // Notification.Builder#setContentTitle() を上書き
			    .setSummaryText("SummaryText")
			    .build();
		*/
		Notification notification = null;
		Notification.BigPictureStyle bigPictureStyle   = new Notification.BigPictureStyle();
	
		
		
	
		if(datainfo[0].equals("0")||datainfo[0]=="0"){
			 notification = new Notification.Builder(this)
			.setSmallIcon(R.drawable.ic_launcher)
			.setWhen(System.currentTimeMillis())
			.setContentTitle(message)
			.setTicker(datainfo[5]) 
			.setLargeIcon(getBitmapFromURL(datainfo[6]))
			.setContentText(message).setContentIntent(pIntent)
			.getNotification();
		}else if(datainfo[0].equals("1")||datainfo[0]=="1"){
			   // 建立BigPictureStyle
				bigPictureStyle.setBigContentTitle(datainfo[1]); // 當BigPictureStyle顯示時，用BigPictureStyle的setBigContentTitle覆蓋setContentTitle的設定
				bigPictureStyle.setSummaryText(datainfo[2]);
			// 要顯示在通知上的大圖片
			bigPictureStyle.bigPicture(getBitmapFromURL(datainfo[7])); // 設定BigPictureStyle的大圖片 
	 notification = 
				new Notification.Builder(getApplicationContext())
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle(datainfo[3])
		.setContentText(datainfo[4])
		.setTicker(datainfo[5]) 
		  .setLargeIcon(getBitmapFromURL(datainfo[6]))
		.setStyle(bigPictureStyle) .setContentIntent(pIntent).build(); // 建立通知
		}
		
		
		
		try{		
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		long[] vibrate = {0,100,200,300}; //0毫秒后开始振动，振动100毫秒后停止，再过200毫秒后再次振动300毫秒
 notification.vibrate = vibrate;
		}catch(Exception e){
			e.printStackTrace();
		}
	notification.defaults |= Notification.DEFAULT_LIGHTS;
	notification.ledARGB = 0xff00ff00;
	notification.ledOnMS = 300; //亮的时间
notification.ledOffMS = 1000; //灭的时间
notification.flags |= Notification.FLAG_SHOW_LIGHTS;
notification.defaults |=Notification.DEFAULT_SOUND;
		
		
		
		
		
		
		// Remove the notification on click
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

	
		manager.notify(ggg, notification);
if(Tool.shareclass.notificationFlag==1){
		ggg++;
}	
		
		

ArrayList<String> recordstr = null;
FileInputStream fis;
try {	
	fis = openFileInput("GCMKEY_FEETH");
	ObjectInputStream is;
	try {
		is = new ObjectInputStream(fis);
		 try {
			 recordstr  = (ArrayList<String>) is.readObject();	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			is.close();
			fis.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
} catch (FileNotFoundException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
if (recordstr == null) {
	recordstr = new ArrayList<String>();
}
	//	it.gmariotti.android.example.navigationdrawer.MainActivity.settings.edit().getStringSet("KEY_HISTORY", null);
int MAX_HISTORY=50;	

SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

String date = sDateFormat.format(new java.util.Date()); 


if (recordstr.size() > MAX_HISTORY) {
	int size = recordstr.size();
	@SuppressWarnings("rawtypes")
	Iterator<String> ite = recordstr.iterator();
	recordstr.remove(0);
	recordstr.add(date+";"+datainfo[3]+";"+datainfo[4]+";"+datainfo[8]
			);		
	FileOutputStream fos;
	try {
		fos = openFileOutput("GCMKEY_FEETH", Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		Log.e("GGGG","FFFFFG");
		os.writeObject(recordstr);
		os.close();
		fos.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}else{
	recordstr.add(date+";"+datainfo[3]+";"+datainfo[4]+";"+datainfo[8]
			);	
	FileOutputStream fos;
	try {
		fos = openFileOutput("GCMKEY_FEETH", Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		Log.e("GGGG","FFFFFG");
		os.writeObject(recordstr);
		os.close();
		fos.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
} 















		/*
		{
			// Wake Android Device when notification received
			PowerManager pm = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			final PowerManager.WakeLock mWakelock = pm.newWakeLock(
					PowerManager.FULL_WAKE_LOCK
							| PowerManager.ACQUIRE_CAUSES_WAKEUP, "GCM_PUSH");
			mWakelock.acquire();
			
			
			// Timer before putting Android Device to sleep mode.
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {
					mWakelock.release();
				}
			};
			timer.schedule(task, 5000);
		}*/

	}

	@Override
	protected void onError(Context arg0, String errorId) {

		Log.e(TAG, "onError: errorId=" + errorId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        // Log exception
	        return null;
	    }
	}
	
	

	check_memberPh daoIsre;
	 class AsyncTaskRESms extends AsyncTask<Void, Integer, String> {
		@Override
		protected String  doInBackground(Void... arg0) {
			Bitmap bmp=null;
			try {
				daoIsre = new check_memberPh();
				JSONArray ja=daoIsre.getJsonContent();
				} catch (Exception e) {
				e.printStackTrace();
				Log.e(this.toString(),e.toString());
				return null;
			}
			return null;
			
		}
		@Override
		protected void onPreExecute() {}

		@Override
		protected void onPostExecute(String result) {
		}
		@Override
		protected void onProgressUpdate(Integer... progress) {
		}
	}
	class check_memberPh {
		public JSONArray ja_;
	
		@SuppressWarnings("deprecation")
		public JSONArray getJsonContent()  {
					JSONArray ja=null;
			BasicHttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
			HttpConnectionParams.setSoTimeout(httpParameters, 30000);		
			HttpPost httpRequest = new HttpPost("http://www.mr-action.com/app/check_memberPh.asp");			
	        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	        params1.add(new BasicNameValuePair("giant", "giant"));
	        params1.add(new BasicNameValuePair("giant1", "giant1"));
	        params1.add(new BasicNameValuePair("giant2", "giant2"));
	        params1.add(new BasicNameValuePair("mpMobile", Tool.shareclass.lineNumber));
	        params1.add(new BasicNameValuePair("mpPhoneType", Tool.shareclass.moblie));
	        params1.add(new BasicNameValuePair("mpDeviceID", Tool.shareclass.registrationId));
	        params1.add(new BasicNameValuePair("mpIMEI", Tool.shareclass.mpIMEI));
	        params1.add(new BasicNameValuePair("os", "Android"));
	        params1.add(new BasicNameValuePair("mpStoreID", "Mr000000"));

	        String prefName = "Shoes";
	  	    settings = getSharedPreferences(prefName, Context.MODE_PRIVATE);	  	   
	  	    params1.add(new BasicNameValuePair("memberid",settings.getString("u_id", "")));        
	       //  params1.add(new BasicNameValuePair("mpStoreID", "M0000156"));	       	        
	        Log.e("params1",params1.toString());	        
	        String result = "";       
	        try {
	            httpRequest.setEntity(new UrlEncodedFormEntity(params1, HTTP.UTF_8));		            
	            DefaultHttpClient  defaultHttpClient = new DefaultHttpClient(httpParameters);		            
	            HttpResponse httpResponse = defaultHttpClient.execute(httpRequest);
	            if (httpResponse.getStatusLine().getStatusCode() == 200) {
	                result = EntityUtils.toString(httpResponse.getEntity());
	               // ja = new JSONArray(result);
	                //ja_ = ja;
	            }
			}catch (Exception e) {
				e.printStackTrace();
				return ja;
			}		
	        Log.e("resmsDAO", result+" ..");
	        return ja;
		}
		
		
	}
	
	
}