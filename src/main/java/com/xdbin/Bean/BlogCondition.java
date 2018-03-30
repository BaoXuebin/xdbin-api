package com.xdbin.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: baoxuebin
 * Date: 2018/2/21
 */
@Data
@AllArgsConstructor
public class BlogCondition implements Serializable {

    private int page = 1;

    private String title;

    private String tagId;

    private String tagName;

    private int pub;

    public BlogCondition() {}
}
