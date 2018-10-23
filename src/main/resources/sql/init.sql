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
  `edit_num` INTEGER DEFAULT 0 COMMENT '编辑次数',
  `if_pub` TINYINT(1) DEFAULT 1 COMMENT '公开是否公开',
  PRIMARY KEY (`blog_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '博客信息表';

CREATE TABLE `dic_tag` (
  `tag_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '标签id主键',
  `tag_name` VARCHAR(20) NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '标签字典表';

create table `NET_BLOG` (
  uid Integer AUTO_INCREMENT primary key,
  id varchar(50) not null,
  title varchar(50),
  href varchar(200),
  author varchar(20),
  publishTime date,
  `count` integer,
  comment integer,
  origin varchar(100),
  originLink varchar(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '关注的博客';

create table `t_comment` (
  `id` int primary key auto_increment comment 'id',
  `origin` varchar(32) comment '来源',
  `replyId` int comment '回复评论ID',
  `username` varchar(50) comment '昵称',
  `email` varchar(50) comment '邮箱',
  `website` varchar(100) comment '网站',
  `content` varchar(1000) comment '评论内容',
  `type` tinyint(1) comment '评论类型：0 文本 1 markdown',
  `publishTime` DATETIME comment '发布时间',
  `valid` tinyint(1) default 1 comment '有效性',
  key `idx_origin` (`origin`)
) engine = InnoDB default charset = utf8 comment = '评论信息';

-- 初始化用户

CREATE USER 'xxx'@'localhost' IDENTIFIED BY 'xxx';
GRANT SELECT, UPDATE, DELETE, INSERT ON xdbin.* TO 'xxx'@'localhost';