package com.xdbin.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Author: baoxuebin
 * Date: 2017/9/21
 */
@Data
public class LoginBean implements Serializable {

    private String username;

    private transient String password;
}
