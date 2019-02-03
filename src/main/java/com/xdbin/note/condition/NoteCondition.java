package com.xdbin.note.condition;

import com.xdbin.condition.PageCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * Author: BaoXuebin
 * Date: 2019/2/2
 * Time: 9:29 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoteCondition extends PageCondition implements Serializable {

    private String content;
    private Integer pub;
    private List<String> images;

}
