package com.giant.Jobpaper;

import java.util.ArrayList;
import java.util.List;

import com.giant.MrAction.Profile01Thing;
import com.giant.MrAction.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class JBP  extends BaseAdapter {  
	  
    private Context context;  
    private List<JBPT> lists;  
    private LayoutInflater layoutInflater;  
    
    TextView tv1;  
    TextView tv2;  
    TextView tv3;  
    TextView tv4;  
   
  
    /** 
     * 构造函数，进行初始化 
     *  
     * @param context 
     * @param lists 
     */  
    JBP(Context context, List<JBPT> lists) {  
        this.context = context;  
        this.lists = lists;  
        layoutInflater = LayoutInflater.from(this.context);  
    }  
  
    // 获得长度，一般返回数据的长度即可  
    @Override  
    public int getCount() {  
        return lists.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        return lists.get(position);  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    /** 
     * 最重要的方法，每一个item生成的时候，都会执行这个方法，在这个方法中实现数据与item中每个控件的绑定 
     */  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        // convertView对象就是item的界面对象，只有为空的时候我们才需要重新赋值一次，这样可以提高效率，如果有这个对象的话，系统会自动复用  
        //item_listview就是自定义的item的布局文件  
        if (convertView == null) {  
            convertView = layoutInflater.inflate(R.layout.ponelist, null);  
        }  
        //注意findViewById的时候，要使用convertView的这个方法，因为是在它里面进行控件的寻找  
      
        tv1 = (TextView) convertView.findViewById(R.id.pm41);  
        tv2 = (TextView) convertView.findViewById(R.id.pm42);  
        tv3 = (TextView) convertView.findViewById(R.id.pm43);  
        tv4 = (TextView) convertView.findViewById(R.id.pm44); 
        //将数据与控件进行绑定  
      
        tv1.setText(lists.get(position).getm1());  
        tv2.setText(lists.get(position).getm2()); 
        tv3.setText(lists.get(position).getm3());  
        tv4.setText(lists.get(position).getm4()); 
        
        
        return convertView;  
    }  
  
}  

