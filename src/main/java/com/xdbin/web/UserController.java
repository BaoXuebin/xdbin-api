package com.xdbin.web;

import com.xdbin.Bean.ErrorBean;
import com.xdbin.Bean.LoginBean;
import com.xdbin.Bean.UserBean;
import com.xdbin.annotation.Security;
import com.xdbin.config.PathProperty;
import com.xdbin.utils.JwtTokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Author: baoxuebin
 * Date: 2017/9/21
 */
@RestController
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Value("${admin.password}")
    private String password;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginBean loginBean) {
        if (StringUtils.isEmpty(loginBean)
                || StringUtils.isEmpty(loginBean.getUsername())
                || StringUtils.isEmpty(loginBean.getPassword()))
            return ResponseEntity.ok(new ErrorBean(400, "登录信息不能为空"));

        if (!loginBean.getUsername().equals("BaoXuebin") || !loginBean.getPassword().equals(password))
            return ResponseEntity.ok(new ErrorBean(400, "用户名密码不匹配"));

        String token = jwtTokenUtil.buildToken("BaoXuebin");
        logger.debug("BaoXuebin 生成 token: " + token);
        return ResponseEntity.ok(new UserBean("BaoXuebin", "包学斌", token));
    }

    @Security
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseEntity validate() {
        return ResponseEntity.ok(new UserBean("BaoXuebin", "包学斌", null));
    }
}
