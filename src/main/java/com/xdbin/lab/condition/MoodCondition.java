package com.xdbin.lab.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class MoodCondition implements Serializable {

    private String user;
    private String action;
    private String avatar;
    private String content;
    private Integer type;
    private Integer pub;

}
