package com.giant.Idcard;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





import com.giant.MrAction.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



public class qrimageActivity extends Activity {
	ImageView imageViewqrcode;
	 SharedPreferences settings ;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qrcodeimg);
 		//actionBar.setDisplayHomeAsUpEnabled(true);
 		setTitle("手動打卡");		
 		imageViewqrcode= (ImageView) findViewById(R.id.imageViewqrcode);
 		settings= getSharedPreferences(Tool.shareclass.prefName, Context.MODE_PRIVATE);
    	
	
 		
		 createQRImage(settings.getString("NAME", "")+";"+settings.getString("esno", "")+
				";"+ settings.getString("DName", ""));
	}	
	
	
	
	
	 public void createQRImage(String url)
	    {
	    	
	    	int QR_WIDTH=200,QR_HEIGHT=200;
	        try
	        {
	            //判断URL合法性
	            if (url == null || "".equals(url) || url.length() < 1)
	            {
	                return;
	            }
	            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
	            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	            //图像数据转换，使用了矩阵转换
	            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
	            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
	            //下面这里按照二维码的算法，逐个生成二维码的图片，
	            //两个for循环是图片横列扫描的结果
	            for (int y = 0; y < QR_HEIGHT; y++)
	            {
	                for (int x = 0; x < QR_WIDTH; x++)
	                {
	                    if (bitMatrix.get(x, y))
	                    {
	                        pixels[y * QR_WIDTH + x] = 0xff000000;
	                    }
	                    else
	                    {
	                        pixels[y * QR_WIDTH + x] = 0xffffffff;
	                    }
	                }
	            }
	            //生成二维码图片的格式，使用ARGB_8888
	            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
	            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
	            //显示到一个ImageView上面
	            imageViewqrcode.setImageBitmap(bitmap);
	        }
	        catch (WriterException e)
	        {
	            e.printStackTrace();
	        }
	    }
	
	
	
	
	
	
	
	
	
	
	
}
