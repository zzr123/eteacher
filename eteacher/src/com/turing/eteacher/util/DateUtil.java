package com.turing.eteacher.util;

import java.text.ParseException;
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

	public static final long CONST_WEEK = 3600 * 1000 * 24 * 7;

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

	public static int getDayBetween(String from, String to) {
		SimpleDateFormat format = new SimpleDateFormat(YYYYMMDD);
		int i = 0;
		try {
			Date _from = format.parse(from);
			Date _to = format.parse(to);
			if (from == null || to == null) {
				return 0;
			}
			long l = _to.getTime() - _from.getTime();
			i = (int) l / (1000 * 60 * 60 * 24);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public static String addDays(String date, int days) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(YYYYMMDD);
		Date _date;
		try {
			_date = format.parse(date);
			calendar.setTime(_date);
			calendar.add(Calendar.DATE, days);
			return format.format(calendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定日期相加几周后的日期
	 * 
	 * @author lifei
	 * @param date
	 * @param weeks
	 * @return
	 */
	public static String addWeeks(String date, int weeks) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat ft = new SimpleDateFormat(YYYYMMDD);
		Date _date;
		try {
			_date = ft.parse(date);
			calendar.setTime(_date);
			calendar.add(Calendar.DATE, weeks * 7);
			return ft.format(calendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定星期的周几所在的日期
	 * 
	 * @author lifei
	 * @param date
	 *            开始日期
	 * @param weekCount
	 *            开始日期后第几周
	 * @param weekNum
	 *            周几
	 * @return
	 */
	public static String getWeek(String date, int weekCount, int weekNum) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat ft = new SimpleDateFormat(YYYYMMDD);
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
	 * 获取指定日期相加多少周后的(周一的)日期
	 * 
	 * @author lifei
	 * @param date
	 * @param weeks
	 * @return
	 */
	public static String addSpecialWeeks(String date, int weeks) {
		if (weeks == 0) {
			return date;
		}
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(YYYYMMDD);
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

	/**
	 * 获取某月的最后一天
	 * 
	 * @Title:getLastDayOfMonth
	 * @Description:
	 * @param:@param year
	 * @param:@param month
	 * @param:@return
	 * @return:String
	 * @throws
	 */
	public static String getLastDayOfMonth(String yearMonth) {
		if (null == yearMonth || "".equals(yearMonth)) {
			return null;
		}
		String[] ym = yearMonth.split("-");
		if (ym.length != 2) {
			return null;
		}
		int year = Integer.parseInt(ym[0]);
		int month = Integer.parseInt(ym[1]);
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());

		return lastDayOfMonth;
	}

	/**
	 * 判断两个日期区间是否有交集
	 * 
	 * @param startdate1
	 * @param enddate1
	 * @param startdate2
	 * @param enddate2
	 * @return true 有交集 false 无交集
	 */
	public static boolean isOverlap(String startdate1, String enddate1,
			String startdate2, String enddate2) {
		SimpleDateFormat format = new SimpleDateFormat(YYYYMMDD);
		Date start1 = null;
		Date end1 = null;
		Date start2 = null;
		Date end2 = null;
		try {
			start1 = format.parse(startdate1);
			end1 = format.parse(enddate1);
			start2 = format.parse(startdate2);
			end2 = format.parse(enddate2);
		} catch (ParseException e) {
			return false;
		}
		return !(((end1.getTime() <= start2.getTime()) || end2.getTime() < start1
				.getTime()));
	}

	/**
	 * 判断给定日期是否在日期区间内
	 * 
	 * @param date
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isInRange(String date, String start, String end) {
		SimpleDateFormat format = new SimpleDateFormat(YYYYMMDD);
		Date _date = null;
		Date _start = null;
		Date _end = null;
		try {
			_date = format.parse(date);
			_start = format.parse(start);
			_end = format.parse(end);
		} catch (ParseException e) {
			return false;
		}
		return (_date.getTime() >= _start.getTime())
				&& (_date.getTime() <= _end.getTime());
	}
	
	/**
	 * date1是否小于date2
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isBefore(String date1,String date2){
		SimpleDateFormat format = new SimpleDateFormat(YYYYMMDD);
		Date _date1 = null;
		Date _date2 = null;
		try {
			_date1 = format.parse(date1);
			_date2 = format.parse(date2);
		} catch (ParseException e) {
			return false;
		}
		return _date1.getTime() <= _date2.getTime();
	}

	/**
	 * 获取两个日期之间的周数
	 * 
	 * @param strBefore
	 * @param strAfter
	 * @return
	 */
	public static int getWeekCount(String strBefore, String strAfter) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
			Calendar before = Calendar.getInstance();
			Calendar after = Calendar.getInstance();
			before.setTime(sdf.parse(strBefore));
			after.setTime(sdf.parse(strAfter));
			int week = before.get(Calendar.DAY_OF_WEEK) - 1;
			if (week == 0) {
				week = 7;
			}
			System.out.println("当前：" + week);
			before.add(Calendar.DATE, -week);
			week = after.get(Calendar.DAY_OF_WEEK) - 1;
			if (week == 0) {
				week = 7;
			}
			after.add(Calendar.DATE, 7 - week);
			System.out.println("before：" + sdf.format(before.getTime()));
			System.out.println("after:" + sdf.format(after.getTime()));
			return (int) ((after.getTimeInMillis() - before.getTimeInMillis()) / CONST_WEEK);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
