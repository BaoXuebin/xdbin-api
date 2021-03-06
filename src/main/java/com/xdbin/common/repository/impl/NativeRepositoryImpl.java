package com.xdbin.common.repository.impl;

import com.xdbin.common.base.CustomPage;
import com.xdbin.common.repository.NativeQueryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository(value = "nativeQueryRepository")
public class NativeRepositoryImpl implements NativeQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private int greaterThanZero(int num) {
        return num < 0 ? 0 : num;
    }

    private Query getQuery(String sql, Map<String, Object> args, Class result) {
        Query query;
        if (StringUtils.isEmpty(result)) {
            query = entityManager.createNativeQuery(sql);
        } else {
            query = entityManager.createNativeQuery(sql, result);
        }
        if (args != null && !args.isEmpty()) {
            args.keySet().stream()
                    .filter(key -> !"pageNo".equals(key) && !"pageSize".equals(key))
                    .forEach(key -> {
                        if (!StringUtils.isEmpty(args.get(key))) {
                            query.setParameter(key, args.get(key));
                        }
                    });
        }
        return query;
    }

    @Override
    public List nativeQueryForList(String sql, Map<String, Object> args, Class result) {
        return getQuery(sql, args, result).getResultList();
    }

    @Override
    public CustomPage nativeQueryForPage(String sql, String countSql, Map<String, Object> args, Class result) {
        CustomPage page = new CustomPage();

        int pageNo = (Integer) args.get("pageNo");
        int pageSize = (Integer) args.get("pageSize");

        page.setPageNo(pageNo);
        page.setPageSize(pageSize);

        int total = Integer.parseInt(getQuery(countSql, args, null).getSingleResult().toString());
        page.setTotal(total);
        page.setFirst(pageNo == 1);
        if (!StringUtils.isEmpty(total) && total != 0) {
            args.put("_from", greaterThanZero(pageSize * (pageNo - 1)));
            args.put("_limit", greaterThanZero(pageSize));
            sql = sql + " LIMIT :_from, :_limit";

            List content = getQuery(sql, args, result).getResultList();
            page.setContent(content);
            page.setLast(total <= (pageSize * (pageNo - 1) + content.size()));
        }
        return page;
    }
}
