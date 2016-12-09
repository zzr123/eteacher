package com.turing.eteacher.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTest {

	static long CONST_WEEK = 3600 * 1000 * 24 * 7;

	public static void main(String[] args) throws Exception {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String strBefore = "2016-11-20";
		// String strAfter = "2016-11-30";
		// Calendar before = Calendar.getInstance();
		// Calendar after = Calendar.getInstance();
		// before.setTime(sdf.parse(strBefore));
		// after.setTime(sdf.parse(strAfter));
		// int week = before.get(Calendar.DAY_OF_WEEK)-1;
		// if (week == 0) {
		// week = 7;
		// }
		// System.out.println("当前：" + week);
		// before.add(Calendar.DATE, -week);
		// week = after.get(Calendar.DAY_OF_WEEK)-1;
		// if (week == 0) {
		// week = 7;
		// }
		// after.add(Calendar.DATE, 7 - week);
		// System.out.println("before："+sdf.format(before.getTime()));
		// System.out.println("after:"+sdf.format(after.getTime()));
		// int interval = (int) ((after.getTimeInMillis() - before
		// .getTimeInMillis()) / CONST_WEEK);
		// System.out.println(interval);
		getWeek("2016-11-23", 2, 3);
	}
	
	public static String getWeek(String date, int weekCount, int weekNum) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Date _date;
		try {
			_date = ft.parse(date);
			calendar.setTime(_date);
			int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			if (week == 0) {
				week = 7;
			}
			int distance = 0;
			if (weekCount == 0) {
				if (week <= weekNum) {
					distance = weekNum - week;
				} else {
					System.out.println("已过期");
					return null;
				}
			}
			if (weekCount == 0) {
				calendar.add(Calendar.DATE, distance);
			}else {
				calendar.add(Calendar.DATE, (7 - week) + (weekCount - 1) * 7 + weekNum);
			}
			System.out.println(ft.format(calendar.getTime()));
			return ft.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 获取指定日期相加多少周后的日期
	 * 
	 * @param date
	 * @param weeks
	 * @return
	 */
	public static String addWeeks(String date, int weeks) {
		if (weeks == 0) {
			return date;
		}
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date _date;
		try {
			_date = format.parse(date);
			calendar.setTime(_date);
			int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			if (week == 0) {
				week = 7;
			}
			calendar.add(Calendar.DATE, (7 - week) + 1 + (weeks - 1) * 7);
			System.out.println(format.format(calendar.getTime()));
			return format.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
