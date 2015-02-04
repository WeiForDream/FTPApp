package org.weiwei.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.weiwei.ftp.FTPClient;
import org.weiwei.ftpapp.R;
import org.weiwei.model.Task;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class CoreService extends Service {

	protected static final int TASK_LIST = 0;  //浏览服务器文件
	protected static final int TASK_UPLOAD = 1;  //上传本地文件
	protected static final int TASK_DONWLOAD = 2; //下载服务器文件
	public static ImageLoaderConfiguration config;
	public static DisplayImageOptions options;
	public static ImageLoader imageLoader;  //图片加载器

	
	//维护一个任务列表
	private List<Task> mTasks = new ArrayList<Task>();
	private List<Task> DoneList = new ArrayList<Task>(); //完成队列
	//该队列中包含
	private TaskManager mTaskManager;

	private MyBinder binder = new MyBinder();

	public class MyBinder extends Binder {
		public CoreService getService() {
			return CoreService.this;
		}
	}

	/**
	 * 绑定时会调用这个方法，返回一个Binder类，通过Binder类可以获取Service对象
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		 mTaskManager = TaskManager.getInstance();
		 initImageLoader();
		// mTaskManager.executor(new uploadThread());
		Log.i("TAG","service is create");
		
//		new downloadTask().executeOnExecutor(Executors.newCachedThreadPool(), ""); //
	}

	// 添加任务
	public void addTask(Task task,FTPClient client) {
		task.setTaskID(mTasks.size()); //这个id设置是list的尾部
		mTasks.add(task);//将任务加入到任务列表中
		
		switch (task.getTaskType()) {
		case 0:
			mTaskManager.executor(new downloadThread(task,client.getmClient())); //下载任务
			break;
		}
	}

	public List<Task> getmTasks() {
		return mTasks;
	}

	public void setmTasks(List<Task> mTasks) {
		this.mTasks = mTasks;
	}
	
	public List<Task> getDoneList() {
		return DoneList;
	}

	public void setDoneList(List<Task> doneList) {
		DoneList = doneList;
	}

	//要更新的handler不止这个页面
	public void setHandler(Handler handler){
		mTaskManager.setHandler(handler);
	}
	
	public void removeHandler(){
		mTaskManager.removeHandler();
		
	}

	public void initImageLoader(){
		File cacheDir = StorageUtils.getOwnCacheDirectory(this, "FTPApp/cache");//设置缓存
		//imageloader config
		config = new ImageLoaderConfiguration.Builder(this)
		.memoryCache(new WeakMemoryCache())
//		.memoryCacheSize(15*1024*1024)
		.threadPriority(Thread.NORM_PRIORITY-2)  //设置线程优先级
		.threadPoolSize(2)
		.denyCacheImageMultipleSizesInMemory() //
		.discCacheFileNameGenerator(new Md5FileNameGenerator()) //设置缓存文件的名字
		.discCacheFileCount(100)  //缓存文件最大个数
		.discCache(new UnlimitedDiscCache(cacheDir)) //自定义缓存路径
		.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		
		imageLoader.getInstance().init(config);
		imageLoader = imageLoader.getInstance();
		//options
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_blank_picture_icon)
		.showImageForEmptyUri(R.drawable.ic_blank_picture_icon)
		.showImageOnFail(R.drawable.ic_blank_picture_icon)
		.bitmapConfig(Bitmap.Config.RGB_565) //这个很关键，否则容易出现OOM,并且会出现卡顿
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//		.cacheInMemory()
		.cacheOnDisc()
		.displayer(new SimpleBitmapDisplayer()).build();
		
	}
}
