package org.weiwei.utils;

import java.util.Calendar;


public class TimeUtils {

	
	public TimeUtils() {
		// TODO Auto-generated constructor stub
	}

	//获取当前时间
	public static String getCurrentTime(){
		String time = "";
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		time = hour+":"+min;
		return time;
	}
}
