package com.xdbin.condition;

/**
 * Author: BaoXuebin
 * Date: 2018/7/8
 * Time: 下午3:39
 */
public class PageCondition {

    private int pageNo = 1;
    private int pageSize = 10;

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = (pageNo != null && pageNo > 0) ? pageNo : 1;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = (pageSize != null && pageSize > 0) ? pageSize : 10;
    }
}
