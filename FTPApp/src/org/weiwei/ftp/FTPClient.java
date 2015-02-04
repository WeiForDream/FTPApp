package org.weiwei.ftp;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.weiwei.model.Task;

public class FTPClient {
	private it.sauronsoftware.ftp4j.FTPClient mClient= new it.sauronsoftware.ftp4j.FTPClient();
	
	
	
	public it.sauronsoftware.ftp4j.FTPClient getmClient() {
		return mClient;
	}

	public void setmClient(it.sauronsoftware.ftp4j.FTPClient mClient) {
		this.mClient = mClient;
	}

	public void connect(String host,int port){
		try {
			mClient.connect(host, port);
		} catch (IllegalStateException e) {


		} catch (IOException e) {
			//IO异常

		} catch (FTPIllegalReplyException e) {
			//FTP非法回应

		} catch (FTPException e) {
			//FTP异常

		}
	}
	
	public String login(String username,String password){
		try {
			mClient.login(username, password);
		} catch (IllegalStateException e) {
			return "ERROR";

		} catch (IOException e) {


		} catch (FTPIllegalReplyException e) {
			return "username or password is wrong";
		} catch (FTPException e) {
			return "unknow error";
		}
		
		return "SUCCESS";
	}

	public FTPFile[] list(){
		FTPFile[] result = null;
		try {
			result = mClient.list();
		} catch (IllegalStateException e) {


		} catch (IOException e) {

		} catch (FTPIllegalReplyException e) {

		} catch (FTPException e) {

		} catch (FTPDataTransferException e) {

		} catch (FTPAbortedException e) {

		} catch (FTPListParseException e) {
			
		}
		return result;
	}
	
	public FTPFile[] changeDir(String params){
		FTPFile[] result = null;
		try {
			mClient.changeDirectory(params);
			result = mClient.list();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPDataTransferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPAbortedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPListParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void upload(Task task,FTPDataTransferListener listener){
		File file = new File("");
		try {
			mClient.upload(file,listener);  //文件上传
		} catch (IllegalStateException e) {
			//非法状态，如未连接
		} catch (FileNotFoundException e) {
			//文件不存在异常
		} catch (IOException e) {

		} catch (FTPIllegalReplyException e) {

		} catch (FTPException e) {

		} catch (FTPDataTransferException e) {
			//数据传输异常
		} catch (FTPAbortedException e) {
			//数据终止异常
		}
		
		
	}
	
	public void download(Task task){
		File file = new File("");
		try {
			mClient.download("", file, mClient.fileSize(""));//第三个参数是服务器文件大小
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
