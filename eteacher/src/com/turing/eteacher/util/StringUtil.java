package com.turing.eteacher.util;

import java.math.BigDecimal;
import java.util.List;

public class StringUtil {

	public static boolean isNotEmpty(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		return true;
	}

	public static String getBigDecimalStr(BigDecimal num) {
		if (num == null) {
			return "";
		}
		String str = num.toString();
		while ((str.indexOf(".") > -1 && str.endsWith("0"))
				|| str.endsWith(".")) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public static String joinByList(List<String> list, String prex) {
		String str = "";
		for (String s : list) {
			str += s + prex;
		}
		if (str.endsWith(prex)) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 检查字符串数组中是否有空字符串
	 * 
	 * @author lifei
	 * @param params
	 * @return
	 */
	public static boolean checkParams(String... params) {
		for (int i = 0; i < params.length; i++) {
			if (!isNotEmpty(params[i])) {
				return false;
			}
		}
		return true;
	}
}
