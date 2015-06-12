package com.giant.MrAction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import net.micode.fileexplorer.FileExplorerTabActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.giant.CloudPrint.CPMainF;
import com.giant.Idcard.PunchCard;
import com.giant.Jobpaper.JBPA;
import com.giant.initmember.GuiderActivity;
import com.giant.initmember.LoginActivity;
import com.giant.initmember.MemberActivity;
import com.giant.meetRecord.MRM1;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MenuActivity extends FragmentActivity implements
		View.OnClickListener,View.OnLongClickListener{

	private ResideMenu resideMenu;
	private MenuActivity mContext;
	private ResideMenuItem itemHome;
	private ResideMenuItem itemProfile;
	private ResideMenuItem item1;
	private ResideMenuItem item2;
	private ResideMenuItem item3;
	private ResideMenuItem item4;
	private ResideMenuItem item5;
	private ResideMenuItem item6;
	private ResideMenuItem item7;
	private ResideMenuItem item8;
	
	private ResideMenuItem item15;

	private ResideMenuItem itemCalendar;
	private ResideMenuItem itemSettings;

	private ResideMenuItem item16;
	private ResideMenuItem item17;
	private ResideMenuItem item18;
	private ResideMenuItem item19;
	private ResideMenuItem item20;
	private ResideMenuItem item21;
	private ResideMenuItem item22;
	private ResideMenuItem item23;
	private ResideMenuItem item24;
	final private String prefName = "Shoes";
	protected SharedPreferences settings;
	/**
	 * Called when the activity is first created.
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;
		setUpMenu();
		if (savedInstanceState == null)
			changeFragment(new HomeFragment());
		//timmer();

		new Thread(runnable).start();
		//-----------
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.cacheOnDisc(true)
		//.imageScaleType(ImageScaleType.EXACTLY ) 
		.bitmapConfig(Bitmap.Config.RGB_565)// 防止内存溢出的，图片太多就这这个。还有其他设置
		//如Bitmap.Config.ARGB_8888
		//.showImageOnLoading(R.drawable.loading02)   //默认图片       
		  //            .showImageForEmptyUri(R.drawable.empty_photo)    //url爲空會显示该图片，自己放在drawable里面的
		             // .showImageOnFail(R.drawable.error)// 加载失败显示的图片
		//.displayer(new RoundedBitmapDisplayer(5))  //圆角，不需要请删除
		                         .build();  

		
		
		
		
		ConnectivityManager mConnectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

		//如果未連線的話，mNetworkInfo會等於null
		if(mNetworkInfo != null)
		{
		    //網路是否已連線(true or false)
		    mNetworkInfo.isConnected();
		    //網路連線方式名稱(WIFI or mobile)
		    mNetworkInfo.getTypeName();
		    //網路連線狀態
		    mNetworkInfo.getState();
		    //網路是否可使用
		    mNetworkInfo.isAvailable();
		    //網路是否已連接or連線中
		    mNetworkInfo.isConnectedOrConnecting();
		    //網路是否故障有問題
		    mNetworkInfo.isFailover();
		    //網路是否在漫遊模式
		    mNetworkInfo.isRoaming();
		//Log.e("mNetworkInfo",mNetworkInfo.getReason());
		Log.e("mNetworkInfo",mNetworkInfo.getSubtypeName());
		Log.e("mNetworkInfo",mNetworkInfo.getExtraInfo());
		}
		
		
		
		 
		
		
		
		
		
		
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
		this)
		//.memoryCacheExtraOptions(480, 800)// 缓存在内存的图片的宽和高度
		.memoryCache(new WeakMemoryCache()) 
		.memoryCacheSize(2 * 1024 * 1024) //缓存到内存的最大数据
		.discCacheSize(50 * 1024 * 1024).  //缓存到文件的最大数据
		defaultDisplayImageOptions(options).  //上面的options对象，一些属性配置
		build();
		ImageLoader.getInstance().init(config); //初始化
		
		
		
		
	          Intent intent = new Intent(this, updataService.class);
	           startService(intent);
	           
	           
	           
	           
	           
	           
	          
	           
	}

	
	
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			logogetBitmapFromURL("http://www.e-giant.com.tw/tw_img/index_02.jpg");
			idgetBitmapFromURL("http://upload.wikimedia.org/wikipedia/commons/0/00/Title_-_Alias_la_Patata.jpg");
			Log.e("GG", "摰??");
		}
	};

	private Bitmap logogetBitmapFromURL(String imageUrl) {
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(input);

			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath().toString();

			File file = new File(path, "/Image1.png");

			Log.e("path", path + "/Image1.png");
			FileOutputStream out = new FileOutputStream(file);

			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);

			out.flush();
			out.close();

			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Bitmap idgetBitmapFromURL(String imageUrl) {
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(input);
			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath().toString();
			File file = new File(path, "/Image2.png");
			Log.e("path", path + "/Image2.png");
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();

			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	

	private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.id_bg);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);

		// create menu items;
		item15 = new ResideMenuItem(this, R.drawable.icon_home, "首頁");
		itemHome = new ResideMenuItem(this, R.drawable.icon_home, "員工識別證");
		itemProfile = new ResideMenuItem(this, R.drawable.icon_profile,
				"出勤狀況");
		item1 = new ResideMenuItem(this, R.drawable.icon_home, "工作日報");
		item2 = new ResideMenuItem(this, R.drawable.icon_home, "會議紀錄");
		item3 = new ResideMenuItem(this, R.drawable.icon_home, "專案管理");
		item4 = new ResideMenuItem(this, R.drawable.icon_home, "帳款管理");
		item5 = new ResideMenuItem(this, R.drawable.icon_home, "工作時程");
		item6 = new ResideMenuItem(this, R.drawable.icon_home, "共用資源");
		item7 = new ResideMenuItem(this, R.drawable.icon_home, "規章查詢");
		item8 = new ResideMenuItem(this, R.drawable.icon_home, "文件申請");
	
		itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar,
				"提醒事項");
		
		item16 = new ResideMenuItem(this, R.drawable.icon_home, "展示中心");
		item17 = new ResideMenuItem(this, R.drawable.icon_home, "即時訊息");
		item18 = new ResideMenuItem(this, R.drawable.icon_home, "會員中心");
		item19 = new ResideMenuItem(this, R.drawable.icon_home, "個人行程");
		item20 = new ResideMenuItem(this, R.drawable.icon_home, "活動中心");
		item21 = new ResideMenuItem(this, R.drawable.icon_home, "雲端列印");
		item22 = new ResideMenuItem(this, R.drawable.icon_home, "檔案管理");
		item23 = new ResideMenuItem(this, R.drawable.icon_home, "雲端空間");
		item24 = new ResideMenuItem(this, R.drawable.icon_home, "電子信箱");
		itemSettings = new ResideMenuItem(this, R.drawable.icon_settings,
				"系統設定");
		item1.setOnClickListener(this);
		item2.setOnClickListener(this);
		item3.setOnClickListener(this);
		item4.setOnClickListener(this);
		item5.setOnClickListener(this);
		item6.setOnClickListener(this);
		item7.setOnClickListener(this);
		item8.setOnClickListener(this);
		
		itemHome.setOnClickListener(this);
		itemProfile.setOnClickListener(this);
		itemCalendar.setOnClickListener(this);
		itemSettings.setOnClickListener(this);
		item16.setOnClickListener(this);
		item17.setOnClickListener(this);
		item18.setOnClickListener(this);
		item19.setOnClickListener(this);
		item20.setOnClickListener(this);
		item21.setOnClickListener(this);
		item22.setOnClickListener(this);
		item23.setOnClickListener(this);
		item24.setOnClickListener(this);
		
		
		
		
		
		
		
		item1.setOnLongClickListener(this);
		item2.setOnLongClickListener(this);
		item3.setOnLongClickListener(this);
		item4.setOnLongClickListener(this);
		item5.setOnLongClickListener(this);
		item6.setOnLongClickListener(this);
		item7.setOnLongClickListener(this);
		item8.setOnLongClickListener(this);
		
		itemHome.setOnLongClickListener(this);
		itemProfile.setOnLongClickListener(this);
		itemCalendar.setOnLongClickListener(this);
		itemSettings.setOnLongClickListener(this);
		item16.setOnLongClickListener(this);
		item17.setOnLongClickListener(this);
		item18.setOnLongClickListener(this);
		item19.setOnLongClickListener(this);
		item20.setOnLongClickListener(this);
		item21.setOnLongClickListener(this);
		item22.setOnLongClickListener(this);
		item23.setOnLongClickListener(this);
		item24.setOnLongClickListener(this);
		// 撌?
		resideMenu.addMenuItem(item15, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(item1, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(item2, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(item3, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(item4, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(item5, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(item6, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(item7, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(item8, ResideMenu.DIRECTION_LEFT);
	
		
		// ?
		resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
		
		resideMenu.addMenuItem(item16, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(item17, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(item18, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(item19, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(item20, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(item21, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(item22, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(item23, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(item24, ResideMenu.DIRECTION_RIGHT);
		
		resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);
		
		
		// You can disable a direction by setting ->
		// resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		findViewById(R.id.title_bar_left_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
		findViewById(R.id.title_bar_right_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
					}
				});
		
		
		
		
		
		
		
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View view) {

		if (view == itemHome) {
			changeFragment(new IDCardFragment());
		} else if (view == itemProfile) {
			Bundle bundle = new Bundle();
			bundle.putString("URL","http://www.mr-action.com/mobile/AttendanceStatusindex.asp");
			MainWebFragment QQ=new MainWebFragment();
			QQ.setArguments(bundle);
			changeFragment(QQ);
		} else if (view == itemCalendar) {
			changeFragment(new CalendarFragment());
		} else if (view == itemSettings) {
			changeFragment(new SettingsFragment());
		}else if(view == item1){
			Bundle bundle = new Bundle();
			bundle.putString("URL","http://www.mr-action.com/mobile/workpaperindex.asp");
			MainWebFragment QQ=new MainWebFragment();
			QQ.setArguments(bundle);
			changeFragment(QQ);
		}else if(view == item3){
			Bundle bundle = new Bundle();
			bundle.putString("URL","http://www.mr-action.com/mobile/projectMangementindex.asp");
			MainWebFragment QQ=new MainWebFragment();
			QQ.setArguments(bundle);
			changeFragment(QQ);
		}else if(view == item4){
			Bundle bundle = new Bundle();
			bundle.putString("URL","http://www.mr-action.com/mobile/AccountsMangementindex.asp");
			MainWebFragment QQ=new MainWebFragment();
			QQ.setArguments(bundle);
			changeFragment(QQ);
		}else if(view == item6){
			Bundle bundle = new Bundle();
			bundle.putString("URL","http://www.mr-action.com/mobile/ResourceCenterindex.asp");
			MainWebFragment QQ=new MainWebFragment();
			QQ.setArguments(bundle);
			changeFragment(QQ);
		}else if(view == item7){
			Bundle bundle = new Bundle();
			bundle.putString("URL","http://www.mr-action.com/mobile/RegulationSearchindex.asp");
			MainWebFragment QQ=new MainWebFragment();
			QQ.setArguments(bundle);
			changeFragment(QQ);
		}else if(view == item8){
			Bundle bundle = new Bundle();
			bundle.putString("URL","http://www.mr-action.com/mobile/FileAnApplicationindex.asp");
			MainWebFragment QQ=new MainWebFragment();
			QQ.setArguments(bundle);
			changeFragment(QQ);
		}else if(view == item16){
			Bundle bundle = new Bundle();
			bundle.putString("URL","http://www.mr-action.com/mobile/ExhibitionCenterindex.asp");
			MainWebFragment QQ=new MainWebFragment();
			QQ.setArguments(bundle);
			changeFragment(QQ);
		}else if(view == item20){
			Bundle bundle = new Bundle();
			bundle.putString("URL","http://www.mr-action.com/mobile/ActivityCenterindex.asp");
			MainWebFragment QQ=new MainWebFragment();
			QQ.setArguments(bundle);
			changeFragment(QQ);
		}
		
		else if(view == item2){//會議記錄
			changeFragment(new MRM1());
			
		}else if(view == item18){
			changeFragment(new MemberActivity());
			
		}else if (view == item22) {// ??辣蝞∠??
			Intent intent = new Intent(MenuActivity.this,
					FileExplorerTabActivity.class);
			startActivity(intent);
			Log.e("????", "QQQ");
		}else if (view == item21) {// ??辣蝞∠??
			changeFragment(new CPMainF());
			//Intent intent = new Intent(MenuActivity.this,
				//	com.giant.CloudPrint.MainActivity.class);
			//startActivity(intent);
		}else if(view == item21){
			
		}else if(view == item24){
			Intent intent = new Intent();
			  PackageManager manager = getPackageManager();
			  intent = manager.getLaunchIntentForPackage("com.fsck.k9");
			  intent.addCategory(Intent.CATEGORY_LAUNCHER);
			  startActivity(intent);
		}
		
		
		
		
		
		
		
		
		
		
		resideMenu.closeMenu();
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			//Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void closeMenu() {
			//Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
		}
	};

	private void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	// What good method is to access resideMenu??蕭
	public ResideMenu getResideMenu() {
		return resideMenu;
	}

	@Override
	public boolean onLongClick(View v) {
		//**
		
		 settings = getSharedPreferences(prefName, Context.MODE_PRIVATE);
		AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setMessage("是否登出?")
          .setPositiveButton("是", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
            // 左方按鈕方法
            settings.edit().putBoolean("login", false).commit();
        	
            Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);	
			finish();
        	

        	
           }
          })
          .setNegativeButton("否", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
            // 右方按鈕方法          
           }
          });
        AlertDialog logout_dialog = builder.create();
        logout_dialog.show();
			
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
