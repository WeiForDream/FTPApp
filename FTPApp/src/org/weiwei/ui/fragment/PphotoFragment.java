package org.weiwei.ui.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.weiwei.application.MyApplication;
import org.weiwei.ftpapp.OperationActivity;
import org.weiwei.ftpapp.R;
import org.weiwei.service.CoreService;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class PphotoFragment extends Fragment{

	private GridView mGridView; //
	private View view;

	private List<Photo> photos = new ArrayList<Photo>();//图片信息
	
	private static ImageLoader imageLoader;  //图片加载器

	private Context context;
	
	public PphotoFragment() {
		this(null);
	}
	
	public PphotoFragment(Context context){

		this.context=context;
		imageLoader = CoreService.imageLoader; //得到图片加载器
		getPhotoImage();  //获取图片路径
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_picture_photo, container,false);
		return view;
	}


	@Override
	public void onResume() {
		Log.i("TAST", "onResume");
		super.onResume();

	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	private void initView(){

		mGridView = (GridView) view.findViewById(R.id.id_fragment_picture_gridview);
		mGridView.setAdapter(new ImageAdapter());
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Photo p = photos.get(position);
				ImageView image = (ImageView) view.findViewById(R.id.id_griditem_picture_fragment_selected);
				image.setVisibility(p.isChoosed()?View.GONE:View.VISIBLE);
				p.setChoosed(p.isChoosed()?false:true);

			}
		});
		
		mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),OperationActivity.class);
				startActivityForResult(intent, 0);
				return false;
			}
		});
	}
	
	
	
	private class ImageAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		
		public ImageAdapter(){
			mInflater = LayoutInflater.from(getActivity());
		}
		@Override
		public int getCount() {
			return photos.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView==null){
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.griditem_picture_fragment, parent, false);
				holder.imageview = (ImageView) convertView.findViewById(R.id.id_griditem_picture_fragment_image);
				holder.imageChoosed = (ImageView)convertView.findViewById(R.id.id_griditem_picture_fragment_selected);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.imageview.setBackgroundResource(R.drawable.photo_bg);
			holder.imageChoosed.setVisibility(photos.get(position).isChoosed()?View.VISIBLE:View.GONE);
//			Log.i("TEST", " position "+position);
			imageLoader.displayImage(photos.get(position).getPath(), holder.imageview,CoreService.options);

			return convertView;
		}
		
	}
	
	private final class ViewHolder{
		ImageView imageview;
		ImageView imageChoosed;
	}
	
	//photo
	private class Photo{
		private String path="";
		private boolean choosed=false;
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public boolean isChoosed() {
			return choosed;
		}
		public void setChoosed(boolean choosed) {
			this.choosed = choosed;
		}
		
	}
	
	

	//获取相册中的图片
	private void getPhotoImage(){

		final String photoDir = "/storage/sdcard0/DCIM/Camera/"; //相册路径
		
		new Thread(new Runnable(){

			@Override
			public void run() {	
				Log.i("path", Environment.getExternalStorageDirectory().getAbsolutePath());
				Log.i("path",""+Environment.DIRECTORY_DCIM);
				Log.i("path",""+Environment.DIRECTORY_PICTURES);
				
				File file = new File(photoDir);
				String[] paths = file.list();
				for(int i = 0;i<paths.length;i++){
					if(paths[i].endsWith(".mp4")){
						continue;
					}
					Photo photo = new Photo();
					photo.setPath("file:///storage/sdcard0/DCIM/Camera/"+paths[i]);
					photo.setChoosed(false);
					photos.add(photo);
//					Log.i("path", paths[i]);
				}

				mHandler.sendEmptyMessage(0);
			}
			
		}).start();
	
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			initView();
		}
		
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		imageLoader.clearDiscCache(); //清理缓存
		imageLoader.clearMemoryCache();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i("TAST", "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.i("TAST", "onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("TAST", "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		Log.i("TAST", "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.i("TAST", "onDetach");
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.i("TAST", "onPause");
		super.onPause();
	}


	@Override
	public void onStart() {
		Log.i("TAST", "onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.i("TAST", "onStop");
		super.onStop();
	}
	
	
}
