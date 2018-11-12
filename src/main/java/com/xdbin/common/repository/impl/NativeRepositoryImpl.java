package com.xdbin.common.repository.impl;

import com.xdbin.common.repository.NativeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class NativeRepositoryImpl implements NativeRepository {

    @PersistenceContext
    private EntityManager entityManager;


    private Query getQuery(String sql, Map<String, Object> args, Class result) {
        Query query = entityManager.createNativeQuery(sql, result);
        if (args != null && !args.isEmpty()) {
            args.keySet().forEach(key -> query.setParameter(key, args.get(key)));
        }
        return query;
    }

    @Override
    public List nativeQueryForList(String sql, Map<String, Object> args, Class result) {
        return getQuery(sql, args, result).getResultList();
    }
}
