package com.turing.eteacher.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期util类
 * 
 * @author caojian
 */
public class DateUtil {

	public static final String YYYYMMDD = "yyyy-MM-dd";

	/**
	 * 获取当前时间字符串
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDateStr(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	/**
	 * 获取今天是周几
	 * 
	 * @param calendar
	 * @return
	 */
	public static int getDayOfWeek(Calendar calendar) {
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return dayOfWeek;
	}

	/**
	 * 获取两个日期相差几天
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static int getDayBetween(Date from, Date to) {
		if (from == null || to == null) {
			return 0;
		}
		long l = to.getTime() - from.getTime();
		int i = (int) l / (1000 * 60 * 60 * 24);
		return i;
	}

	/**
	 * 获取指定日期相加多少天后的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	/**
	 * 获取给定时间的日历对象
	 * 
	 * @param time
	 *            (13:30:00)
	 * @return
	 */
	public static Calendar getCalendarByTime(String time) {
		int hour = Integer.parseInt(time.split(":")[0]);
		int minute = Integer.parseInt(time.split(":")[1]);
		int second = Integer.parseInt(time.split(":")[2]);
		Calendar lessonStart = Calendar.getInstance();
		lessonStart.set(Calendar.HOUR_OF_DAY, hour);
		lessonStart.set(Calendar.MINUTE, minute);
		lessonStart.set(Calendar.SECOND, second);
		lessonStart.get(Calendar.HOUR_OF_DAY);// 修改会延迟生效，调用下使修改生效
		return lessonStart;
	}

	/**
	 * 判断验证码是否在某个时间段内（验证码是否可用）
	 * 
	 * @param before
	 * @param now
	 * @return
	 */
	public static boolean isAvailable(long before, long now, long distance) {
		if ((now - before) < distance) {
			return true;
		}
		return false;
	}
}
