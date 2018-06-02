package com.xdbin.config;

import com.xdbin.Bean.ErrorBean;
import com.xdbin.annotation.Ignore;
import com.xdbin.annotation.Security;
import com.xdbin.utils.ContextUtil;
import com.xdbin.utils.JwtTokenUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * Author: baoxuebin
 * Date: 2017/7/29
 */
@Aspect
@Component
public class SecurityAspect {

    private static Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Pointcut("@within(com.xdbin.annotation.Security) || @annotation(com.xdbin.annotation.Security)")
    private void security() {}

    @Around("security()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();

        if (isIgnore(method) || validateToken()) {
            return pjp.proceed();
        }

        logger.debug("用户未登录");

        return ResponseEntity.ok(new ErrorBean(401, "Unauthorized"));
    }

    private boolean isIgnore(Method method) {
        Ignore ignore = method.getAnnotation(Ignore.class);
        if (ignore != null) {
            Class clazz = ignore.value();
            if (Security.class.equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateToken() {
        HttpServletRequest request = ContextUtil.getRequest();
        String token = request.getHeader("auth");
        if (StringUtils.isEmpty(token)) { // header 中不携带 token, 从 cookie 中取
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                Optional<Cookie> cookie = Arrays.stream(cookies).filter(c -> "x-token".equals(c.getName())).findFirst();
                if (cookie.isPresent()) {
                    token = cookie.get().getValue();
                }
            }
        }
        return jwtTokenUtil.validateToken(token);
    }

}
