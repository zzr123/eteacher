package com.turing.eteacher.constants;

import java.util.HashMap;
import java.util.Map;

public class ConfigContants {

	public static Map<String, String> configMap = new HashMap<String, String>();
	
	
	/**
	 * 每节课的时长和上课时间的设定
	 */
	public static final String CLASS_TIME_LENGTH = "CLASS_TIME_LENGTH";
	public static final String[] CLASS_TIME = { "CLASS_TIME_ZERO", "CLASS_TIME_ONE",
			"CLASS_TIME_TWO", "CLASS_TIME_THREE", "CLASS_TIME_FOUR",
			"CLASS_TIME_FIVE", "CLASS_TIME_SIX", "CLASS_TIME_SEVEN",
			"CLASS_TIME_EIGHT", "CLASS_TIME_NINE", "CLASS_TIME_TEN", "CLASS_TIME_ELEVEN", "CLASS_TIME_TWELVE" };

//	public static final String CLASS_TIME_ONE = "CLASS_TIME_ONE";
//	public static final String CLASS_TIME_TWO = "CLASS_TIME_TWO";
//	public static final String CLASS_TIME_THREE = "CLASS_TIME_THREE";
//	public static final String CLASS_TIME_FOUR = "CLASS_TIME_FOUR";
//	public static final String CLASS_TIME_FIVE = "CLASS_TIME_FIVE";
//	public static final String CLASS_TIME_SIX = "CLASS_TIME_SIX";
//	public static final String CLASS_TIME_SEVEN = "CLASS_TIME_SEVEN";
//	public static final String CLASS_TIME_EIGHT = "CLASS_TIME_EIGHT";
//	public static final String CLASS_TIME_NINE = "CLASS_TIME_NINE";
//	public static final String CLASS_TIME_TEN = "CLASS_TIME_TEN";

	
	static{
//		configMap.put(CLASS_TIME_LENGTH, "60");
		configMap.put(CLASS_TIME[1], "08:00-8:50");
		configMap.put(CLASS_TIME[2], "09:00-9:50");
		configMap.put(CLASS_TIME[3], "10:00-10:50");
		configMap.put(CLASS_TIME[4], "11:00-12:50");
		
		configMap.put(CLASS_TIME[5], "14:00-14:50");
		configMap.put(CLASS_TIME[6], "15:00-15:50");
		configMap.put(CLASS_TIME[7], "16:00-16:50");
		configMap.put(CLASS_TIME[8], "17:00-17:50");
		
		configMap.put(CLASS_TIME[9], "19:00-19:50");
		configMap.put(CLASS_TIME[10], "20:00-20:50");
		configMap.put(CLASS_TIME[11], "21:00-21:50");
		configMap.put(CLASS_TIME[12], "22:00-22:50");
	}
}
