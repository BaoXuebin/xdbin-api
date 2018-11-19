package com.xdbin.common.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CustomPage {

    private int pageNo;

    private int pageSize;

    private int total;

    private boolean first;

    private boolean last;

    private List content;

}
