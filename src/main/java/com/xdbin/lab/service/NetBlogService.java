package com.xdbin.lab.service;

import com.xdbin.domain.NetBlog;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NetBlogService {

    Page<NetBlog> findAllByPage(int page, String title, String origin);

    List groupByOrigin();

}
