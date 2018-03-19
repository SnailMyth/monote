package com.myth.base;

import java.text.MessageFormat;

public enum Errors implements Error {
	SAVE_FAIL("add {0} fail,{1}"),
	FILE_NULL("file is null"),
	UPLOAD_FAIL("upload fail"),
	FIND_USER_FAIL("find user with name:{0} fail"),
	Login_FAIL("login fail,{0}");

	;

	private String message;

	private Errors(String msg) {
		this.message = msg;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return this.name().toLowerCase();
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	public String getMessage(Object... args) {
		return MessageFormat.format(this.message, args);
	}

}
