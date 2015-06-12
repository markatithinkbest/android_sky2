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

public class Profile02activity  extends Activity implements OnItemClickListener { 
	private WebView webView;
	private ProgressBar progressBar;
	private ListView listview;  
    private List<Profile01Thing> lists;
	  ImageView  IV1US;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobpaperlistgo);
		
 
		        listview = (ListView) findViewById(R.id.listViewpls1);  
		        //初始化数据  
		        lists = getLists();  
		        //设置适配器  
		        listview.setAdapter(new Profile01Adapter(this, lists));  
		        //设置监听  
		        listview.setOnItemClickListener(this);  

 		
	}
	
	 //返回数据  
    private List<Profile01Thing> getLists() {  
        List<Profile01Thing> lists = new ArrayList<Profile01Thing>();  
        for (int i = 0; i < 21; i++) {  
        	Profile01Thing thing = new Profile01Thing("1","2","3","4","5");  
            
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
