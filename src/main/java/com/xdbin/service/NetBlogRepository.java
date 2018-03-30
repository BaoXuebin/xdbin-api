package com.xdbin.service;

import com.xdbin.domain.NetBlog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: baoxuebin
 * Date: 2018/3/30
 */
public interface NetBlogRepository extends JpaRepository<NetBlog, Long> {
}
