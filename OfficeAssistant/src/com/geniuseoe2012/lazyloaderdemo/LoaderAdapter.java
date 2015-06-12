package com.geniuseoe2012.lazyloaderdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geniuseoe2012.lazyloaderdemo.cache.ImageLoader;
import com.giant.MrAction.R;





public class LoaderAdapter extends BaseAdapter{

	private static final String TAG = "LoaderAdapter";
	private boolean mBusy = false;

	public void setFlagBusy(boolean busy) {
		this.mBusy = busy;
	}

	
	private ImageLoader mImageLoader;
	private int mCount;
	private Context mContext;
	private String[] urlArrays;
	private String[] name;
	
	public LoaderAdapter(int count, Context context, String []url,String []nameString) {
		this.mCount = count;
		this.mContext = context;
		urlArrays = url;
		name=nameString;
		mImageLoader = new ImageLoader(context);
		
	}
	
	public ImageLoader getImageLoader(){
		return mImageLoader;
	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.shoesknowlist_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mTextView = (TextView) convertView
					.findViewById(R.id.tv_tipssk);
			viewHolder.mImageView = (ImageView) convertView
					.findViewById(R.id.iv_imagesk);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String url = "";
		url = urlArrays[position % urlArrays.length];

		//viewHolder.mImageView.setImageResource(R.drawable.empty_photo);
		viewHolder.mTextView.setText(name[position % urlArrays.length]);
		
		mImageLoader.DisplayImage(url, viewHolder.mImageView, false);
			
			
			
		return convertView;
	}

	static class ViewHolder {
		TextView mTextView;
		ImageView mImageView;
	}
}
