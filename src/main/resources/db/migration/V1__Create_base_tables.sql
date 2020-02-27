DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT PRIMARY key,
  `account_id` varchar(100) NOT NULL,
  `name` varchar(50),
  `token` char(36) NOT NULL,
  `gmt_create` bigint(20) NOT NULL,
  `gmt_modified` bigint(20) NOT NULL,
  `bio` varchar(255),
  `avatar_url` varchar(100)
);

DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY key,
  `title` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `tags` varchar(256)  NOT NULL,
  `gmt_create` bigint(20) NOT NULL,
  `gmt_modified` bigint(20) NOT NULL,
  `comment_count` int(11) DEFAULT 0,
  `like_count` int(11) DEFAULT 0,
  `view_count` int(11) DEFAULT 0,
  `creator` bigint(20) NOT NULL
);

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY key,
  `parent_id` bigint(20) NOT NULL,
  `type` int(1) NOT NULL COMMENT '1：问题的评论  2：回复的评论',
  `content` varchar(1024)  NOT NULL,
  `commentator` bigint(20) NOT NULL,
  `gmt_create` bigint(20) NOT NULL,
  `gmt_modified` bigint(20) NOT NULL,
  `like_count` bigint(20) NULL DEFAULT 0,
  `comment_count` bigint(20) NULL DEFAULT 0
);

DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY key COMMENT '通知消息id',
  `notifier_id` bigint(20) NOT NULL COMMENT '通知者id',
  `notify_name` varchar(50) COMMENT '通知者姓名',
  `receiver_id` bigint(20) NOT NULL COMMENT '接收者id',
  `target_id` bigint(20) NOT NULL COMMENT '问题或评论id',
  `target_title` varchar(100) COMMENT '问题标题',
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '通知的类型，1:问题，2:评论',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '状态，0:未读，1:已读',
  `gmt_create` bigint(20) COMMENT '通知时间'
);