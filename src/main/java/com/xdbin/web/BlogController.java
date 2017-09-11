package com.xdbin.web;

import com.xdbin.Bean.BlogDetailBean;
import com.xdbin.service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author: baoxuebin
 * Date: 2017/9/10
 */
@RestController
@CrossOrigin(value = "*")
public class BlogController {

    @Resource
    private BlogService blogService;

    @RequestMapping(value = "/blogs", method = RequestMethod.GET)
    public ResponseEntity blogs() {
        return ResponseEntity.ok(blogService.getPublicBlogs());
    }

    @RequestMapping(value = "/blog/{blogId}", method = RequestMethod.GET)
    public ResponseEntity blogDetail(@PathVariable("blogId") String blogId) {
        BlogDetailBean blogDetailBean = null;
        if (!StringUtils.isEmpty(blogId))
            blogDetailBean = blogService.getPublicBlogDetailById(blogId);

        return ResponseEntity.ok(blogDetailBean);
    }
}
