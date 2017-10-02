package com.xdbin.Bean;

import java.io.Serializable;

/**
 * Author: baoxuebin
 * Date: 2017/9/21
 */
public class UserBean implements Serializable {

    private String userId;

    private String userName;

    private String token;

    public UserBean() {
    }

    public UserBean(String userId, String userName, String token) {
        this.userId = userId;
        this.userName = userName;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
