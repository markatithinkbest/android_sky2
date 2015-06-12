package com.giant.MrAction;

import java.util.ArrayList;
import java.util.Hashtable;
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

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.giant.Idcard.qrimageActivity;
import com.giant.MrAction.updataService.check_smsCodeDAO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.special.ResideMenu.ResideMenu;


@SuppressLint("NewApi")
public class IDCardFragment extends Fragment {

    private View parentView;
  

    SharedPreferences settings ;  
    ImageView companylogo;
    ImageView idphoto;
    ImageView status1;
    ImageView status2;
    ImageView status3;
	String DName;
	String emlyNO;
	String picstype;
	String NAME;
	String small_photo ;
	String esno ;
    Button bbbimage1;
    Button bbbimage2;
    Button bbbimage3;
    TextView idname;
    TextView idnumber;
    TextView iddepartment;
    TextView idjobtitle;
    TextView iddate;
	ImageLoader imageLoader = ImageLoader.getInstance();
	
    private String[] lunch = {"空閒", "工作量少", "一般", "忙碌", "非常忙"};
    int [] flags={R.drawable.btn_blue,R.drawable.btn_blue,R.drawable.btn_blue,
    		R.drawable.btn_blue,R.drawable.btn_blue};
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
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    
    	  initImageLoader(getActivity().getApplicationContext());
    	 
    	   
    	    
    	    
    	    
    	parentView = inflater.inflate(R.layout.idcard, container, false);   
    	settings= getActivity().getSharedPreferences(Tool.shareclass.prefName, Context.MODE_PRIVATE);
    	companylogo= (ImageView) parentView.findViewById(R.id.companylogo);
    	idphoto= (ImageView) parentView.findViewById(R.id.idphoto);
    	status1= (ImageView) parentView.findViewById(R.id.status1);
    	status2= (ImageView) parentView.findViewById(R.id.status2);
    	status3= (ImageView) parentView.findViewById(R.id.status3);
    	bbbimage1= (Button) parentView.findViewById(R.id.bbbimage1);
    	bbbimage2= (Button) parentView.findViewById(R.id.bbbimage2);
    	bbbimage3= (Button) parentView.findViewById(R.id.bbbimage3);
    	idname= (TextView) parentView.findViewById(R.id.idname);
    	idnumber= (TextView) parentView.findViewById(R.id.idnumber);
    	iddepartment= (TextView) parentView.findViewById(R.id.iddepartment);
    	idjobtitle= (TextView) parentView.findViewById(R.id.idjobtitle);
    	iddate= (TextView) parentView.findViewById(R.id.iddate);
    	
    	redata();
    	Environment.getDataDirectory();
    	
    	setUpViews();
    	updatainfoTask fff=new updatainfoTask();
    	fff.execute();
    	
    
        return parentView;
    }

    
   

    
    private static Bitmap getBitmapFromSDCard(String file) 
    {
       try  
       {
          String sd = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
          Bitmap bitmap = BitmapFactory.decodeFile(sd + "/" + file);
          return bitmap;
       }  
       catch (Exception e)  
       {  
          e.printStackTrace();  
          return null;  
       }  
    }
    
    
    
    
    
    public void redata(){
    	settings.edit().putString("companylogo", "�L").commit();
    	settings.edit().putString("idphoto", "�L").commit();
    	settings.edit().putString("status1", "0").commit();
    	settings.edit().putString("status2", "0").commit();
    	settings.edit().putString("status3", "0").commit();
    	settings.edit().putString("bbbimage1", "0").commit();
    	settings.edit().putString("bbbimage2", "0").commit();
    	settings.edit().putString("bbbimage3", "http://www.e-giant.com.tw/about_01.asp").commit();
    	
    	settings.edit().putString("idname", "�����v").commit();
    	settings.edit().putString("idnumber", "00000001").commit();
    	settings.edit().putString("iddepartment", "��o��").commit();
    	settings.edit().putString("idjobtitle", "���u").commit();
    	settings.edit().putString("iddate", "20150301").commit();
    	
    }
    
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	private void setUpViews() {
    //	idname.setText("�W��:"+settings.getString("idname", "�L���"));
    	//idnumber.setText("�u��:"+settings.getString("idnumber", "�L���"));
    	//iddepartment.setText("����:"+settings.getString("iddepartment", "�L���"));
    	//idjobtitle.setText("¾��:"+settings.getString("idjobtitle", "�L���"));
    //	iddate.setText("���:"+settings.getString("iddate", "�L���"));
    	
    	
    	
    
    	
    	
    	  companylogo.setImageBitmap(getBitmapFromSDCard("Image1.png"));
         
    
    	
    	String input = settings.getString("status1","0");
    	int afterConvert1 = Integer.parseInt(input);
    	input = settings.getString("status2", "0");
    	int afterConvert2 = Integer.parseInt(input);
    	input = settings.getString("status3", "0");
    	int afterConvert3 = Integer.parseInt(input);
    	status1.setImageResource(flags[afterConvert1]);
    	status2.setImageResource(flags[afterConvert2]);
    	status3.setImageResource(flags[afterConvert3]);
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	status3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Builder MyAlertDialog = new AlertDialog.Builder(getActivity());
				MyAlertDialog.setTitle("���u�@���A");
				DialogInterface.OnClickListener ListClick = new DialogInterface.OnClickListener()
				{
				public void onClick(DialogInterface dialog, int which) {
					status3.setImageResource(flags[which]);
					settings.edit().putString("status3", which+"").commit();
				}
				};
			
				DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener()
				{
				public void onClick(DialogInterface dialog, int which) {
				}
				}; 
				
				MyAlertDialog.setItems(lunch, ListClick);
				MyAlertDialog.setNeutralButton("����",OkClick );
				MyAlertDialog.show();     	
            }
        });
    	
    	bbbimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent intent = new Intent(getActivity(), qrimageActivity.class);
            	intent.putExtra("title", "�ӤH�W��");
            	//intent.putExtra("image", selectType);
            	startActivity(intent);
            }
        });
    	bbbimage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	 
            	Intent intent = new Intent(getActivity(), qrimageActivity.class);
            	intent.putExtra("title", "手動打卡");
            	//intent.putExtra("image", selectType);
            	startActivity(intent);
            	
            }
        });
    	bbbimage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	 Uri uri=Uri.parse(settings.getString("bbbimage3", ""));
                 Intent i=new Intent(Intent.ACTION_VIEW,uri);
                 startActivity(i);	
            }
        });
    	 
    	
    	
    	
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
					 DName = jsonObject.getString("DName");
					 iddepartment.setText(DName);
					 emlyNO = jsonObject.getString("emlyNO");
					 idnumber.setText(emlyNO);
					// picstype = jsonObject.getString("picstype");
					 NAME = jsonObject.getString("NAME");
					 idname.setText(NAME);
					 small_photo = jsonObject.getString("picstype");
					 esno = jsonObject.getString("esno");
					 idnumber.setText(esno);				 
					  imageLoader.displayImage("http://www.mr-action.com/15Employee/images/pics/"+small_photo, idphoto);
					 settings.edit().putString("NAME", NAME).commit();
					 settings.edit().putString("esno", esno).commit();	
					 settings.edit().putString("DName", DName).commit();	
		 
			}			
				
			} catch (JSONException e) {
							
			}
		
		}
		@Override
		protected void onProgressUpdate(Integer... progress) {
		}
	}

}
