package Tool;


import com.giant.MrAction.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

public class ShowDialog {
	public static Dialog dialog;

	public static void showDialog(Context c) {
		if (dialog != null)
			dialog.dismiss();
		dialog = new Dialog(c, R.style.TTT);
		dialog.setContentView(R.layout.dialog_process);
		dialog.setCancelable(false);
		Window window = dialog.getWindow();
		window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.CENTER);
		dialog.show();
	}

	public static void showDialog(Context c, boolean show) {
		// if(dialog == null)
		if (dialog != null)
			dialog.dismiss();
		dialog = new Dialog(c, R.style.KeyBoard3); 
		dialog.setContentView(R.layout.dialog_process); 
		dialog.setCancelable(show);
		Window window = dialog.getWindow();
		window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.CENTER);
		dialog.show();
	}
	
	public static void dismissDialog() {
		if (dialog != null && dialog.isShowing())
			dialog.dismiss();
	}
	
	
}
