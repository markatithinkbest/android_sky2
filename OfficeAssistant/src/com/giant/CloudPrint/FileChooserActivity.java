package com.giant.CloudPrint;

import java.io.File;
import java.util.ArrayList;

import com.giant.CloudPrint.FileChooserAdapter.FileInfo;
import com.giant.MrAction.R;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import android.view.KeyEvent;
import android.widget.TextView;

public class FileChooserActivity extends Activity {
	
	private GridView mGridView;
	private View mBackView;
	private View mBtExit;
	private TextView mTvPath ;
	
	private String mSdcardRootPath ;  //sdcard �??�臬��
	private String mLastFilePath ;    //?��?����?��?�蝷箇�??��?�敺�?
	
	private ArrayList<FileInfo> mFileLists  ;
	private FileChooserAdapter mAdatper ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cloudprintfilechooser_show);

		mSdcardRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();// ??��?��?�蕭sdcard?���?

		mBackView = findViewById(R.id.imgBackFolder);
		mBackView.setOnClickListener(mClickListener);
		mBtExit = findViewById(R.id.btExit);
		mBtExit.setOnClickListener(mClickListener);
		
		mTvPath = (TextView)findViewById(R.id.tvPath);
		
		mGridView = (GridView)findViewById(R.id.gvFileChooser);
		mGridView.setEmptyView(findViewById(R.id.tvEmptyHint));
		mGridView.setOnItemClickListener(mItemClickListener);
		setGridViewAdapter(mSdcardRootPath);
	}
	//��??�蔭��?��??��??
	private void setGridViewAdapter(String filePath) {
		updateFileItems(filePath);
		mAdatper = new FileChooserAdapter(this , mFileLists);
		mGridView.setAdapter(mAdatper);
	}
	//���?��?�臬��?��?���?����?��?�??��?��?���?�Adatper���?��??����
	private void updateFileItems(String filePath) {
		mLastFilePath = filePath ;
		mTvPath.setText(mLastFilePath);
		
		if(mFileLists == null)
			mFileLists = new ArrayList<FileInfo>() ;
		if(!mFileLists.isEmpty())
			mFileLists.clear() ;
		
		File[] files = folderScan(filePath);
		if(files == null) 
			return ;
		
		for (int i = 0; i < files.length; i++) {
			if(files[i].isHidden())  // ??��?��?�蝷粹�??��?����?
				continue ;
			
			String fileAbsolutePath = files[i].getAbsolutePath() ;
			String fileName = files[i].getName();
		    boolean isDirectory = false ;
			if (files[i].isDirectory()){
				isDirectory = true ;
			}
		    FileInfo fileInfo = new FileInfo(fileAbsolutePath , fileName , isDirectory) ;
			mFileLists.add(fileInfo);
		}
		//When first enter , the object of mAdatper don't initialized
		if(mAdatper != null)
		    mAdatper.notifyDataSetChanged();  //��??��?��??���?
	}
	//�?���?����?��?�敺??��??����?����?
	private File[] folderScan(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		return files;
	}
	
	private View.OnClickListener mClickListener = new  OnClickListener() {
		public void onClick(View v) {
			int i = v.getId();
			if (i == R.id.imgBackFolder) {
				backProcess();

			} else if (i == R.id.btExit) {
				setResult(RESULT_CANCELED);
				finish();

			} else {
			}
		}
	};
	
	private AdapterView.OnItemClickListener mItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			FileInfo fileInfo = (FileInfo)(((FileChooserAdapter)adapterView.getAdapter()).getItem(position));
			if(fileInfo.isDirectory())   //��?���??����?���??���?, ��?�蝷箄砲��?���??���?��?����?����?
				updateFileItems(fileInfo.getFilePath()) ;
			else if(fileInfo.isPPTFile()){  //��?ppt��?���? ??�� ���??�砲??�臬��?���?��?��?��?��??���?
			    Intent intent = new Intent();
			    intent.putExtra(CPMainF.EXTRA_FILE_CHOOSER , fileInfo.getFilePath());
			    setResult(RESULT_OK , intent);
			    finish();
			}
			else {   //��???���?.....
				toast(getText(R.string.open_file_error_format));
			}
		}
	};
    
	public boolean onKeyDown(int keyCode , KeyEvent event){
		if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode()
			== KeyEvent.KEYCODE_BACK){
			backProcess();   
			return true ;
		}
		return super.onKeyDown(keyCode, event);
	}
	//餈�?���?���?��?��?�敶?����?��??���?
	public void backProcess(){
		//��?��敶?����?��?�敺??��???��?��?sdcard??�臬�� ??�� ??��?���??��???���?��??���?��?���?��?���?
		if (!mLastFilePath.equals(mSdcardRootPath)) {  
			File thisFile = new File(mLastFilePath);
			String parentFilePath = thisFile.getParent();
			updateFileItems(parentFilePath);
		} 
		else {   //��?sdcard??�臬�� ??��??��?���
			setResult(RESULT_CANCELED);
			finish();
		}
	}
	private void toast(CharSequence hint){
	    Toast.makeText(this, hint , Toast.LENGTH_SHORT).show();
	}
}