# ESM数据库SQL语句
---
## 一、管理员相关的表

### 1、manage_info（管理员用户信息表）：存储管理员用户的所有信息
建表操作：
``` sql
CREATE TABLE IF NOT EXISTS `manage_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT UNIQUE,
  `user_name` varchar(30) NOT NULL,
  `user_real_name` varchar(30) NOT NULL,
  `user_pwd` varchar(64) NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  `delete_time` timestamp ,
  PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```
插入超级管理员：
``` sql
INSERT INTO `manage_info` (`user_name`, `user_real_name`, `user_pwd`, `update_time`) VALUES
('superadmin', '超级管理员', '182c9edd7d985f19d6c48acb58a39fd4', '0000-00-00 00:00:00');
```

