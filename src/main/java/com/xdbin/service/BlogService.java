package com.xdbin.service;

import com.xdbin.Bean.BlogBean;
import com.xdbin.Bean.BlogDetailBean;
import com.xdbin.Bean.BlogItemBean;
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

    public BlogDetailBean getBlogDetailById(String blogId) {
        if (StringUtils.isEmpty(blogId)) return null;
        Blog blog = blogRepository.getOne(blogId);
        if (!StringUtils.isEmpty(blog)) {
            String content = FileUtil.readBlogContent(pathProperty.getBlog(), blog.getContentUrl());
            return BlogDetailBean.parseBean(blog, content);
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

}
