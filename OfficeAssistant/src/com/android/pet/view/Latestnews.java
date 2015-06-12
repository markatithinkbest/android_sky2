package com.android.pet.view;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;









import com.geniuseoe2012.lazyloaderdemo.LoaderAdapter;
import com.giant.MrAction.GCMMessageView;
import com.giant.MrAction.R;



public class Latestnews  extends Fragment {

	/* 定义适配器 */
	private ListView item_list;

	// 換掉原來的字串陣列
	private ArrayList<String> data = new ArrayList<String>();
	private LoaderAdapter adapter;
	ArrayList<String> recordstr = null;
	
	String content[]=null;
	
	@SuppressWarnings("unchecked")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
View rootView = inflater.inflate(R.layout.feetsizerecord, container, false);
getActivity().setTitle("最新公告");
item_list = (ListView)rootView.findViewById(R.id.item_list);

processControllers();

// 加入範例資料


FileInputStream fis;
try {	
	fis = getActivity().openFileInput("GCMKEY_FEETH");
	ObjectInputStream is;
	try {
		is = new ObjectInputStream(fis);
		 try {
			 recordstr  = (ArrayList<String>) is.readObject();	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			is.close();
			fis.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
} catch (FileNotFoundException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
if (recordstr == null) {
	recordstr = new ArrayList<String>();
}

String addstr="";
content=new String[recordstr.size()];
String name[]=new String[recordstr.size()];
String imgurl[]=new String[recordstr.size()];

for(int i=0;i<recordstr.size();i++){
String	recordstrpas[]= (recordstr.get(i)).split(";");
imgurl[i]=new String();
content[i]=new String();
name[i]=new String();
content[i]=recordstrpas[3];
	addstr=recordstrpas[0]+"\n"+recordstrpas[1];
	name[i]=addstr;
	imgurl[i]=recordstrpas[4];
	data.add(addstr);
	addstr="";
}


int layoutId = android.R.layout.simple_list_item_single_choice;
//adapter = new ArrayAdapter<String>(this.getActivity(), layoutId, data);
adapter = new LoaderAdapter(data.size(),getActivity(),imgurl ,name);

item_list.setAdapter(adapter);   
        return rootView;
 }
	
	
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// 如果被啟動的Activity元件傳回確定的結果
		if (resultCode == Activity.RESULT_OK) {
			// 讀取標題
			String titleText = data.getStringExtra("titleText");
			
			// 如果是新增記事
			if (requestCode == 0) {
				// 加入標題項目
				this.data.add(titleText);
				// 通知資料已經改變，ListView元件才會重新顯示
				adapter.notifyDataSetChanged();
			}
			// 如果是修改記事
			else if (requestCode == 1) {
				// 讀取記事編號
				int position = data.getIntExtra("position", -1);
				
				if (position != -1) {
					// 設定標題項目
					this.data.set(position, titleText);
					// 通知資料已經改變，ListView元件才會重新顯示
					adapter.notifyDataSetChanged();
				}
			}
		}
	}


    
    

    private void processControllers() {
		// 建立選單項目點擊監聽物件
		OnItemClickListener itemListener = new OnItemClickListener() {
		    // 第一個參數是使用者操作的ListView物件
		    // 第二個參數是使用者選擇的項目
		    // 第三個參數是使用者選擇的項目編號，第一個是0
		    // 第四個參數在這裡沒有用途			
		    @Override
		    public void onItemClick(AdapterView<?> parent, View view, 
		            int position, long id) {							
				Intent intent = new Intent(getActivity(), GCMMessageView.class);		
				intent.putExtra("message", content[position]);			
		    }
		};
		
		
		
		
		
		
		
		
		
		
		
		
		
		item_list.setOnScrollListener(mScrollListener);
		OnItemClickListener itemListener0 = new OnItemClickListener() {
				
   		    @Override
   		    public void onItemClick(AdapterView<?> parent, View view, 
   		            int position, long id) {		
   		    	Intent intent = new Intent(getActivity(), GCMMessageView.class);
   		    	intent.putExtra("message", content[position]);
   		   startActivity(intent);
   		    }
   		};
	   		
	   		// 註冊選單項目點擊監聽物件
	   		item_list.setOnItemClickListener(itemListener0);
		
		
    }	
	
	// 載入選單資源

	
	
	
    OnScrollListener mScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_FLING:
				adapter.setFlagBusy(true);
				break;
			case OnScrollListener.SCROLL_STATE_IDLE:
				adapter.setFlagBusy(false);
				break;
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				adapter.setFlagBusy(false);
				break;
			default:
				break;
			}
			adapter.notifyDataSetChanged();
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

		}		
	};
	
	
	
	
	
	
	
	
	
	
}