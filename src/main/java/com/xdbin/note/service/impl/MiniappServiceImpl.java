package com.xdbin.note.service.impl;

import com.google.gson.GsonBuilder;
import com.xdbin.common.constants.Constants;
import com.xdbin.common.service.IdService;
import com.xdbin.common.service.RestService;
import com.xdbin.config.WxProperty;
import com.xdbin.note.entity.MiniappUserInfo;
import com.xdbin.note.repository.MiniappUserInfoRepository;
import com.xdbin.note.service.MiniappService;
import com.xdbin.utils.JwtTokenUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Author: BaoXuebin
 * Date: 2019/2/5
 * Time: 11:07 AM
 */
@Service
public class MiniappServiceImpl implements MiniappService {

    public static final Logger logger = LoggerFactory.getLogger(MiniappServiceImpl.class);

    private WxProperty wxProperty;
    private RestService restService;
    private JwtTokenUtil jwtTokenUtil;
    private MiniappUserInfoRepository miniappUserInfoRepository;
    private IdService idService;

    @Autowired
    public MiniappServiceImpl(WxProperty wxProperty, RestService restService, JwtTokenUtil jwtTokenUtil,
                              MiniappUserInfoRepository miniappUserInfoRepository, IdService idService) {
        this.wxProperty = wxProperty;
        this.restService = restService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.miniappUserInfoRepository = miniappUserInfoRepository;
        this.idService = idService;
    }

    @Override
    public String wxLoginAndGetToken(String code)  {
        ResponseEntity responseEntity = restService.fetch(
                buildUrl(wxProperty.getAppId(),wxProperty.getSecret(), code),
                HttpMethod.GET,
                null);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            WxEntity wxEntity = new GsonBuilder().create().fromJson(responseEntity.getBody().toString(), WxEntity.class);
            if (!StringUtils.isEmpty(wxEntity.getErrcode())) {
                logger.error("Failded to login: " + responseEntity.getBody());
                return null;
            }
            MiniappUserInfo user = miniappUserInfoRepository.findByOpenIdAndValid(wxEntity.getOpenid(), Constants.DELETE_FLAG_NORMAL);
            if (StringUtils.isEmpty(user)) {
                MiniappUserInfo miniappUserInfo = new MiniappUserInfo();
                miniappUserInfo.setUserId(idService.newWxUserId());
                miniappUserInfo.setOpenId(wxEntity.getOpenid());
                miniappUserInfo.setValid(Constants.DELETE_FLAG_NORMAL);
                user = miniappUserInfoRepository.save(miniappUserInfo);
            }
            // 只允许 ox-CY5e5HXtRKvxS1K94qF8zps3c
            if ("ox-CY5e5HXtRKvxS1K94qF8zps3c".equals(user.getOpenId())) {
                return jwtTokenUtil.buildToken(user.getUserId());
            }
        }
        return null;
    }

    @Override
    public MiniappUserInfo save(String userId, String nickName, String avatarUrl) {
        MiniappUserInfo user = miniappUserInfoRepository.findByUserIdAndValid(userId, Constants.DELETE_FLAG_NORMAL);
        if (StringUtils.isEmpty(user)) return null;
        user.setAvatarUrl(avatarUrl);
        user.setNickName(nickName);
        user = miniappUserInfoRepository.save(user);
        return user;
    }

    // {"session_key":"Rs\/7Ap0o16JYFhP+NZszug==","openid":"ox-CY5e5HXtRKvxS1K94qF8zps3c"}
    @Data
    private class WxEntity {
        private Integer errcode;
        private String errmsg;
        private String session_key;
        private String openid;
    }

    private String buildUrl(String appId, String secret, String code) {
        return "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId
                + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
    }

}
