package cn.itcast.elec.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	//使用当前时间，转换成日期格式字符串（文件夹）
	public static String dateToStringFile(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("/yyyy/MM/dd/");
		return dateFormat.format(date);
	}

	//使用当前时间，转换成日期格式字符串
	public static String dateToStringTime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
	
	//使用当前时间，导出excel的格式
	public static String dateToStringExcel(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(date);
	}
	
	//使用当前时间，转换成日期格式字符串
	public static Date stringToDate(String sDate) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = dateFormat.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
