package com.turing.eteacher.constants;

import java.util.HashMap;
import java.util.Map;

public class CustomDataContants {

	public static Map<String,Map> datas = new HashMap();
	
	/**
	 * 自定义数据（成绩组成）
	 */
	public static final String CUSTOM_DATA_TYPE_SCORE = "score";
	
	static{
		Map scoreMap = new HashMap();
		scoreMap.put("normal", "平时");
		scoreMap.put("middle", "期中");
		scoreMap.put("final", "期末");
		datas.put(CUSTOM_DATA_TYPE_SCORE, scoreMap);
	}
}
