package com.xdbin.user.controller;

import com.xdbin.bean.ErrorBean;
import com.xdbin.bean.LoginBean;
import com.xdbin.bean.UserBean;
import com.xdbin.annotation.Security;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
    public ResponseEntity login(
            HttpServletResponse response,
            @RequestBody LoginBean loginBean
    ) {
        if (StringUtils.isEmpty(loginBean)
                || StringUtils.isEmpty(loginBean.getUsername())
                || StringUtils.isEmpty(loginBean.getPassword()))
            return ResponseEntity.ok(new ErrorBean(400, "登录信息不能为空"));

        if (!loginBean.getUsername().equals("BaoXuebin") || !loginBean.getPassword().equals(password))
            return ResponseEntity.ok(new ErrorBean(400, "用户名密码不匹配"));

        String token = jwtTokenUtil.buildToken("BaoXuebin");
        logger.debug("BaoXuebin 生成 token: " + token);

        // 将 token 添加到 response 并返回
        Cookie cookie = new Cookie("x-token", token);
        cookie.setMaxAge(2 * 60 * 60); // 2 个小时
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); // 只有 https 请求才能携带此 cookie
        response.addCookie(cookie);

        return ResponseEntity.ok(new UserBean("BaoXuebin", "包学斌", token));
    }

    @Security
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseEntity validate() {
        return ResponseEntity.ok(new UserBean("BaoXuebin", "包学斌", null));
    }

    @Security
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpServletResponse response) {
        // 将 token 添加到 response 并返回
        Cookie cookie = new Cookie("x-token", "");
        cookie.setMaxAge(1);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(new UserBean("BaoXuebin", "包学斌", null));
    }
}
