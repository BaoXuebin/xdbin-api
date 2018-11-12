package com.xdbin.vo;

import com.xdbin.annotation.Args;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: BaoXuebin
 * Date: 2018/10/29
 * Time: 下午8:50
 */
@AllArgsConstructor
@Data
public class TagVO implements Serializable {

    @Args
    private Long tagId;

    @Args(order = 1)
    private String tagName;

    @Args(order = 2)
    private Long count;

}
