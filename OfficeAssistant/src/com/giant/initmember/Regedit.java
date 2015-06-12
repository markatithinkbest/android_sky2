package com.giant.initmember;

import java.util.ArrayList;

import com.giant.MrAction.R;

import Tool.EmailValidator;
import Tool.ShowDialog;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Regedit extends Activity {

	private Button btnSent;

	private EditText etName;
	private EditText etAccount;
	private EditText etPassowrd;
	private EditText etPassowrd1;
	private EditText etPhone;
	private EditText etCellPhone;
	private EditText etAddr;
	private static Button btnSetDate;
	private Spinner spinner1;
	private Spinner spinner2;
	private String place;
	private String place2;
	private static int mYear = 1980;
	private static int mMonth = 00;
	private static int mDay = 1;

	final private String prefName = "Shoes";
	protected SharedPreferences settings;
	
	private ArrayList<String[]> list;
	private ArrayAdapter<String> adapterPlace;

	private String name;
	private String mail;
	private String password;
	private String sex = "F";
	private String epaper = "Y";
	private String phone;
	private String mobilePhone;
//	private String city;
//	private String area;
//	private String address;
	private String birthday;
	private Dialog dialog;
	private String resultQ;
	private CheckBox cbFemale;
	private CheckBox cbMale;
	private CheckBox cbReceveY;
	private CheckBox cbReceveN;
	private Intent i;
	private CheckBox  sccc1,sccc2,sccc3;
	private MyAsyncTask myTask;
String vers="企業";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_regedit);
		setAddr();
		findViews();
	
	}

	private void findViews() {
		settings = getSharedPreferences(prefName, Context.MODE_PRIVATE);
		
		sex = "F";
		epaper = "Y";
		 

		etName = (EditText) findViewById(R.id.etName);
		etAccount = (EditText) findViewById(R.id.etAccount);
		etPassowrd = (EditText) findViewById(R.id.etPassowrd);
		etPassowrd1 = (EditText) findViewById(R.id.etPassowrd1);
		etPhone = (EditText) findViewById(R.id.etPhone);
		etCellPhone = (EditText) findViewById(R.id.etCellPhone);
		etAddr = (EditText) findViewById(R.id.etAddr);

		btnSetDate = (Button) findViewById(R.id.btnSetDate);
		btnSetDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(Regedit.this, d, 1980,1,01).show();
			}
		});

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this, R.array.city_array, R.layout.spinner_item);
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
		spinner1.setAdapter(adapter);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		 spinner1.setAdapter(new ArrayAdapter(this, R.layout.spinner_dropdown_item));
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				place = arg0.getItemAtPosition(arg2).toString();
				adapterPlace = new ArrayAdapter<String>(Regedit.this, R.layout.spinner_item, list.get(arg2));
				adapterPlace.setDropDownViewResource(R.layout.spinner_dropdown_item);
				spinner2.setAdapter(adapterPlace);
				spinner2.setPrompt("請選擇鄕鎮市區");
				adapterPlace.notifyDataSetChanged();
				
				Log.e(place, place);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		adapterPlace = new ArrayAdapter<String>(this, R.layout.spinner_item,list.get(0));
		adapterPlace.setDropDownViewResource(R.layout.spinner_dropdown_item);
		spinner2.setAdapter(adapterPlace);
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {				
				place2 = arg0.getItemAtPosition(arg2).toString();Log.e(place2, place2);
				// ((TextView)
				// arg0.getChildAt(0)).setTextColor(Color.parseColor("#F000FF"));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		updateDisplay();
//		etName.setText("錢錢");
//		etAccount.setText("aaa@yahoo.com");
//		etPassowrd.setText("111111");
//		etPassowrd1.setText("111111");
		btnSent = (Button) findViewById(R.id.btnSent);
		btnSent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				name = etName.getText().toString().trim();
				if (name.length() < 2) {
					Toast.makeText(getApplicationContext(), "會員姓名 少於2個",
							Toast.LENGTH_LONG).show();
					return;
				}

				mail = etAccount.getText().toString().trim();
				EmailValidator email = new EmailValidator();
				if (false == email.validate(mail)) {
					Toast.makeText(getApplicationContext(), "E-MAIL格式錯誤",
							Toast.LENGTH_LONG).show();
					return;
				}

				password = etPassowrd.getText().toString().trim();
				if (password.length() < 6) {
					Toast.makeText(getApplicationContext(), "密碼少於6個",
							Toast.LENGTH_LONG).show();
					return;
				}

				String password1 = etPassowrd1.getText().toString().trim();
				if (password1.length() < 6) {
					Toast.makeText(getApplicationContext(), "密碼少於6個",
							Toast.LENGTH_LONG).show();
					return;
				}

				if (password1.equals(password) == false) {
					Toast.makeText(getApplicationContext(), "密碼比對失敗",
							Toast.LENGTH_LONG).show();
					return;
				}
				
				if (etPhone.getText().toString().equals("") == true) {
					Toast.makeText(getApplicationContext(), "請輸入電話",
							Toast.LENGTH_LONG).show();
					return;
				}
				
				if (etCellPhone.getText().toString().equals("") == true) {
					Toast.makeText(getApplicationContext(), "請輸入手機電話",
							Toast.LENGTH_LONG).show();
					return;
				}
				
				if(etAddr.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "請輸入地址",
							Toast.LENGTH_LONG).show();
					return;
				}
				phone = etPhone.getText().toString().trim();
				mobilePhone = etCellPhone.getText().toString().trim();
				birthday = mYear + "-" + (mMonth+1) + "-" + mDay;
				Log.e("birthdaybirthdaybirthday", birthday);
