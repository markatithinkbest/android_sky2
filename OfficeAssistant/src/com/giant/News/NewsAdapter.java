package com.giant.News;

import java.util.ArrayList;

import com.giant.MrAction.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import Tool.TakeHttpUrl;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter<NewsItem> {
	private LayoutInflater mInflater;
	private ArrayList<NewsItem> listData;
	private ImageLoader loader;
	private DisplayImageOptions options;
	 private Context c;
	 protected ImageLoader imageLoader = ImageLoader.getInstance();
		
	public NewsAdapter(Context context, int rid, ArrayList<NewsItem> list) {
		super(context, rid, list);
		this.listData = list;
		c = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
			loader = ImageLoader.getInstance();
			
			options  = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
        
	}

	private static class ViewHolder {
		ImageView img;
		TextView tv1;
		TextView tvDate;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup paretnt) {
		// long startTime = System.nanoTime();
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.lv_news_list, null);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.imgShow);
			holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
			holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		if (listData.get(position).getApp_anpic() != null) {
//			if (listData.get(position).getApp_anpic().length() >=1)
//				loader.displayImage(URLS.NEWS_IMAGE + listData.get(position).getApp_anpic()+"", holder.img, options);
		ArrayList<String> urls = TakeHttpUrl.getUrl(listData.get(position).getMedias());
		if(urls.size() >=1)
			loader.displayImage(urls.get(0)+"", holder.img, options);
		else
			loader.displayImage("", holder.img, options);
//		}
//		if(SplashActivity.lang.equals("TW")){
//			Log.e("QQQ", listData.get(position).getSubject_TW().equals("null") + "  .  " + listData.get(position).getSubject_TW().length());
//			if (listData.get(position).getSubject_TW().equals("null")  == false && listData.get(position).getSubject_TW().trim().length() >= 1) {
//				holder.tv1.setText(listData.get(position).getSubject_TW());			
//			}else{
				holder.tv1.setText(listData.get(position).getSubject());
//			}
//		}else{
//			if (listData.get(position).getSubject() != null && listData.get(position).getSubject().length() >= 1) {
//				holder.tv1.setText(listData.get(position).getSubject());			
//			}
//		}
		holder.tvDate.setText(listData.get(position).getPublishDate());
		return convertView;
	}
}
