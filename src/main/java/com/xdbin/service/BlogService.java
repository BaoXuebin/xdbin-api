package com.xdbin.service;

import com.xdbin.Bean.BlogDetailBean;
import com.xdbin.Bean.BlogItemBean;
import com.xdbin.config.PathProperty;
import com.xdbin.domain.Blog;
import com.xdbin.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: baoxuebin
 * Date: 2017/9/10
 */
@Service
@Transactional
public class BlogService {

    @Resource
    private PathProperty pathProperty;

    @Resource
    private BlogRepository blogRepository;

    public BlogDetailBean getPublicBlogDetailById(String blogId) {
        if (StringUtils.isEmpty(blogId)) return null;
        Blog blog = blogRepository.findPublicBlog(blogId);
        String content = FileUtil.readBlogContent(pathProperty.getBlog(), blog.getContentUrl());

        return BlogDetailBean.parseBean(blog, content);
    }

    public BlogDetailBean getBlogDetailById(String blogId) {
        if (StringUtils.isEmpty(blogId)) return null;
        Blog blog = blogRepository.getOne(blogId);
        String content = FileUtil.readBlogContent(pathProperty.getBlog(), blog.getContentUrl());
        return BlogDetailBean.parseBean(blog, content);
    }

    public void saveBlog(Blog blog) {
        if (blog != null)
            blogRepository.save(blog);
    }

    public List<BlogItemBean> getPublicBlogs() {
        return parseBeanList(blogRepository.findBlogsByIfPubEquals(1));
    }

    public List<BlogItemBean> getAllBlogs() {
        return parseBeanList(blogRepository.findAll());
    }

    private List<BlogItemBean> parseBeanList(List<Blog> blogs) {
        List<BlogItemBean> blogItemBeans = new ArrayList<>();
        if (!StringUtils.isEmpty(blogs) && blogs.size() > 0) {
            blogs.forEach(blog -> {
                blogItemBeans.add(BlogItemBean.parseBean(blog));
            });
        }
        return blogItemBeans;
    }

}
