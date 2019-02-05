package com.xdbin.note;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Author: BaoXuebin
 * Date: 2019/2/3
 * Time: 9:28 PM
 */
public class NoteSql {

    public static String buildQuerySql(Map<String, Object> params) {
        String sql = baseFullNoteQuery();
        sql += where(params);
        sql += "GROUP BY n.id";
        return sql;
    }

    public static String buildCountSql(Map<String, Object> params) {
        String sql = baseFullNoteCount();
        sql += where(params);
        return sql;
    }

    private static String baseFullNoteQuery() {
        return "SELECT n.id, n.user_id, n.content, n.publish_time, n.pub, n.valid, GROUP_CONCAT(ni.image_url SEPARATOR ',') as images, u.nick_name, u.avatar_url " +
                "FROM note n LEFT JOIN note_images ni ON n.id = ni.note_id AND ni.valid = 1 " +
                "LEFT JOIN miniapp_user_info u ON u.user_id = n.user_id AND u.valid = 1 WHERE n.valid = 1 ";
    }

    private static String baseFullNoteCount() {
        return "SELECT COUNT(n.id) " +
                "FROM note n WHERE n.valid = 1 ";
    }

    private static String where(Map<String, Object> params) {
        if (StringUtils.isEmpty(params)) return "";
        String sql = "";
        if (!StringUtils.isEmpty(params.get("pubOrUserId"))) {
            sql += "AND (n.pub = 1 OR n.user_id = :pubOrUserId) ";
        }
        if (!StringUtils.isEmpty(params.get("pub"))) {
            sql += "AND n.pub = :pub ";
        }
        if (!StringUtils.isEmpty(params.get("userId"))) {
            sql += "AND n.user_id = :userId ";
        }
        if (!StringUtils.isEmpty(params.get("startTime"))) {
            sql += "AND n.publish_time >= :startTime ";
        }
        if (!StringUtils.isEmpty(params.get("endTime"))) {
            sql += "AND n.publish_time <= :endTime ";
        }
        return sql;
    }

}
