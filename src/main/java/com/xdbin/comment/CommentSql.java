package com.xdbin.comment;

public class CommentSql {

    public static final String QUERY_COMMENT_DETAIL =
            "SELECT id, origin, b.title, reply_id, username, email, website, content, type, c.publish_time, valid " +
            "FROM t_comment c LEFT JOIN t_blog b ON c.origin = b.blog_id WHERE c.valid = 1 ORDER BY c.publish_time DESC";

    public static final String COUNT_COMMENT_DETAIL =
            "SELECT COUNT(id) FROM t_comment WHERE valid = 1";

}