//				city = "";
//				city = place + place2 + etAddr.getText().toString().trim();
				myTask = new MyAsyncTask();
				myTask.execute();
			}
		});
		
		cbFemale = (CheckBox) findViewById(R.id.cbFemale);
		cbMale = (CheckBox) findViewById(R.id.cbMale);
		sccc1= (CheckBox) findViewById(R.id.sccc1);
		sccc2= (CheckBox) findViewById(R.id.sccc2);
		sccc3= (CheckBox) findViewById(R.id.sccc3);
		sccc1.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked == true){
					sccc1.setChecked(true);
					sccc2.setChecked(false);
					sccc3.setChecked(false);
					vers = "01";
				}
			}
		});
		sccc2.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked == true){
					sccc1.setChecked(false);
					sccc2.setChecked(true);
					sccc3.setChecked(false);
					vers = "02";
				}
			}
		});
		sccc3.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked == true){
					sccc1.setChecked(false);
					sccc2.setChecked(false);
					sccc3.setChecked(true);
					vers = "99";
				}
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		cbFemale.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked == true){
					cbFemale.setChecked(true);
					cbMale.setChecked(false);
					sex = "F";
				}
			}
		});
		
		cbMale.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked == true){
					cbFemale.setChecked(false);
					cbMale.setChecked(true);
					sex = "M";					
				}
			}
		});
		cbReceveY = (CheckBox) findViewById(R.id.cbReceveY);
		cbReceveY.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked == true){
					cbReceveN.setChecked(false);
					cbReceveY.setChecked(true);
					epaper = "Y";
				}
			}
		});
		cbReceveN = (CheckBox) findViewById(R.id.cbReceveN);
		cbReceveN.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked == true){
					cbReceveY.setChecked(false);
					cbReceveN.setChecked(true);
					epaper = "N";
				}
			}
		});
	}

	private class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(Void... arg0) {
			RegeditDAO dao = new RegeditDAO();
			String receive = settings.getString("receive", "YES");
			if(receive.equals("YES")){
				receive = "0";
			}else{
				receive = "1";
			}
			resultQ = dao.getJsonContent(name, mail, password, phone,
					mobilePhone, place, place2,etAddr.getText().toString().trim(),  birthday, sex, epaper, settings.getString("ID", "1"),receive,vers);
			// if(resultQ.equals("註冊成功")){
			// resultQ = "註冊成功";
			// }
			return null;
		}

		@Override
		protected void onPreExecute() {
			dialog = new Dialog(Regedit.this, R.style.TTT);
			dialog.setContentView(R.layout.dialog_process);
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected void onPostExecute(Void result) {
			dialog.dismiss();
			SharedPreferences settings = getApplicationContext()
					.getSharedPreferences(prefName, Context.MODE_PRIVATE);
			if (resultQ != null)
				if (resultQ.equals("註冊成功")) {
					Toast.makeText(getApplicationContext(), resultQ, Toast.LENGTH_SHORT).show();

					// resultIntent.putExtra("result","result");
					setResult(RESULT_OK, i);
					int  index = mail.indexOf("@");		
					System.out.println(mail.substring(0, index));
					String u_id= mail.substring(0, index);
					//settings.edit().putString("login", "Yes").commit();
					settings.edit().putString("u_id", mail).commit();
					settings.edit().putString("u_pw", password).commit();
					Regedit.this.finish();
				} else {
					Toast.makeText(getApplicationContext(), resultQ, 	Toast.LENGTH_SHORT).show();
					//settings.edit().putString("login", "No").commit();
				}
			resultQ = "";
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
		}
	}

	// 將指定的日期顯示在TextView上。「mMonth + 1」是因為一月的值是0而非1
	private static void updateDisplay() {
		StringBuilder sb = new StringBuilder().append(mYear).append("-") .append(pad(mMonth + 1)).append("-").append(pad(mDay));
		btnSetDate.setText(sb);		
	}

	// 若數字有十位數，直接顯示；若只有個位數則補0後再顯示。例如7會改成07後再顯示
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	// 此Fragment為內部類別，必須宣告為static
	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new DatePickerDialog(getActivity(), this, mYear, mMonth,
					mDay);
		}

		// 日期挑選完成會呼叫此方法，並傳入選取的年月日
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			mYear = year;
			mMonth = month;
			mDay = day;
			updateDisplay();
		}
	}

	
      @Override
	protected void onPause() {
		ShowDialog.dismissDialog();
		if(myTask != null)
			myTask.cancel(true);
		super.onPause();
	}

	private DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	private void setAddr() {
		list = new ArrayList<String[]>();
		list.add(city1);
		list.add(city2);
		list.add(city3);
		list.add(city4);
		list.add(city5);
		list.add(city6);
		list.add(city7);
		list.add(city8);
		list.add(city9);
		list.add(city10);
		list.add(city11);
		list.add(city12);
		list.add(city13);
		list.add(city14);
		list.add(city15);
		list.add(city16);
		list.add(city17);
		list.add(city18);
		list.add(city19);
		list.add(city20);
		list.add(city21);
		list.add(city22);
		list.add(city23);
	}

	private String city1[] = { "仁愛區", "信義區", "中正區", "中山區", "安樂區", "暖暖區", "七堵區" };// 基隆

	private String city2[] = { "中正區", "大同區", "中山區", "松山區", "大安區", "萬華區", "信義區",
			"士林區", "北投區", "內湖區", "南港區", "文山區" };// 台北市

	private String city3[] = { "萬里區", "金山區", "板橋區", "汐止區", "深坑區", "石碇區", "瑞芳區",
			"平溪區", "雙溪區", "貢寮區", "新店區", "坪林區",
			"烏來區", // 新北
			"永和區", "中和區", "土城區", "三峽區", "樹林區", "鶯歌區", "三重區", "新莊區", "泰山區",
			"林口區", "蘆洲區", "五股區", "八里區", "淡水區", "三芝區", "石門區" };// 新北

	private String city4[] = { "中壢市", "平鎮市", "龍潭鄕", "楊梅市", "新屋鄕", "觀音鄕", "桃園市",
			"龜山鄕", "八德市", "大溪鎮", "復興鄕", "大園鄕", "蘆竹鄕" };// 桃園

	private String city5[] = { "東區", "北區", "香山區" };// 新竹

	private String city6[] = { "竹北市", "湖口鄕", "新豐鄕", "新埔鎮", "關西鎮", "芎林鄕", "寶山鄕",
			"竹東鎮", "五峰鄕", "橫山鄕", "尖石鄕", "北埔鄕", "峨眉鄕" };// 新竹縣

	private String city7[] = { "竹南鎮", "頭份鎮", "三灣鄕", "南庄鄕", "獅潭鄕", "後龍鎮", "通霄鎮",
			"苑裡鎮", "苗栗市", "造橋鄕", "頭屋鄕", "公館鄕", "大湖鄕", "泰安鄕", "銅鑼鄕", "三義鄕",
			"西湖鄕", "桌蘭鎮" };// 苗栗縣

	private String city8[] = { "中區", "東區", "南區", "西區", "北區", "北屯區", "西屯區",
			"南屯區", "太平區", "大里區", "霧峰區", "烏日區", "豐原區", "后里區", "石岡區", "東勢區",
			"和平區", "新社區", "潭子區", "大雅區", "神岡區", "大肚區", "沙鹿區", "龍井區", "梧棲區",
			"清水區", "大甲", "外埔區", "大安區" };// 台中市

	private String city9[] = { "彰化市", "芬園鄕", "花壇鄕", "秀水鄕", "鹿港鎮", "福興鄕", "線西鄕",
			"和美鎮", "伸港鄕", "員林鎮", "社頭鄕", "永靖鄕", "埔心鄕", "溪湖鎮", "大村鄕", "埔鹽鄕",
			"田中鎮", "北斗鎮", "田尾鄕", "埤頭鄕", "溪州鄕", "竹塘鄕", "二林鎮", "大城鄕", "芳苑鄕",
			"二水鄕" };// 彰化
	private String city10[] = { "南投市", "中寮鄕", "草屯鎮", "國姓鄕", "埔里鎮", "仁愛鄕",
			"名間鄕", "集集鎮", "水里鄕", "魚池鄕", "信義鄕", "竹山鎮", "鹿谷鄕" };// 南投
	private String city11[] = { "斗南鎮", "大埤鄕", "虎尾鎮", "土庫鎮", "褒忠鄕", "東勢鄕",
			"台西鄕", "崙背鄕", "麥寮鄕", "斗六市", "林內鄕", "古坑鄕", "莿桐鄕", "西螺鄕", "二崙鄕",
			"北港鎮", "水林鄕", "口湖鄕", "四湖鄕", "元長鄕" };// 雲林縣

	private String city12[] = { "東區", "西區" };// 嘉義市

	private String city13[] = { "番路鄕", "梅山鄕", "竹崎鄕", "阿里山鄕", "中埔鄕", "大埔鄕",
			"水上鄕", "鹿草鄕", "太保市", "朴子市", "東石鄕", "六腳鄕", "新港鄕", "民雄鄕", "大林鎮",
			"溪口鄕", "義竹鄕", "布袋鎮" };// 嘉義縣

	private String city14[] = { "中西區", "東區", "南區", "北區", "安平區", "安南區", "永康區",
			"歸仁區", "新化區", "左鎮區", "玉井區", "楠西區", "南化區", "仁德區", "關廟區", "龍崎區",
			"官田區", "麻豆區", "佳里區", "西港區", "七股區", "將軍區", "學甲區", "北門區", "新營區",
			"後壁區", "白河區", "東山區", "六甲區", "下營區", "柳營區", "鹽水區", "善化區", "大內區",
			"山上區", "新市區", "安定區" };// 台南市

	private String city15[] = { "新興區", "苓雅區", "鹽埕區", "鼓山區", "旗津區", "前鎮區",
			"三民區", "楠梓區", "小港區", "左營區", "仁武區", "大社區", "東沙群島", "南沙群島", "岡山區",
			"路竹區", "阿蓮區", "田寮區", "燕巢區", "橋頭區", "梓官區", "彌陀區", "永安區", "湖內區",
			"鳳山區", "大寮區", "林園區", "鳥松區", "大樹區", "旗山區", "美濃區", "六龜區", "內門區",
			"杉林區", "甲仙區", "桃源區", "那瑪夏區", "荿林區", "茄萣區" };// 高雄市

	private String city16[] = { "屏東市", "三地門鄕", "霧台鄕", "瑪家鄕", "九如鄕", "里港鄕",
			"高樹鄕", "鹽埔鄕", "長治鄕", "麟洛鄕", "竹田鄕", "內埔鄕", "萬丹鄕", "潮州鎮", "泰武鄕",
			"來義鄕", "萬巒鄕", "崁頂鄕", "新埤鄕", "南州鄕", "林邊鄕", "東港鎮", "琉球鄕", "佳冬鄕",
			"新園鄕", "枋寮鄕", "枋山鄕", "春日鄕", "獅子鄕", "車城鄕", "社丹鄕", "恆春鄕", "滿州鄕" };// 屏東

	private String city17[] = { "台東市", "綠島鄕", "蘭嶼鄕", "延平鄕", "卑南鄕", "鹿野鄕",
			"關山鎮", "海端鄕", "池上鄕", "東河鄕", "成功鎮", "長濱鄕", "太麻里鄕", "金峰鄕", "大武鄕",
			"達仁鄕" };// 台東

	private String city18[] = { "花蓮市", "新城鄕", "秀林鄕", "吉安鄕", "壽豐鄕", "鳳林鎮",
			"光復鄕", "豐濱鄕", "瑞穗鄕", "萬榮鄕", "玉里鎮", "卓溪鄕", "富里鄕" };// 花蓮

	private String city19[] = { "宜蘭市", "頭城鎮", "礁溪鄕", "壯圍鄕", "員山鄕", "羅東鎮",
			"三星鄕", "大同鄕", "五結鄕", "冬山鄕", "蘇澳鎮", "南澳鄕", "釣魚台" };// 宜蘭

	private String city20[] = { "馬公市", "西嶼鄕", "望安鄕", "七美鄕", "白沙鄕", "湖西鎮" };// 澎湖

	private String city21[] = { "金沙鎮", "金湖鎮", "金寧鄕", "金城鎮", "烈嶼鄕", "烏坵鄕" };// 金門縣

	private String city22[] = { "南竿鄕", "北竿鄕", "莒光鄕", "東引鄕" };// 連江縣
	private String city23[] = { "大陸地區" };// 
}
