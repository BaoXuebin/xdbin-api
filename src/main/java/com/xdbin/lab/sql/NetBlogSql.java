package com.xdbin.lab.sql;

public class NetBlogSql {

    public static final String GROUP_BY_ORIGIN = "SELECT origin, COUNT(1) as count FROM net_blog GROUP BY origin ORDER BY count DESC;";

}
