package com.xdbin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: BaoXuebin
 * Date: 2019/2/5
 * Time: 10:55 AM
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WxProperty {

    private String appId;

    private String secret;

}
