package com.xdbin.Bean;

import java.io.Serializable;

/**
 * Author: baoxuebin
 * Date: 2017/9/21
 */
public class LoginBean implements Serializable {

    private String username;

    private transient String password;

    public LoginBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
