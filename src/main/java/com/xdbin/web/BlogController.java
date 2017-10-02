package com.xdbin.web;

import com.xdbin.Bean.BlogBean;
import com.xdbin.Bean.BlogDetailBean;
import com.xdbin.Bean.ErrorBean;
import com.xdbin.annotation.Security;
import com.xdbin.domain.Blog;
import com.xdbin.service.BlogService;
import com.xdbin.utils.ConvertUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

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
    public ResponseEntity blogs(String page) {
        long p = ConvertUtil.parseLong(page, 1);
        return ResponseEntity.ok(blogService.getPublicBlogsByPage((int) p));
    }

    @Security
    @RequestMapping(value = "/blogs/all", method = RequestMethod.GET)
    public ResponseEntity allTableBlogs(String page) {
        long p = ConvertUtil.parseLong(page, 1);
        return ResponseEntity.ok(blogService.getAllBlogsByPage((int) p));
    }

    @RequestMapping(value = "/blog/{blogId}", method = RequestMethod.GET)
    public ResponseEntity blogDetail(@PathVariable("blogId") String blogId) {
        BlogDetailBean blogDetailBean = null;
        if (!StringUtils.isEmpty(blogId))
            blogDetailBean = blogService.getPublicBlogDetailById(blogId);

        return ResponseEntity.ok(blogDetailBean);
    }

    @Security
    @RequestMapping(value = "/blog", method = RequestMethod.POST)
    public ResponseEntity saveBlog(@RequestBody BlogBean blogBean) {
        if (StringUtils.isEmpty(blogBean)) {
            return ResponseEntity.ok(new ErrorBean(400, "博客不能为空"));
        }
        String error = blogBean.validate();
        if (!StringUtils.isEmpty(error)) {
            return ResponseEntity.ok(new ErrorBean(400, error));
        }

        String result = blogService.saveBlog(blogBean);
        if (StringUtils.isEmpty(result)) {
            return ResponseEntity.ok(new ErrorBean(500, "博客保存失败"));
        }

        blogBean.setBlogId(result);
        return ResponseEntity.ok(blogBean);
    }

    @Security
    @RequestMapping(value = "/blog/{blogId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("blogId") String blogId) {
        if (StringUtils.isEmpty(blogId))
            return ResponseEntity.ok(new ErrorBean(400, "博客Id不能为空"));
        blogService.deleteBlog(blogId);
        return null;
    }

}
