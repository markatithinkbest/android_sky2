package com.giant.initmember;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.util.Log;

public class RegeditDAO {
	private final String url = "http://www.mr-action.com/app/regedit.asp";

	public String getJsonContent(String name, String mail, String password, String phone, 
			String mobilePhone,String place1, String place2, String addrress, String birthday, String sex, String epaper, String android_id,
			String receive,String vers)  {
		BasicHttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
		HttpConnectionParams.setSoTimeout(httpParameters, 30000);		
		
		int  index = mail.indexOf("@");
		System.out.println(mail.substring(0, index));
		String u_id= mail.substring(0, index);
//		Log.e("!!!!!!!!!!!!!!", u_id);
		String otheraddrress = "";
		if(place1.equals("其它地區")){
			otheraddrress = addrress;
			addrress = "";
		}
		HttpPost httpRequest = new HttpPost(url);
        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("giant", "giant"));
        params1.add(new BasicNameValuePair("giant1", "giant1"));
        params1.add(new BasicNameValuePair("giant2", "giant2"));
        
        params1.add(new BasicNameValuePair("u_id", u_id));
        params1.add(new BasicNameValuePair("u_password", password));
        params1.add(new BasicNameValuePair("u_RealPassword", password));
        params1.add(new BasicNameValuePair("u_name", name));
        params1.add(new BasicNameValuePair("sex", sex));
        params1.add(new BasicNameValuePair("phone1", phone));
        params1.add(new BasicNameValuePair("phone2",mobilePhone ));
        params1.add(new BasicNameValuePair("mail", mail));
        
        params1.add(new BasicNameValuePair("city", place1));
        params1.add(new BasicNameValuePair("area", place2));
        params1.add(new BasicNameValuePair("address", addrress));
        params1.add(new BasicNameValuePair("email", mail));
        
        params1.add(new BasicNameValuePair("epaper", epaper));

        params1.add(new BasicNameValuePair("birthday", birthday));
        params1.add(new BasicNameValuePair("othe_address", otheraddrress));
        params1.add(new BasicNameValuePair("android_id", android_id));
        params1.add(new BasicNameValuePair("ios_id", ""));
        params1.add(new BasicNameValuePair("receive", receive));
        
        params1.add(new BasicNameValuePair("mpMobile", Tool.shareclass.lineNumber));
        params1.add(new BasicNameValuePair("mpPhoneType", Tool.shareclass.moblie));
        params1.add(new BasicNameValuePair("mpDeviceID", Tool.shareclass.registrationId));
        params1.add(new BasicNameValuePair("mpIMEI", Tool.shareclass.mpIMEI));
        params1.add(new BasicNameValuePair("os", "Android"));
        params1.add(new BasicNameValuePair("vers",vers));
 Log.e("params1",params1.toString());
        String result = "";
        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(params1, HTTP.UTF_8));		            
            DefaultHttpClient  defaultHttpClient = new DefaultHttpClient(httpParameters);		            
            HttpResponse httpResponse = defaultHttpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result= EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "x";
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "x";
		} catch (ParseException e) {
			e.printStackTrace();
			 return "x";
		} catch (IOException e) {
			e.printStackTrace();
			 return "x";
		}catch (Exception e) {
			e.printStackTrace();
			 return "x";
		}
		Log.e("resultresultresult", result + "   result");
			
        return result;
	}
	
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = md5Bytes[i] & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

//	// 可逆的加密算法
//	public static String KL(String inStr) {
//		// String s = new String(inStr);
//		char[] a = inStr.toCharArray();
//		for (int i = 0; i < a.length; i++) {
//			a[i] = (char) (a[i] ^ 't');
//		}
//		String s = new String(a);
//		return s;
//	}
//
//	// 加密后解密
//	public static String JM(String inStr) {
//		char[] a = inStr.toCharArray();
//		for (int i = 0; i < a.length; i++) {
//			a[i] = (char) (a[i] ^ 't');
//		}
//		String k = new String(a);
//		return k;
//	}
}
