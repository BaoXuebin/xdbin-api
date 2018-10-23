package com.xdbin.web;

import com.xdbin.Bean.*;
import com.xdbin.annotation.Security;
import com.xdbin.domain.Blog;
import com.xdbin.sdk.qiniu.QiniuService;
import com.xdbin.service.BlogService;
import com.xdbin.service.NetBlogService;
import com.xdbin.utils.ConvertUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: baoxuebin
 * Date: 2017/9/10
 */
@RestController
public class BlogController {

    @Resource
    private BlogService blogService;

    @Resource
    private NetBlogService netBlogService;

    @Resource
    private QiniuService qiniuService;

    /**
     * 条件分页查询博客列表
     * @param blogCondition 分页条件
     */
    @RequestMapping(value = "/blogs", method = RequestMethod.GET)
    public ResponseEntity blogs(BlogCondition blogCondition) {
        if (!StringUtils.isEmpty(blogCondition)) {
            blogCondition.setPub(1);
        } else {
            blogCondition = new BlogCondition();
            blogCondition.setPub(1);
        }
        return ResponseEntity.ok(blogService.getBlogsByCondition(blogCondition));
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
    @RequestMapping(value = "/blog/update/{blogId}", method = RequestMethod.GET)
    public ResponseEntity updateBlogDetail(@PathVariable("blogId") String blogId) {
        UpdateBlogBean updateBlogBean = null;
        if (!StringUtils.isEmpty(blogId))
            updateBlogBean = blogService.getUpdateBlogById(blogId);

        return ResponseEntity.ok(updateBlogBean);
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
    @RequestMapping(value = "/blog", method = RequestMethod.DELETE)
    public ResponseEntity delete(String blogId) {
        if (StringUtils.isEmpty(blogId))
            return ResponseEntity.ok(new ErrorBean(400, "博客Id不能为空"));
        blogService.deleteBlog(blogId);

        Blog blog = new Blog();
        blog.setBlogId(blogId);
        return ResponseEntity.ok(blog);
    }

    @Security
    @RequestMapping(value = "/blog/togglePub", method = { RequestMethod.POST, RequestMethod.PATCH })
    public ResponseEntity togglePub(String type, String blogId) {
        if (StringUtils.isEmpty(blogId)) {
            return ResponseEntity.ok(new ErrorBean(400, "博客Id不能为空"));
        }
        Blog blog = new Blog();
        blog.setBlogId(blogId);
        if ("pub".equals(type)) {
            // 设置为公开
            blogService.setBlogPublic(blogId);
            blog.setIfPub(1);
        } else if ("pvt".equals(type)) {
            // 设置为私有
            blogService.setBlogPrivate(blogId);
            blog.setIfPub(0);
        } else {
            return ResponseEntity.ok(new ErrorBean(400, "无效请求"));
        }
        return ResponseEntity.ok(blog);
    }

    // 关注的其他博客
    @RequestMapping(value = "/blog/star")
    public ResponseEntity findNetBlogs(String page) {
        long p = ConvertUtil.parseLong(page, 1);
        return ResponseEntity.ok(netBlogService.findAllByPage((int) p, null));
    }

    @Security
    @RequestMapping(value = "/qiniu/token")
    public ResponseEntity getQiniuUploadToken() {
        Map result = new HashMap();
        result.put("token", qiniuService.buildUpToken());
        result.put("key", "pics/" + ConvertUtil.parseDateTimetoString(new Date()));

        return ResponseEntity.ok(result);
    }

}
