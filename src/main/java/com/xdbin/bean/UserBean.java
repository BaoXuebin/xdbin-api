package com.xdbin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: baoxuebin
 * Date: 2017/9/21
 */
@Data
@AllArgsConstructor
public class UserBean implements Serializable {

    private String userId;

    private String userName;

    private String token;
}
