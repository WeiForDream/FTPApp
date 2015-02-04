package org.weiwei.application;

import org.weiwei.service.CoreService;

import android.app.Application;

public class MyApplication extends Application{

	private CoreService coreService;

	public MyApplication() {
	}

	public CoreService getCoreService() {
		return coreService;
	}

	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}



	
}
