package edu.awieclawski.web.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimeUtils {
	
	public String nowString() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String result = sdf1.format(timestamp).toString();
		return result;
	}

}
