package com.giant.Jobpaper;

import java.util.ArrayList;
import java.util.List;

import com.giant.MrAction.Profile01Thing;
import com.giant.MrAction.R;
import com.special.ResideMenu.ResideMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class JBPA   extends Fragment implements OnItemClickListener { 
	private WebView webView;
	private ProgressBar progressBar;
	private ListView listview;  
    private List<JBPT> lists;
	  ImageView  IV1US;
		    private View parentView;
		    private ResideMenu resideMenu;

		    @Override
		    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		        parentView = inflater.inflate(R.layout.jbalist, container, false);
		        listview = (ListView) parentView.findViewById(R.id.jbalistViewpls1);  
		        //初始化数据  
		        lists = getLists();  
		        //设置适配器  
		        listview.setAdapter(new JBP(getActivity(), lists));  
		        //设置监听  
		        listview.setOnItemClickListener(this);  
		        return parentView;
 		
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
  //      Toast.makeText(getActivity(), ((Profile01Thing)view.getItemAtPosition(position)).getm1(), 0).show();  
    	
    	
    	Intent intent = new Intent(getActivity(), JobcontentActivity.class);
    	//intent.putExtra("sex", sex);
    	//intent.putExtra("selectType", selectType);
    	startActivity(intent);
    } 

}
