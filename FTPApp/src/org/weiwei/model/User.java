package org.weiwei.model;

import java.util.List;

import org.weiwei.ftp.FTPClient;

/**
 * 用户类
 * @author weiwei
 *
 */

public class User {

	private String host,port,username,password; //连接信息
	private FTPClient ListClient;  //
	private List<FTPClient> taskList; 
	
	public User() {
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public FTPClient getListClient() {
		return ListClient;
	}

	public void setListClient(FTPClient listClient) {
		ListClient = listClient;
	}

	
}
