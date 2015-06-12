package com.giant.initmember;

import java.util.UUID;

import com.android.pet.view.NavigationActivity;
import com.giant.MrAction.GCMIntentService;
import com.giant.MrAction.MenuActivity;
import com.giant.MrAction.R;
import com.google.android.gcm.GCMRegistrar;

import Tool.shareclass;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

public class GuiderActivity extends Activity implements OnItemSelectedListener {

    private View mViewDoor = null;
    private ImageView mImageLeft = null;
    private ImageView mImageRight = null;
    private XyzGallery mGallery = null;
    private GalleryAdapter mAdapter = null;
    private PageControlView mIndicateView = null;
    final private String prefName = "Shoes";
	protected SharedPreferences settings;
    private static final String FLAG_FIRST_LOGIN = "first";

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whats_news);
        settings = getSharedPreferences(prefName, Context.MODE_PRIVATE);
        
        
        
        TelephonyManager mTelManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		   shareclass.moblie=Build.MODEL;
		 //取得手機號碼 ，因哭狗隱私政策問題現在無法獲取只會得到null
		   shareclass.lineNumber = mTelManager.getLine1Number();

		 //取得手機IMEI碼
		 shareclass.mpIMEI = mTelManager.getDeviceId();
		 Tool.shareclass.registrationId=settings.getString("registrationId", "");
			
		    String UUID__ = settings.getString("UUID", "");
		    if(UUID__.isEmpty() == true || UUID__ == null){
		    	settings.edit().putString("UUID", UUID.randomUUID().toString()).commit();
		    }
		
		    GCMRegistrar.checkDevice(this);
		    GCMRegistrar.checkManifest(this);

		   Log.i("TAG", "Registering device");
		    // Retrive the sender ID from GCMIntentService.java
		    // Sender ID will be registered into GCMRegistrar
		    GCMRegistrar.register(GuiderActivity.this,
		    		GCMIntentService.SENDER_ID);   
		    Boolean QQQQ=settings.getBoolean("login",false);
	        if(QQQQ){   	
	        	Intent intent = new Intent(GuiderActivity.this, NavigationActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);	
				GuiderActivity.this.finish();
	        }
		    
		    
		    
		    
		    
		    
		    
		    
        
        mGallery = (XyzGallery) findViewById(R.id.what_news_gallery);
        mAdapter = new GalleryAdapter(this);
        mGallery.setFadingEdgeLength(0);
        mGallery.setSpacing(-1);
        mGallery.setAdapter(mAdapter);
        mGallery.setOnItemSelectedListener(this);

        mIndicateView = (PageControlView) findViewById(R.id.what_news_page_control);
        mIndicateView.setIndication(mGallery.getCount(), 0);
        mViewDoor = findViewById(R.id.mm_door);
        mImageLeft = (ImageView) findViewById(R.id.mm_left);
        mImageRight = (ImageView) findViewById(R.id.mm_right);
    }

    private class GalleryAdapter extends BaseAdapter implements
            OnClickListener, AnimationListener {

        private Context mContext;
        private LayoutInflater mInfater = null;

        private int[] mLayouts = new int[] {
                R.layout.init001,
                R.layout.init002,
                R.layout.init003,
                 };

        public GalleryAdapter(Context ctx) {
            mContext = ctx;
            mInfater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mLayouts.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return Integer.valueOf(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (convertView == null) {
                convertView = mInfater.inflate(mLayouts[position], null);
                if (position == 2) {
                    Button btn = (Button) convertView
                            .findViewById(R.id.buttonstart1);
                    btn.setOnClickListener(this);
                }
            }
            return convertView;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mViewDoor.setVisibility(View.VISIBLE);
            mImageLeft.startAnimation(setAnimation(R.anim.slide_left));
            mImageRight.startAnimation(setAnimation(R.anim.slide_right));
        }

        private Animation setAnimation(int resId) {
            Animation anim = AnimationUtils.loadAnimation(mContext, resId);
            anim.setAnimationListener(this);
            return anim;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            mImageLeft.setVisibility(View.GONE);
            mImageRight.setVisibility(View.GONE);
            SharedPreferences sp = PreferenceManager
                    .getDefaultSharedPreferences(mContext);
            Editor edit = sp.edit();
            edit.putBoolean(FLAG_FIRST_LOGIN, false);
            edit.commit();
            
            Intent intent = new Intent(GuiderActivity.this, LoginActivity.class);
			//intent.putExtra("state", "car");
			startActivity(intent); 
            GuiderActivity.this.finish();
            
            
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        if (mIndicateView != null) {
            mIndicateView.setIndication(parent.getCount(), position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }

}
