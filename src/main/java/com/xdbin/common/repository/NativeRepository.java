package com.xdbin.common.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface NativeRepository {

    List nativeQueryForList(String sql, Map<String, Object> args, Class result);

}
