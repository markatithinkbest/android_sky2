package com.giant.Jobpaper;

import java.util.ArrayList;
import java.util.List;

import com.giant.MrAction.R;

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

public class JobcontentActivity  extends Activity implements OnItemClickListener { 

	private ListView listview;  
    private List<JBPT> lists;
	  ImageView  IV1US;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobpaperwkp);
		
 
		        listview = (ListView) findViewById(R.id.listViewpls1);  
		        //初始化数据  
		        lists = getLists();  
		        //设置适配器  
		        listview.setAdapter(new JBP(this, lists));  
		        //设置监听  
		        listview.setOnItemClickListener(this);  

		      
	}
	
	 //返回数据  
    private List<JBPT> getLists() {  
        List<JBPT> lists = new ArrayList<JBPT>();  
        for (int i = 0; i < 21; i++) {  
        	JBPT thing = new JBPT("1","2","3","4","5");   
            lists.add(thing);  
        }  
        return lists;  
    }  
    //item的点击监听时间  
    
  
    
    

    @Override  
    public void onItemClick(AdapterView<?> view, View arg1, int position,  
            long arg3) {  
        Toast.makeText(this, ((JBPT)view.getItemAtPosition(position)).getm1(), 0).show();  
  
    } 

}