package com.android.pet.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.micode.fileexplorer.FileExplorerTabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.giant.CloudPrint.CFMainF;
import com.giant.CloudPrint.CPMainF;
import com.giant.MrAction.IDCardFragment;
import com.giant.MrAction.MainWebFragment;
import com.giant.MrAction.MenuActivity;
import com.giant.MrAction.R;

@SuppressWarnings("unused")
public class NavigationActivity extends FragmentActivity {
	TextView flag;
	private DrawerLayout mDrawerLayout;
	ImageView home;
	Fragment fragment = null;
	TextView appname;
	ExpandableListView expListView;
	HashMap<String, List<String>> listDataChild;
	ExpandableListAdapter listAdapter;
	List<String> listDataHeader;
	TextView	tVflag1;
	SharedPreferences settings ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String fontPath = "fonts/Shadow Boxing.ttf";
		setContentView(R.layout.nmactivity_navigation);
		home = (ImageView)findViewById(R.id.home);
		home.setOnClickListener(homeOnclickListener);
		appname = (TextView)findViewById(R.id.appname);
		//Typeface tf = Typeface.createFromAsset(this.getAssets(), fontPath);
		//appname.setTypeface(tf);
		setUpDrawer();
		settings= getSharedPreferences(Tool.shareclass.prefName, Context.MODE_PRIVATE);





