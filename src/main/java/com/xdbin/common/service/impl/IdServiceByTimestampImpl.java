package com.xdbin.common.service.impl;

import com.xdbin.common.service.IdService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Author: BaoXuebin
 * Date: 2019/2/5
 * Time: 11:29 AM
 */
@Service("idService")
public class IdServiceByTimestampImpl implements IdService {
    @Override
    public String newWxUserId() {
        return "WX" + new Date().getTime();
    }
}
