package org.weiwei.model;

import org.weiwei.ftp.FTPClient;

import android.provider.ContactsContract.Data;

/**
 * 表示当前连接的服务器
 * @author weiwei
 *
 */
public class ConServer{

	private String serverName;
	private String hostAndPort; //ip以及端口信息
	private String lastConnectTime; //最近登录时间
	private FTPClient mClient; //所使用的client
	private boolean state; //连接状态
	
	public ConServer(){
		
	}
	
	public ConServer(FTPClient client){
		mClient = client;
	}

	
	public FTPClient getmClient() {
		return mClient;
	}

	public void setmClient(FTPClient mClient) {
		this.mClient = mClient;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getLastConnectTime() {
		return lastConnectTime;
	}

	public void setLastConnectTime(String lastConnectTime) {
		this.lastConnectTime = lastConnectTime;
	}

	public String getHostAndPort() {
		return hostAndPort;
	}

	public void setHostAndPort(String hostAndPort) {
		this.hostAndPort = hostAndPort;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
}