		//--
		geocoder = new Geocoder(this);
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationListenerNGPS = new GPSListener();
		locationListenerGPS = new GPSListener();
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNGPS);     locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);

		//---

		ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = CM.getActiveNetworkInfo();
		if (info == null || !info.isAvailable()){ //判斷是否有網路

		}else{
			Log.e("ConnectivityManager",info.getTypeName().toString());     // 目前以何種方式連線 [WIFI]
			Log.e("ConnectivityManager",info.getState().toString());         // 目前連線狀態 [CONNECTED]
			Log.e("ConnectivityManager",info.isAvailable()+"");      // 目前網路是否可使用 [true]
			Log.e("ConnectivityManager",info.isConnected()+"");      // 網路是否已連接 [true]
			Log.e("ConnectivityManager",info.isConnectedOrConnecting()+"");  // 網路是否已連接 或 連線中 [true]
			Log.e("ConnectivityManager",info.isFailover()+"");      // 網路目前是否有問題 [false]
			Log.e("ConnectivityManager",info.isRoaming()+"");        // 網路目前是否在漫遊中 [false]
		}

		//--
	}

	/**
	 *
	 * Get the names and icons references to build the drawer menu...
	 */
	private void setUpDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
		mDrawerLayout.setDrawerListener(mDrawerListener);
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		prepareListData();
		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
		// setting list adapter
		expListView.setAdapter(listAdapter);
		fragment = new MercuryFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		mDrawerLayout.closeDrawer(expListView);

		//expListView.setOnItemClickListener(mDrawerItemClickedListener);


		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Log.e("onGroupExpand",groupPosition+" ");

				switch (groupPosition) {
					case 0:
						fragment = new MercuryFragment();
						break;
					case 1:
						fragment = new IDCardFragment();
						break;
					case 2:
						Bundle bundle0 = new Bundle();
						bundle0.putString("URL","http://www.mr-action.com/mobile/workpaperindex.asp");
						fragment=new MainWebFragment();
						fragment.setArguments(bundle0);
						break;
					case 3:
						Bundle bundle = new Bundle();
						bundle.putString("URL","http://www.mr-action.com/mobile/AttendanceStatusindex.asp");
						fragment =new MainWebFragment();
						fragment.setArguments(bundle);
						break;
					case 4:
						fragment=new CPMainF();
						break;
					case 5:
						fragment=new CFMainF();
						break;
					case 6:
						Intent intent = new Intent(NavigationActivity.this,
								FileExplorerTabActivity.class);
						startActivity(intent);
						break;
					case 7:
						Intent intentqmsg1 = new Intent();
						PackageManager managermsg1 = getPackageManager();
						intentqmsg1 = managermsg1.getLaunchIntentForPackage("com.fsck.k9");
						intentqmsg1.addCategory(Intent.CATEGORY_LAUNCHER);
						startActivity(intentqmsg1);



						break;
					case 8:
						Intent intentqmsg = new Intent();
						PackageManager managermsg = getPackageManager();
						intentqmsg = managermsg.getLaunchIntentForPackage("com.giant.mrtt");
						intentqmsg.addCategory(Intent.CATEGORY_LAUNCHER);
						startActivity(intentqmsg);
						break;
					case 9:
						fragment=new Latestnews();
						break;
					default:
						break;
				}

				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
				mDrawerLayout.closeDrawer(expListView);

			}

		});


		expListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				Log.e("groupPosition",groupPosition+" ");
				Log.e("childPosition",childPosition+" ");

				switch (groupPosition) {
					case 0:
						switch (childPosition) {
							case 0:
								fragment = new MercuryFragment();
								break;
							case 1:
								fragment = new VenusFragment();
								break;
							case 2:
								fragment = new EarthFragment();
								break;
							default:
								break;
						}
						break;

					case 1:
						switch (childPosition) {
							case 0:
								fragment = new MercuryFragment();
								break;
							case 1:
								fragment = new VenusFragment();
								break;
							case 2:
								fragment = new EarthFragment();
								break;
							default:
								break;
						}
						break;

					case 2:
						switch (childPosition) {
							case 0:
								fragment = new MercuryFragment();
								break;
							case 1:
								fragment = new VenusFragment();
								break;
							case 2:
								fragment = new EarthFragment();
								break;
							default:
								break;
						}
						break;

					default:
						break;
				}
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
				mDrawerLayout.closeDrawer(expListView);
				return false;
			}
		});
	}

	View.OnClickListener homeOnclickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(mDrawerLayout.isDrawerOpen(expListView)){
				mDrawerLayout.closeDrawer(expListView);
			}else{
				mDrawerLayout.openDrawer(expListView);
			}
		}
	};



	// Catch the events related to the drawer to arrange views according to this
	// action if necessary...
	private DrawerListener mDrawerListener = new DrawerListener() {

		@Override
		public void onDrawerStateChanged(int status) {

		}

		@Override
		public void onDrawerSlide(View view, float slideArg) {

		}

		@Override
		public void onDrawerOpened(View view) {
		}

		@Override
		public void onDrawerClosed(View view) {
		}
	};

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("首頁");
		listDataHeader.add("員工識別證");
		listDataHeader.add("工作日報");
		listDataHeader.add("出勤記錄");
		listDataHeader.add("雲端列印");
		listDataHeader.add("網路傳真");
		listDataHeader.add("檔案管理");
		listDataHeader.add("郵件管理");
		listDataHeader.add("即時訊息");
		listDataHeader.add("最新公告");


		// Adding child data
		List<String> batsman = new ArrayList<String>();
		List<String> itemlist1 = new ArrayList<String>();

		List<String> itemlist2 = new ArrayList<String>();


		List<String> itemlist3 = new ArrayList<String>();


		List<String> itemlist4 = new ArrayList<String>();

		//listDataChild.put(listDataHeader.get(0), null);
		listDataChild.put(listDataHeader.get(0), batsman); // Header, Child data
		listDataChild.put(listDataHeader.get(1), batsman);
		listDataChild.put(listDataHeader.get(2), batsman);
		listDataChild.put(listDataHeader.get(3), batsman);
		listDataChild.put(listDataHeader.get(4), batsman);
		listDataChild.put(listDataHeader.get(5), batsman);
		listDataChild.put(listDataHeader.get(6), batsman);
		listDataChild.put(listDataHeader.get(7), batsman);
		listDataChild.put(listDataHeader.get(8), itemlist1);
		listDataChild.put(listDataHeader.get(9), itemlist1);


	}

	public class ExpandableListAdapter extends BaseExpandableListAdapter {

		private Context _context;
		private List<String> _listDataHeader; // header titles
		// child data in format of header title, child title
		private HashMap<String, List<String>> _listDataChild;


		public ExpandableListAdapter(Context context, List<String> listDataHeader,
									 HashMap<String, List<String>> listChildData) {
			this._context = context;
			this._listDataHeader = listDataHeader;
			this._listDataChild = listChildData;
		}

		@Override
		public Object getChild(int groupPosition, int childPosititon) {
			return this._listDataChild.get(this._listDataHeader.get(groupPosition))
					.get(childPosititon);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, final int childPosition,
								 boolean isLastChild, View convertView, ViewGroup parent) {

			final String childText = (String) getChild(groupPosition, childPosition);

			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.nmlist_item, null);
			}




			TextView txtListChild = (TextView) convertView
					.findViewById(R.id.lblListItem);

			txtListChild.setText(childText);



			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return this._listDataChild.get(this._listDataHeader.get(groupPosition))
					.size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return this._listDataHeader.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return this._listDataHeader.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
								 View convertView, ViewGroup parent) {
			String headerTitle = (String) getGroup(groupPosition);
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				if(headerTitle.equals("�P�P�M��")){
					convertView = infalInflater.inflate(R.layout.nmlist_grouptitle, null);
					convertView.setBackgroundColor(Color.GREEN);

				}else{
					convertView = infalInflater.inflate(R.layout.nmlist_group, null);
				}
			}

			TextView lblListHeader = (TextView) convertView
					.findViewById(R.id.lblListHeader);
			lblListHeader.setTypeface(null, Typeface.BOLD);
			lblListHeader.setText(headerTitle);


			Log.e("headerTitle",headerTitle);
			//	 flag= (TextView) convertView
			//			.findViewById(R.id.tVflag1);





			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

















	private LocationManager locationManager;
	GPSListener   locationListenerNGPS ;
	GPSListener   locationListenerGPS ;
	Geocoder geocoder;
	//----GPS

	class GPSListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			String strAddress = "";
			List places = null;
			try
			{
				double a1=location.getLatitude();
				settings.edit().putString("getLatitude", a1+" ").commit();
				settings.edit().putString("getLongitude", location.getLongitude()+"").commit();
				places = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);

			}catch(Exception e)        {
				e.printStackTrace();
			}
			if(places != null && places.size() > 0)
			{
				strAddress = ((Address)places.get(0)).getAddressLine(0)+ "," +
						((Address)places.get(0)).getAddressLine(1)+","+
						((Address)places.get(0)).getCountryName();
				settings.edit().putString("places", strAddress).commit();
				Log.e("strAddress",strAddress);
			}

		}
		@Override
		public void onProviderDisabled(String provider) {}
		@Override
		public void onProviderEnabled(String provider) {}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras){}
	}












}
