package com.myth.base;

import com.google.common.base.Strings;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;


public class ApiModel extends HashMap {

    /**
     *
     */
    private static final long serialVersionUID = 5215379190842912614L;

    public static final String ERROR = "error";
    public static final String ERROR_MESSAGE = "error_message";
    public static final String ERROR_DATA = "error_data";
    public static final String DATA = "data";
    public static final String FLAG = "flag";

    public ApiModel(){
        this.fail();
    }

    public String getError() {
        return (String) this.get(ERROR);
    }

    public void setError(Errors error, Object... data) {
        fail();
        if (error != null) {
            this.put(ERROR, error.getCode());
            this.setErrorData(data);
            if (data == null) {
                data = new String[]{};
            }
            this.put(ERROR_DATA, data);
            if (data.length > 0) {
                this.put(ERROR_MESSAGE, MessageFormat.format(error.getMessage(), data));
            } else {
                this.put(ERROR_MESSAGE, error.getMessage());
            }

        } else {
            this.cleanError();
        }
    }

    public void setError(Exception ex) {
        fail();
        if (ex != null) {
            if (ex instanceof BaseException) {
                BaseException e = ((BaseException) ex);
                setErr(e.getCode(), ex.getMessage());
                this.put(ERROR_DATA, ((BaseException) ex).getData());
            }

        } else {
            this.cleanError();
        }
    }

    private void setErr(String code, String message) {
        this.put(ERROR, code);
        this.put(ERROR_MESSAGE, message);
    }

    public String getErrorMessage() {
        return (String) this.get(ERROR_MESSAGE);
    }

    public Object[] getErrorData() {

        return (Object[]) this.get(ERROR_DATA);
    }

    public void setErrorData(Object... errorData) {
        this.put(ERROR_DATA, errorData);
    }

    public void cleanError() {
        fail();
        this.remove(ERROR);
        this.remove(ERROR_MESSAGE);
        this.remove(ERROR_DATA);
    }


    public void success() {
        this.put(FLAG, true);
    }

    public void fail() {
        this.put(FLAG, false);
    }

    public boolean isSuccess() {
        return Strings.isNullOrEmpty(this.getError());
    }

    public void setData(Object value) {
        success();
        this.put(DATA, value);
    }

    public Object getData() {
        return this.get(DATA);
    }

}
