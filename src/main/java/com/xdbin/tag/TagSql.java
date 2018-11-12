package com.xdbin.tag;

public class TagSql {

    public static final String GROUP_TAG_BY_ID = "SELECT t1.tag_id, t1.tag_name, t2.count " +
            "FROM dic_tag t1 LEFT JOIN (SELECT tag_id, COUNT(blog_id) AS count FROM t_blog_tag GROUP BY tag_id) t2 " +
            "ON t1.tag_id = t2.tag_id";

}
