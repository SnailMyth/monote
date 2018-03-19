package com.myth.base;

import java.text.MessageFormat;

public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3903679948325345950L;

	private Object[] data;
	private Errors error;

	public BaseException(Errors err, Object... args) {
		this(err, null, args);
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

	public Errors getError() {
		return error;
	}
	
	public String getCode()
	{
		return error.getCode();
	}

	public BaseException(Errors err, Throwable cause, Object... args) {
		super(MessageFormat.format(err.getMessage(), args), cause);
		this.error = err;
	}

	public static BaseException create(Errors err, Throwable cause, Object... args) {
		return new BaseException(err, cause, args);
	}

	public static BaseException create(Errors err, Object... args) {
		return new BaseException(err, args);
	}

}
