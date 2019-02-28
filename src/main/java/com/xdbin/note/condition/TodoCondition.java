package com.xdbin.note.condition;

import com.xdbin.condition.PageCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Author: BaoXuebin
 * Date: 2019/2/25
 * Time: 10:36 PM
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class TodoCondition extends PageCondition {

    private Long id;
    private String title;
    private String type;
    private Date createTimeStart;
    private Date createTimeEnd;
    private Date finishTimeStart;
    private Date finishTimeEnd;
    private Integer ifPub;

}
