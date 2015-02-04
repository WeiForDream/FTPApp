package org.weiwei.service;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;

import org.weiwei.ftp.FTPClient;
import org.weiwei.model.Task;

import android.os.Handler;
import android.os.Message;

/**
 * 下载线程
 * @author weiwei
 *
 */
public class uploadThread implements Runnable,FTPDataTransferListener,ITaskThread{

	private FTPClient mClient;
	private Handler mHandler=null;
	private Task task;
	private int done= 0;
	
	public uploadThread() {
	}

	public uploadThread(FTPClient client,Task task){
		mClient = client;
		this.task = task;
	}
	
	@Override
	public void run() {
		mClient.upload(task,this);  //上传任务
	}

	@Override
	public void started() {
		
	}

	
	@Override
	public void transferred(int length) {
		done = done+length;
		if(mHandler!=null){
			Message msg = mHandler.obtainMessage();
			msg.arg1 = done;
			msg.sendToTarget();  //给handler发送消息
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

	@Override
	public void setHandler(Handler handler) {
		
	}

	@Override
	public void removeHandler() {
		
	}

}
