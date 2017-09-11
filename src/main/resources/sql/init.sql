CREATE DATABASE xdbin;

-- 初始化表

CREATE TABLE `T_BLOG` (
  `blogId` varchar(32) NOT NULL COMMENT '博客id主键',
  `title` VARCHAR(100) NOT NULL COMMENT '博客标题',
  `publishTime` DATETIME DEFAULT NULL COMMENT '博客发布时间',
  `updateTime` DATETIME DEFAULT NULL COMMENT '博客最近修改时间',
  `tags` VARCHAR(50) COMMENT '标签（最多五个）',
  `summary` VARCHAR(2000) COMMENT '博客概要',
  `contentUrl` VARCHAR(100) COMMENT '博客内容文件路径',
  `ifPub` TINYINT(1) COMMENT '公开是否公开',
  PRIMARY KEY (`blogId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '博客信息表';

CREATE TABLE `DIC_TAG` (
  `tagId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '标签id主键',
  `tagName` VARCHAR(20) NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '标签字典表';

-- 初始化用户

CREATE USER 'xdbin_db'@'localhost' IDENTIFIED BY 'xdbin_db%rw';
GRANT SELECT, INSERT ON xdbin.* TO 'xdbin_db'@'localhost';