package org.weiwei.model;

//记录传输列表的信息
public class Task {

	private int taskID; //用来获取任务在progressBar中的位置
	private int taskType; //任务类型
	private String taskName;  //任务名称
	private String remoteFileName; //远程文件名（含路径）
	private String localFilename; //本地存储位置 (含路径)
	private int progressValue;//传输进度
	private int taskState;//任务状态
	private long filesize; //文件大小
	
	
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getRemoteFileName() {
		return remoteFileName;
	}
	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}
	public String getLocalFilename() {
		return localFilename;
	}
	public void setLocalFilename(String localFilename) {
		this.localFilename = localFilename;
	}
	public int getProgressValue() {
		return progressValue;
	}
	public void setProgressValue(int progressValue) {
		this.progressValue = progressValue;
	}
	public int getTaskState() {
		return taskState;
	}
	public void setTaskState(int taskState) {
		this.taskState = taskState;
	}
	
	
	
}
