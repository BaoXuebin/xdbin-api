package com.xdbin.lab.sql;

import org.springframework.util.StringUtils;

import java.util.Map;

public class BookSql {

    private static String where(Map<String, Object> params) {
        if (StringUtils.isEmpty(params)) return "";
        String sql = "";
        if (!StringUtils.isEmpty(params.get("title"))) {
            sql += " AND b.title like :title ";
        }
        if (!StringUtils.isEmpty(params.get("publisher"))) {
            sql += " AND b.publisher like :publisher ";
        }
        if (!StringUtils.isEmpty(params.get("progress"))) {
            sql += " AND p.progress >= :progress ";
        }
        if (!StringUtils.isEmpty(params.get("pub"))) {
            sql += " AND b.pub = :pub ";
        }
        return sql;
    }

    private static String whereComment(Map<String, Object> params) {
        if (StringUtils.isEmpty(params)) return "";
        String sql = "";
        if (!StringUtils.isEmpty(params.get("bookId"))) {
            sql += " AND c.book_id = :bookId ";
        }
        if (!StringUtils.isEmpty(params.get("chapter"))) {
            sql += " AND c.chapter = :chapter ";
        }
        if (!StringUtils.isEmpty(params.get("section"))) {
            sql += " AND c.section >= :section ";
        }
        if (!StringUtils.isEmpty(params.get("endDate"))) {
            sql += " AND c.publish_time < :endDate ";
        }
        if (!StringUtils.isEmpty(params.get("beginDate"))) {
            sql += " AND c.publish_time >= :beginDate ";
        }
        if (!StringUtils.isEmpty(params.get("pub"))) {
            sql += " AND c.pub = :pub ";
        }
        return sql;
    }

    private static String order() {
        return " ORDER BY b.id DESC ";
    }
    private static String orderComment() { return " ORDER BY c.publish_time DESC "; }

    public static String buildQueryBooksSQL(Map<String, Object> params) {
        String sql = "SELECT b.id, b.isbn, b.title, b.sub_title, b.author, b.translator, b.tags, b.publish_date, " +
                " b.publisher, b.price, b.summary, b.pages, b.image, b.douban_link, b.pub, p.progress, p.update_time " +
                " FROM book b left join book_progress p on b.id = p.book_id and p.valid = 1 " +
                " WHERE b.valid = 1 ";
        sql += where(params);
        sql += order();
        return sql;
    }

    public static String buildCountBooksSQL(Map<String, Object> params) {
        String sql = "SELECT COUNT(b.id) " +
                " FROM book b left join book_progress p on b.id = p.book_id and p.valid = 1 " +
                " WHERE b.valid = 1 ";
        sql += where(params);
        sql += order();
        return sql;
    }

    public static String buildQueryBookCommentsSQL(Map<String, Object> params) {
        String sql = "SELECT c.id, c.book_id, c.comment, c.author, c.avatar, c.chapter, c.section, c.position, c.publish_time, c.pub, c.valid " +
                " FROM book_comment c WHERE c.valid = 1 ";
        sql += whereComment(params);
        sql += orderComment();
        return sql;
    }

    public static String buildCountBookCommentsSQL(Map<String, Object> params) {
        String sql = "SELECT COUNT(c.id) FROM book_comment c WHERE c.valid = 1 ";
        sql += whereComment(params);
        sql += orderComment();
        return sql;
    }

}
