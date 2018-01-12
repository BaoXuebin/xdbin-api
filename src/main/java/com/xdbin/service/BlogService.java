package com.xdbin.service;

import com.xdbin.Bean.*;
import com.xdbin.annotation.Security;
import com.xdbin.config.PathProperty;
import com.xdbin.domain.Blog;
import com.xdbin.utils.ConvertUtil;
import com.xdbin.utils.FileUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
        if (!StringUtils.isEmpty(blog)) {
            String content = FileUtil.readBlogContent(pathProperty.getBlog(), blog.getContentUrl());
            return BlogDetailBean.parseBean(blog, content);
        }
        return null;
    }

    public UpdateBlogBean getUpdateBlogById(String blogId) {
        if (StringUtils.isEmpty(blogId)) return null;
        Blog blog = blogRepository.getOne(blogId);
        if (!StringUtils.isEmpty(blog)) {
            String content = FileUtil.readBlogContent(pathProperty.getBlog(), blog.getContentUrl());
            return UpdateBlogBean.parseBean(blog, content);
        }
        return null;
    }

    public String saveBlog(BlogBean blogBean) {
        // base + time + name.md
        String contentUrl = FileUtil.writeBlogContent(pathProperty.getBlog(), blogBean.getContent());
        if (StringUtils.isEmpty(contentUrl)) {
            return null;
        }

        Blog blog = new Blog();
        blog.setBlogId(blogBean.getBlogId());
        blog.setPublishTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setTitle(blogBean.getTitle());
        blog.setTags(blogBean.getTags());
        blog.setSummaryTextType(Blog.MARKDOWN);
        blog.setSummary(blogBean.getSummary());
        blog.setContentTextType(Blog.MARKDOWN);
        blog.setContentUrl(contentUrl);
        blog.setIfPub(blogBean.isIfPub() ? 1 : 0);

        try {
            saveBlog(blog);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return blog.getBlogId();
    }


    public void saveBlog(Blog blog) {
        if (blog != null)
            blogRepository.save(blog);
    }

    public void deleteBlog(String blogId) {
        blogRepository.delete(blogId);
    }

    public List<BlogItemBean> getPublicBlogsByPage(int page) {
        Sort s = new Sort(Sort.Direction.DESC, "updateTime");
        return parseBeanList(blogRepository.findPubBlogsByPage(new PageRequest(page - 1, 10, s)));
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

    public List<BlogTableBean> getAllBlogsByPage(int page) {
        Sort s = new Sort(Sort.Direction.DESC, "updateTime");
        return parseBlogTableList(blogRepository.findAllBlogsByPage(new PageRequest(page - 1, 10, s)));
    }

    private List<BlogTableBean> parseBlogTableList(List<Blog> blogs) {
        List<BlogTableBean> blogTableBeans = new ArrayList<>();
        if (!StringUtils.isEmpty(blogs) && blogs.size() > 0) {
            blogs.forEach(blog -> {
                blogTableBeans.add(BlogTableBean.parseBean(blog));
            });
        }
        return blogTableBeans;
    }

    public void setBlogPublic(String blogId) {
        blogRepository.updateBlogPubByBlogId(1, blogId);
    }

    public void setBlogPrivate(String blogId) {
        blogRepository.updateBlogPubByBlogId(0, blogId);
    }

}
