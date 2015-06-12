package com.giant.News;

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

import android.util.Log;



public class NewsDAO {
	public String getJsonContent() {
		BasicHttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 20000);
		HttpConnectionParams.setSoTimeout(httpParameters, 20000);
		// HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
		HttpPost httpRequest = new HttpPost("http://www.comfort-mobility.com/app/news.php");
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		params1.add(new BasicNameValuePair("lang", "TW"));
//		params1.add(new BasicNameValuePair("giant", "giant"));
		String result = "";
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params1, HTTP.UTF_8));
			DefaultHttpClient defaultHttpClient = new DefaultHttpClient(httpParameters);
			HttpResponse httpResponse = defaultHttpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "x";
		}
		// Log.e("ddddddds", result + " ver =" + ver );
		return result;
	}

	public ArrayList<NewsItem> getNewsListFromJson(String jsonContent) {
		ArrayList<NewsItem> newData = new ArrayList<NewsItem>();

		try {
			JSONArray jsonArray = new JSONArray(jsonContent);
//			Log.e("jsonArray", "TW  " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
//				Log.e("jsonArray", "TW  " + jsonArray.length());
				
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String publishDate = "";
				String subject = "";
//				String subject_TW = "";
				String app_anpic = "";	
				String target = "";
				int rowId = 0;
				String medias = "";
				String fields = "";
				if(jsonObject.isNull( "target" ) == false)//語系們沒有的話
					target = jsonObject.getString("target");
				else
					continue;
//				Log.e("target", target + " .");
				//使用「:」進行切割
//				int count = 0;
//				String[] locs = target.split(",");
//				for(String loc:locs){
				
							subject = jsonObject.getString("subject_TW");
						
//				}
				
//				if(count == 0)//沒有要顯示的語言
//					continue;
				
				if(jsonObject.isNull( "publishDate" ) == false)
					publishDate = jsonObject.getString("publishDate");
								
//				Log.e("count",  " .. publishDate " + publishDate);
				
//				if(jsonObject.isNull( "subject" ) == false)
//					subject = jsonObject.getString("subject");
				
//				Log.e("count",  " .. subject " + subject);
				
//				if(jsonObject.isNull( "subject_TW" ) == false)
//					subject_TW = jsonObject.getString("subject_TW");
				if(jsonObject.isNull( "rowId" ) == false)
					rowId = jsonObject.getInt("rowId");	
				
//				Log.e("count",  " .. rowId " + rowId);
				
				if(jsonObject.isNull( "app_anpic" ) == false)
					app_anpic = jsonObject.getString("app_anpic");				
				
//				Log.e("count",  " .. app_anpic " + app_anpic);
				
				if(jsonObject.isNull( "fields" ) == false)
					fields = jsonObject.getString("fields");
				
				if(jsonObject.isNull( "medias" ) == false)
					medias = jsonObject.getString("medias");
				
				NewsItem news = new NewsItem(publishDate, subject, app_anpic, rowId, medias, fields);
				newData.add(news);
			}
//			Log.e("NewsActivity", "..NewsActivity QQ111"  + newData.size());
			return newData;
		} catch (JSONException e) {
			Log.e("error", "error"  + e.toString());
			return newData;
		}
	}
}
