package com.xdbin.lab.service.impl;

import com.xdbin.common.repository.NativeQueryRepository;
import com.xdbin.domain.NetBlog;
import com.xdbin.lab.entity.NetBlogOrigin;
import com.xdbin.lab.repository.NetBlogRepository;
import com.xdbin.lab.service.NetBlogService;
import com.xdbin.lab.sql.NetBlogSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: baoxuebin
 * Date: 2018/3/30
 */
@Service
public class NetBlogServiceImpl implements NetBlogService {

    @Resource
    private NetBlogRepository netBlogRepository;

    private final NativeQueryRepository nativeQueryRepository;

    @Autowired
    public NetBlogServiceImpl(@Qualifier("nativeQueryRepository") NativeQueryRepository nativeQueryRepository) {
        this.nativeQueryRepository = nativeQueryRepository;
    }

    @Override
    public Page<NetBlog> findAllByPage(int page, String title, String origin) {
        Sort s = new Sort(Sort.Direction.DESC, "publishTime");
        return netBlogRepository.findAll(where(origin), new PageRequest(page - 1, 10, s));
    }

    @Override
    public List groupByOrigin() {
        return nativeQueryRepository.nativeQueryForList(
                NetBlogSql.GROUP_BY_ORIGIN,
                null,
                NetBlogOrigin.class
        );
    }

    /**
     * private function.
     *
     */
    private Specification<NetBlog> where(String origin) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 模糊匹配标题
            if (!StringUtils.isEmpty(origin)) {
                predicates.add(criteriaBuilder.like(root.get("origin").as(String.class), origin));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
    }

}
