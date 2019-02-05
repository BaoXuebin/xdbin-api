package com.xdbin.note.controller;

import com.xdbin.annotation.Security;
import com.xdbin.bean.ErrorBean;
import com.xdbin.bean.UserBean;
import com.xdbin.common.service.RestService;
import com.xdbin.config.WxProperty;
import com.xdbin.note.service.MiniappService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: BaoXuebin
 * Date: 2019/2/2
 * Time: 9:28 PM
 */
@RestController
@RequestMapping("/miniapp")
public class MiniappController {

    private MiniappService miniappService;

    @Autowired
    public MiniappController(MiniappService miniappService) {
        this.miniappService = miniappService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestParam(value = "code") String code) {
        String token = miniappService.wxLoginAndGetToken(code);
        if (StringUtils.isEmpty(token)) {
            return ResponseEntity.ok(new ErrorBean(400, "Failed to login."));
        }
        return ResponseEntity.ok(new UserBean(null, null, token));
    }

    @Security
    @RequestMapping(value = "/collect", method = RequestMethod.POST)
    public ResponseEntity collectUserInfo(@RequestParam(value="nickName") String nickName,
                                          @RequestParam(value = "avatarUrl") String avatarUrl, HttpServletRequest request) {
        String userId = (String) request.getAttribute("_userId");
        miniappService.save(userId, nickName, avatarUrl);
        return ResponseEntity.ok(null);
    }
}
