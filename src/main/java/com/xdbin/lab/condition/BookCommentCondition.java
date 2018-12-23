package com.xdbin.lab.condition;

import com.xdbin.condition.PageCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookCommentCondition extends PageCondition implements Serializable {

    private Integer bookId;
    private Integer chapter;
    private Integer section;
    private Date beginDate;
    private Date endDate;
    private Integer pub;

}
