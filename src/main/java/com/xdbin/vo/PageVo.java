package com.xdbin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Author: BaoXuebin
 * Date: 2018/9/15
 * Time: 下午1:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> implements Serializable {

    private int pageNo;

    private int pageSize;

    private long total;

    private List<T> content;

}
