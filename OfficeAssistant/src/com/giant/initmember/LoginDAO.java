package com.giant.initmember;

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

import Tool.WebUrls;
import Tool.shareclass;
import android.util.Log;



public class LoginDAO {
	@SuppressWarnings("deprecation")
	public String getJsonContent(String s_account, String u_password, String UUID) {
		BasicHttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
		HttpConnectionParams.setSoTimeout(httpParameters, 30000);
		
		HttpPost httpPost = new HttpPost(WebUrls.login);
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		params1.add(new BasicNameValuePair("u_id", s_account));
		params1.add(new BasicNameValuePair("u_password", u_password));
		params1.add(new BasicNameValuePair("giant", "giant"));
		params1.add(new BasicNameValuePair("giant1", "giant1"));
		params1.add(new BasicNameValuePair("giant2", "giant2"));
		params1.add(new BasicNameValuePair("UUID", UUID));
		
		params1.add(new BasicNameValuePair("mpMobile",shareclass.lineNumber ));
		params1.add(new BasicNameValuePair("mpPhoneType",shareclass.moblie ));
		params1.add(new BasicNameValuePair("mpDeviceID",shareclass.registrationId ));
		params1.add(new BasicNameValuePair("mpIMEI",shareclass.mpIMEI ));
		params1.add(new BasicNameValuePair("mpStoreID","Mr000000" ));
		params1.add(new BasicNameValuePair("os","Android" ));
		
		
		
		
		
	
		
		
		
		
		
		
		
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
