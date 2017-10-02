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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Author: baoxuebin
 * Date: 2017/7/29
 */
@Aspect
@Component
public class SecurityAspect {

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
        return jwtTokenUtil.validateToken(token);
    }

}
