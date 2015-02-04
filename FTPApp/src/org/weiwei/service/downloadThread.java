package org.weiwei.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import org.weiwei.model.Task;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 下载线程,执行下载任务,处理下载逻辑
 * @author weiwei
 *
 */
public class downloadThread implements Runnable,FTPDataTransferListener,ITaskThread{

	private Task task;
	private FTPClient mClient;  
	private Handler mHandler; //与Activity建立的联系
	private String path = "/storage/sdcard0/UCDownloads/";
	private int done;
	
	public downloadThread(Task task,FTPClient client) {
		this.task = task;
		mClient = client;
	}
	
	@Override
	public void setHandler(Handler handler){
		this.mHandler = handler;
	}
	@Override
	public void removeHandler(){
		this.mHandler = null;
	}
	
	@Override
	public void run() {
		Log.i("TAG_1", "____________线程启动____________________________");
		if(mClient!=null){
			File file = new File(path+task.getLocalFilename());
			try {
				mClient.download(task.getRemoteFileName(), file, this);
			} catch (IllegalStateException e) {
				
			} catch (FileNotFoundException e) {
				
			} catch (IOException e) {
				
			} catch (FTPIllegalReplyException e) {
				
			} catch (FTPException e) {
				
			} catch (FTPDataTransferException e) {
				
			} catch (FTPAbortedException e) {
				
			}
		}
	}

	@Override
	public void started() {
		
	}

	@Override
	public void transferred(int length) {
		done = done+length;
		task.setProgressValue((int) ((done / (float)task.getFilesize()) * 100)); //设置ui
		if(mHandler!=null){ //只有在有UI界面的时候才发送消息
			Message msg = Message.obtain();
			msg.what = 1;
			msg.obj = task;
//			Log.i("TAG", done+"B");
			mHandler.sendMessage(msg);//向handler发送消息
		}else{
//			Log.i("TAG", "handler is empty");
		}

	}

	@Override
	public void completed() {
		
	}

	@Override
	public void aborted() {
		
	}

	@Override
	public void failed() {
		
	}


}
