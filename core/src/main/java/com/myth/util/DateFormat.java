package com.myth.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum DateFormat {
	FORMAT_SHORT("yyyy-MM-dd"),
	FORMAT_LONG("yyyy-MM-dd HH:mm:ss"),
	FORMAT_FULL("yyyy-MM-dd HH:mm:ss.S"),
	FORMAT_SHORT_CN("yyyy年MM月dd日"),
	FORMAT_LONG_CN("yyyy年MM月dd日  HH时mm分ss秒"),
	FORMAT_FULL_CN("yyyy年MM月dd日  HH时mm分ss秒SSS毫秒"),
	FORMAT_ROUND_NAME("yyMMddHHmmssSSS");
	
	private String value;
	private DateFormat(String value) {
		this.value = value;
	}
	
	public String format(Date date) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(value);
            returnValue = df.format(date);
        }
        return (returnValue);
	}
	
	public String format(Date date,String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
	}
	
	public static void main(String[] args) {
		System.out.println(DateFormat.FORMAT_FULL_CN.format(new Date()));
	}
}
