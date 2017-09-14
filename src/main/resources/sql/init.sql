CREATE DATABASE xdbin;

-- 初始化表

CREATE TABLE `t_blog` (
  `blog_id` varchar(32) NOT NULL COMMENT '博客id主键',
  `title` VARCHAR(100) NOT NULL COMMENT '博客标题',
  `publish_time` DATETIME NOT NULL COMMENT '博客发布时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '博客最近修改时间',
  `tags` VARCHAR(50) COMMENT '标签（最多五个）',
  `summary_text_type` TINYINT(1) COMMENT '博客概要文本类型',
  `summary` VARCHAR(2000) COMMENT '博客概要',
  `content_text_type` TINYINT(1) COMMENT '博客概要文本类型',
  `content_url` VARCHAR(100) NOT NULL COMMENT '博客内容文件路径',
  `if_pub` TINYINT(1) DEFAULT 1 COMMENT '公开是否公开',
  PRIMARY KEY (`blog_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '博客信息表';

CREATE TABLE `dic_tag` (
  `tag_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '标签id主键',
  `tag_name` VARCHAR(20) NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '标签字典表';

-- 初始化用户

CREATE USER 'xdbin_db'@'localhost' IDENTIFIED BY 'xdbin_db%rw';
GRANT SELECT, UPDATE, DELETE, INSERT ON xdbin.* TO 'xdbin_db'@'localhost';