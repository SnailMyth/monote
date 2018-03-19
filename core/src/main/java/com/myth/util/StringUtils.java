package com.myth.util;

public class StringUtils {
	public static void printValue(Class<?> clazz,String name,String value) {
		System.out.println(clazz.getSimpleName()+": ["+name+"="+value+"]");
	}
	public static void printString(Class<?> clazz,String string) {
		System.out.println(clazz.getSimpleName()+": "+string);
	}

}
