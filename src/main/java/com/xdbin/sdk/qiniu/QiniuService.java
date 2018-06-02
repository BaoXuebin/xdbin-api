package com.xdbin.sdk.qiniu;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.xdbin.config.QiniuProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author: BaoXuebin
 * Date: 2018/6/2
 * Time: 上午11:55
 */
@Service
public class QiniuService {

    @Resource
    private QiniuProperty qiniuProperty;

    /**
     * 构建 token
     * @return token
     */
    public String buildUpToken() {
        Auth auth = Auth.create(qiniuProperty.getAccessKey(), qiniuProperty.getSecretKey());

        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody",  "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"url\":\"" + qiniuProperty.getBaseUrl() + "$(key)\",\"fsize\":$(fsize)}");

        long expireSeconds = 3600;

        return auth.uploadToken(qiniuProperty.getBucket(), null, expireSeconds, putPolicy);
    }
}
