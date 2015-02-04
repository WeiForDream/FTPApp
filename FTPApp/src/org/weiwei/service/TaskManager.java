package org.weiwei.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;

/**
 * 线程管理
 * 
 * @author weiwei
 * 
 */

public class TaskManager {

	private final static Object syncObj = new Object();
	private static TaskManager instance;
	private ExecutorService executorService; // 线程池
	private final static int defTheadNumber = 3; //最多进行3个任务

	//维护线程列表
	private List<ITaskThread> mTaskThreadList = new ArrayList<ITaskThread>(); //线程列表
	
	private TaskManager(){
		this(defTheadNumber);
	}
	
	private TaskManager(int ThreadNumber) {
		executorService = Executors.newFixedThreadPool(ThreadNumber);
	}
	
	public static TaskManager getInstance(){
		return getInstance(defTheadNumber);
	}
	
	public static TaskManager getInstance(int ThreadNumber) {
		if (instance == null) {
			synchronized (syncObj) {
				instance = new TaskManager(ThreadNumber);
			}
		}
		return instance;
	}
	
	//执行线程
	public void executor(Runnable taskThread){
		if(taskThread!=null){
			mTaskThreadList.add((ITaskThread) taskThread); 
			executorService.execute(taskThread);
		}
	}
	
	//这样就把每个Thread都设置成同一个handler了
	public void setHandler(Handler handler){
		Iterator<ITaskThread> it = mTaskThreadList.iterator();
		while(it.hasNext()){
			ITaskThread taskThread = it.next();
			taskThread.setHandler(handler);
		}
	}
	
	//移除handler
	public void removeHandler(){
		Iterator<ITaskThread> it = mTaskThreadList.iterator();
		while(it.hasNext()){
			ITaskThread taskThread = it.next();
			taskThread.removeHandler();
		}
	}
	
	
}
