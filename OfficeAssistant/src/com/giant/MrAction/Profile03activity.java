package com.giant.MrAction;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Profile03activity  extends Activity implements OnItemClickListener { 

	private ListView listview;  
    private List<Profile03Thing> lists;
	  ImageView  IV1US;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobpaperlisttable);
		
 
		        listview = (ListView) findViewById(R.id.listViewpls1);  
		        //初始化数据  
		        lists = getLists();  
		        //设置适配器  
		        listview.setAdapter(new Profile03Adapter(this, lists));  
		        //设置监听  
		        listview.setOnItemClickListener(this);  

 		
	}
	
	 //返回数据  
    private List<Profile03Thing> getLists() {  
        List<Profile03Thing> lists = new ArrayList<Profile03Thing>();  
        for (int i = 0; i < 21; i++) {  
        	Profile03Thing thing = new Profile03Thing("1","2","3","4","5","3","4","5");  
            
            lists.add(thing);  
        }  
        return lists;  
    }  
    //item的点击监听时间  
    
  
    
    

    @Override  
    public void onItemClick(AdapterView<?> view, View arg1, int position,  
            long arg3) {  
        Toast.makeText(this, ((Profile01Thing)view.getItemAtPosition(position)).getm1(), 0).show();  
  
    } 

}