package com.xdbin.note.service;

import com.xdbin.note.entity.MiniappUserInfo;

/**
 * Author: BaoXuebin
 * Date: 2019/2/5
 * Time: 11:06 AM
 */
public interface MiniappService {

    String wxLoginAndGetToken(String code);

    MiniappUserInfo save(String userId, String nickName, String avatarUrl);

}
