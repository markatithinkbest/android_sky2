package com.giant.initmember;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

public class ChangePwDAO {
	private final String url = "http://www.mr-action.com/app/change_pw.asp";

	public String getJsonContent(String id, String password, String orgPw)  {
		Log.e("getJsonContent", id+ " . " + password);
		BasicHttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 30000);
		HttpConnectionParams.setSoTimeout(httpParameters, 30000);		
		
		HttpPost httpRequest = new HttpPost(url);
        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//        int  index = id.indexOf("@");		
//		System.out.println(id.substring(0, index));
//		String u_id= id.substring(0, index);
//		Log.e("!!!!!!!!!!!!!!", u_id);
		
        params1.add(new BasicNameValuePair("giant", "giant"));
        params1.add(new BasicNameValuePair("giant1", "giant1"));
        params1.add(new BasicNameValuePair("giant2", "giant2"));
        params1.add(new BasicNameValuePair("u_id", id));
        params1.add(new BasicNameValuePair("u_password", password));
        params1.add(new BasicNameValuePair("orgPw", orgPw));
        String result = "";       
        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(params1, HTTP.UTF_8));		            
            DefaultHttpClient  defaultHttpClient = new DefaultHttpClient(httpParameters);		            
            HttpResponse httpResponse = defaultHttpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result= EntityUtils.toString(httpResponse
                        .getEntity());
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
		
        return result;
//		String xml = null;
//		try {
//			// defaultHttpClient
//			DefaultHttpClient httpClient = new DefaultHttpClient();
//			HttpGet httpPost = new HttpGet(url);
//
//			HttpResponse httpResponse = httpClient.execute(httpPost);
//			HttpEntity httpEntity = httpResponse.getEntity();
//			xml = EntityUtils.toString(httpEntity);	
//			return xml;
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			return null;
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//			return null;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
	}

//	public List<AllProductListItem> getNewsListFromJson(String jsonContent,
//			List<AllProductListItem> inPhoneData) {
//		List<AllProductListItem> newData = new ArrayList<AllProductListItem>();
//		// Log.e("Start", " " + count);
//		try {
//			JSONArray jsonArray = new JSONArray(jsonContent);
//			// List<AllProductListItem> allProductList = new
//			// ArrayList<AllProductListItem>();
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject jsonObject = jsonArray.getJSONObject(i);
//				String id = jsonObject.getString("id");
//
//				String ver = jsonObject.getString("ver");
//
//				String imgUrl = jsonObject.getString("imgUrl");
//
//				String productUrl = jsonObject.getString("productUrl");
//
//				String pddata = jsonObject.getString("pddata");
//
//				Log.e("http", jsonObject.getString("imgUrl") + "");
//					Log.e("加 1 = " + newData.size(), "=" + jsonObject.getString("id") + "sss" + jsonObject.getString("imgUrl"));	
//					AllProductListItem news = new AllProductListItem(id, ver,
//							imgUrl, productUrl, pddata);
//					newData.add(news);				 
//			}
//			 for (int y = 0; y < inPhoneData.size(); y++) {
//				 
//				 for (int z = 0; z < newData.size(); z++) {
////			 Log.e("y", y+ " z =" + z);
//			 if (inPhoneData.get(y).getId().equals(newData.get(z).getId())) {
////			 Log.e("xx", inPhoneData.get(y).getVer() + "   ..  " +newData.get(z).getVer());
////			 Log.e("x..x", inPhoneData.get(y).getId() + "   ..  " +newData.get(z).getId());
//			 if (inPhoneData.get(y).getVer().equals(newData.get(z).getVer())){
//				 newData.set(z,new AllProductListItem(
//			 inPhoneData.get(y).getId(),
//			 inPhoneData.get(y).getVer(),
//			 inPhoneData.get(y).getImgUrl(),
//			 inPhoneData.get(y).getProductUrl(),
//			 inPhoneData.get(y).getData()));
//			 }
//			 }
//			 }
//			
//			 }
//			 Log.e("size", newData.size() + "");
//			return newData;
//		} catch (JSONException e) {
//			return null;
//		}
//	}


	
//	public List<AllProductListItem> getDefaultListFromJson(String jsonContent) {
//		List<AllProductListItem> newData = new ArrayList<AllProductListItem>();
//		try {
//			JSONArray jsonArray = new JSONArray(jsonContent);
//			Log.e("json new ", jsonArray.length()+ "");
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject jsonObject = jsonArray.getJSONObject(i);
//				String id = jsonObject.getString("id");
//
//				String ver = jsonObject.getString("ver");
//
//				String imgUrl = jsonObject.getString("imgUrl");
//
//				String productUrl = jsonObject.getString("productUrl");
//
//				String pddata = jsonObject.getString("pddata");
//				
////				Log.e("加 1 = " + newData.size(), "=" + jsonObject.getString("id") );	
//					AllProductListItem news = new AllProductListItem(id, ver,
//							imgUrl, productUrl, pddata);
//					newData.add(news);
//			}
////			Log.e("newData size = ", "" + newData.size() );
//			
//		} catch (JSONException e) {
//			return null;
//		}
//		return newData;
//	}
}
