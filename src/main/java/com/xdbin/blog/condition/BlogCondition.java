package com.xdbin.blog.condition;

import com.xdbin.condition.PageCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Author: baoxuebin
 * Date: 2018/2/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlogCondition extends PageCondition implements Serializable {

    private String title;

    private String tagId;

    private String month;

    private Integer pub;
}
