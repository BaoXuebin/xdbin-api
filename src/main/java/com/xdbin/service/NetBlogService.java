package com.xdbin.service;

import com.xdbin.domain.NetBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author: baoxuebin
 * Date: 2018/3/30
 */
@Service
public class NetBlogService {

    @Resource
    private NetBlogRepository netBlogRepository;

    public Page<NetBlog> findAllByPage(int page, String title) {
        Sort s = new Sort(Sort.Direction.DESC, "publishTime");
        return netBlogRepository.findAll(new PageRequest(page - 1, 10, s));
    }

}
