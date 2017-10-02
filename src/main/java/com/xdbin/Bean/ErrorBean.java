package com.xdbin.Bean;

import java.io.Serializable;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
public class ErrorBean implements Serializable {

    private int code;

    private String error;

    public ErrorBean() {
    }

    public ErrorBean(int code, String error) {
        this.code = code;
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
