package com.xdbin.blog;

import org.springframework.util.StringUtils;

import java.util.Map;

public class BlogSql {

    public static String buildQueryBlogListSQL(Map<String, Object> params) {
        String sql = "SELECT b.blog_id, b.title, b.summary, GROUP_CONCAT(bt.tag_id SEPARATOR '|') AS tags, b.publish_time, b.update_time, b.if_pub as pub " +
                "FROM t_blog b LEFT JOIN t_blog_tag bt ON b.blog_id = bt.blog_id WHERE 1 = 1 ";
        sql += where(params);
        sql += " GROUP BY b.blog_id ORDER BY b.publish_time DESC ";
        return sql;
    }

    public static String buildCountBlogListSQL(Map<String, Object> params) {
        String sql = "SELECT COUNT(b.blog_id) " +
                "FROM t_blog b WHERE 1 = 1 ";
        sql += where(params);
        return sql;
    }

    public static final String GROUP_BY_MONTHLY = "SELECT DATE_FORMAT(publish_time, '%Y-%m') as month, COUNT(1) as count FROM t_blog WHERE if_pub = 1 " +
            "GROUP BY DATE_FORMAT(publish_time, '%Y-%m') ORDER BY month DESC";

    private static String where(Map<String, Object> params) {
        if (StringUtils.isEmpty(params)) return "";
        String whereSql = "";
        if (!StringUtils.isEmpty(params.get("blogId"))) {
            whereSql += " AND b.blog_id = :blogId ";
        }
        if (!StringUtils.isEmpty(params.get("title"))) {
            whereSql += " AND b.title like :title ";
        }
        if (!StringUtils.isEmpty(params.get("month"))) {
            whereSql += " AND date_format(publish_time, '%Y-%m') = :month ";
        }
        if (!StringUtils.isEmpty(params.get("tagId"))) {
            whereSql += " AND b.blog_id in (SELECT blog_id FROM t_blog_tag WHERE tag_id = :tagId) ";
        }
        if (!StringUtils.isEmpty(params.get("pub"))) {
            whereSql += " AND b.if_pub = :pub ";
        }
        return whereSql;
    }

}
