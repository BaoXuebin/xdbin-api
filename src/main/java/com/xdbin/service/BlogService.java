package com.xdbin.service;

import com.xdbin.Bean.*;
import com.xdbin.annotation.Security;
import com.xdbin.config.PathProperty;
import com.xdbin.domain.Blog;
import com.xdbin.utils.ConvertUtil;
import com.xdbin.utils.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

        // 标签后加 ,
        String tags = blogBean.getTags();
        if (!StringUtils.isEmpty(blogBean.getTags())) {
            if (!blogBean.getTags().startsWith(","))
                tags = "," + tags;
            if (!blogBean.getTags().endsWith(","))
                tags = tags + ",";
        }

        Blog blog = new Blog();
        blog.setBlogId(blogBean.getBlogId());
        blog.setPublishTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setTitle(blogBean.getTitle());
        blog.setTags(tags);
        blog.setSummaryTextType(Blog.MARKDOWN);
        blog.setSummary(blogBean.getSummary());
        blog.setContentTextType(Blog.MARKDOWN);
        blog.setContentUrl(contentUrl);
        blog.setIfPub(blogBean.isIfPub() ? 1 : 0);

        saveBlog(blog);
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

    private List<BlogItemBean> parseBeanList(List<Blog> blogList) {
        List<BlogItemBean> blogItemBeans = Collections.emptyList();
        if (!StringUtils.isEmpty(blogList) && blogList.size() > 0) {
            blogItemBeans = blogList.stream()
                    .map(BlogItemBean::parseBean)
                    .collect(toList());
        }
        return blogItemBeans;
    }

    public List<BlogTableBean> getAllBlogsByPage(int page) {
        Sort s = new Sort(Sort.Direction.DESC, "updateTime");
        return parseBlogTableList(blogRepository.findAllBlogsByPage(new PageRequest(page - 1, 10, s)));
    }

    private List<BlogTableBean> parseBlogTableList(List<Blog> blogList) {
        List<BlogTableBean> blogTableBeans = Collections.emptyList();
        if (!StringUtils.isEmpty(blogList) && blogList.size() > 0) {
            blogTableBeans = blogList.stream()
                    .map(BlogTableBean::parseBean)
                    .collect(toList());
        }
        return blogTableBeans;
    }

    public void setBlogPublic(String blogId) {
        blogRepository.updateBlogPubByBlogId(1, blogId);
    }

    public void setBlogPrivate(String blogId) {
        blogRepository.updateBlogPubByBlogId(0, blogId);
    }

    /**
     * 条件查询博客列表
     * @param condition 条件集合
     * @return List<BlogItemBean>
     */
    public List<BlogItemBean> getBlogsByCondition(BlogCondition condition) {
        List<BlogItemBean> blogItemBeans = Collections.emptyList();
        if (StringUtils.isEmpty(condition)) return blogItemBeans;

        Sort s = new Sort(Sort.Direction.DESC, "updateTime");
        Page<Blog> blogPage = blogRepository.findAll(where(condition), new PageRequest(condition.getPage() - 1, 10, s));
        if (blogPage.getContent().size() > 0) {
            blogItemBeans = blogPage.getContent().stream().map(BlogItemBean::parseBean).collect(toList());
        }
        return blogItemBeans;
    }

    private Specification<Blog> where(BlogCondition blogCondition) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 模糊匹配标题
            if (!StringUtils.isEmpty(blogCondition.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + blogCondition.getTitle() + "%"));
            }
            // 模糊查询标签
            if (!StringUtils.isEmpty(blogCondition.getTagId())) {
                predicates.add(criteriaBuilder.like(root.get("tags").as(String.class), "%," + blogCondition.getTagId() + ",%"));
            }
            // 是否公开
            if (blogCondition.getPub() != 0) {
                // 只查询公开
                predicates.add(criteriaBuilder.equal(root.get("ifPub").as(Integer.class), 1));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
    }

}
