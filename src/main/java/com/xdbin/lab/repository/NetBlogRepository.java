package com.xdbin.lab.repository;

import com.xdbin.domain.NetBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Author: baoxuebin
 * Date: 2018/3/30
 */
public interface NetBlogRepository extends JpaRepository<NetBlog, Long>, JpaSpecificationExecutor<NetBlog> {

    @Override
    Page<NetBlog> findAll(Specification<NetBlog> specification, Pageable pageable);

}
