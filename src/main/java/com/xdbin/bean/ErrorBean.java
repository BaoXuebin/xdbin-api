package com.xdbin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: baoxuebin
 * Date: 2017/9/11
 */
@Data
@AllArgsConstructor
public class ErrorBean implements Serializable {

    private int code;

    private String error;

}
