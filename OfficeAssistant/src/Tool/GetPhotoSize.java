package Tool;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

public class GetPhotoSize {

//	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//	public static int getSize(WindowManager w) {
//		int Measuredwidth = 0;
//		int Measuredheight = 0;
//
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//			Point size = new Point();
//			w.getDefaultDisplay().getSize(size);
//			Measuredwidth = size.x;
//			Measuredheight = size.y;
//		} else {
//			Display d = w.getDefaultDisplay();
//			Measuredwidth = d.getWidth();
//			Measuredheight = d.getHeight();
//		}
//		Log.e("Size", "Width" + Measuredwidth + "  Height " + Measuredheight);
//		if (Measuredheight > 950)
//			return 300;
//		else
//			return 150;
//	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static int getScreemHeight(Context mContext){
	    int width=0;
	    WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
	    Display display = wm.getDefaultDisplay();
	    if(Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB_MR2){                   
	        Point size = new Point();
	        display.getSize(size);
	        width = size.y;
	    }
	    else{
	        width = display.getHeight();  // deprecated
	    }
//	    Log.e("getWidth", "  width " + width);
	    return width;
	}
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static int getWidth(Context mContext){
	    int width=0;
	    WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
	    Display display = wm.getDefaultDisplay();
	    if(Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB_MR2){                   
	        Point size = new Point();
	        display.getSize(size);
	        width = size.x;
	    }
	    else{
	        width = display.getWidth();  // deprecated
	    }
//	    Log.e("getWidth", "  width " + width);
	    return width;
	}
	
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static int getHeight(Context mContext){
	    int height=0;
	    WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
	    Display display = wm.getDefaultDisplay();
	    if(Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB_MR2){               
	        Point size = new Point();
	        display.getSize(size);
	        height = size.y;
	    }else
	        height = display.getHeight();  // deprecated
	    
//	    Log.e("getHeight", "  Height " + height);
	    if (height > 1000)
	    	return 290;
	    else  if (height < 999 && height > 870)
			return 290;
	    else if (height < 869 && height > 699)
			return 270;
		else if (height < 500 )
			return 130;     
		else return 100;
	}
	
	
//	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//	public static int getProductCustomHeight(Context mContext){
//	    int height=0;
//	    WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//	    Display display = wm.getDefaultDisplay();
//	    if(Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB_MR2){               
//	        Point size = new Point();
//	        display.getSize(size);
//	        height = size.y;
//	    }else{
//	        height = display.getHeight();  // deprecated
//	    }
////	    Log.e("getHeight", "  Height " + height);
//	    if (height > 1000)
//			return 200;
//	    else if (height <= 999 && height > 860)
//			return 170;
//	    else if (height <= 859 && height > 799)
//			return 100;
//		else
//			return 80;     
//	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static int getRealHeight(Context mContext){
	    WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
	    Display display = wm.getDefaultDisplay();
	    if(Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB_MR2){               
	        Point size = new Point();
	        display.getSize(size);
	        return size.y;
	    }else{
	    	return display.getHeight();  // deprecated
	    }   
	}
}
