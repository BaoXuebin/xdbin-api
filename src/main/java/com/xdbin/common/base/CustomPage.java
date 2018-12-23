package com.xdbin.common.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Data
@NoArgsConstructor
public class CustomPage {

    private int pageNo;

    private int pageSize;

    private int total;

    private boolean first;

    private boolean last;

    private List content = Collections.emptyList();

    public void mapEntity(Function f) {
        List result = new ArrayList();
        for (Object o : content) {
            result.add(f.apply(o));
        }
        this.content = result;
    }

}
