# ESM数据库SQL语句
---
## 一、管理员相关的表

### 1、manage_info（管理员用户信息表）：存储管理员用户的所有信息
建表操作：
``` sql
CREATE TABLE IF NOT EXISTS `manage_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT UNIQUE,
  `user_name` varchar(30) NOT NULL UNIQUE,
  `user_real_name` varchar(30) NOT NULL,
  `user_pwd` varchar(64) NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  `delete_time` timestamp ,
  `other1` varchar(30),
  `other2` varchar(30),
  `other3` varchar(30),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```
插入超级管理员：
``` sql
INSERT INTO `manage_info` (`id`, `user_name`, `user_real_name`, `user_pwd`, `update_time`) VALUES
(999,'superadmin', '超级管理员', '182c9edd7d985f19d6c48acb58a39fd4', '0000-00-00 00:00:00');
```
## 二、管理端配置相关的表结构

### 1、setting（各类用户权限表）：存储管理员用户的所有信息
建表操作：
``` sql
CREATE TABLE IF NOT EXISTS `setting` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT UNIQUE,
  `type` varchar(30) NOT NULL,
  `f1_access` tinyint(1) NOT NULL,
  `f2_access` tinyint(1) NOT NULL,
  `f3_access` tinyint(1) NOT NULL,
  `valid_year` int(10) NOT NULL DEFAULT 1,
  `other1` varchar(30),
  `other2` varchar(30),
  `other3` varchar(30),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```
插入标准模板：
``` sql
TRUNCATE setting;
INSERT INTO `setting` (`type`, `f1_access`, `f2_access`, `f3_access`, `valid_year`) VALUES ('教职工', '1', '1', '1', '10'),('学生', '1', '1', '1', '3'),('服务商', '1', '1', '1', '5');
```

### 2、major（学生专业表）：存储学生专业的所有信息
建表操作：
``` sql
CREATE TABLE IF NOT EXISTS `major` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT UNIQUE,
  `major` varchar(30) NOT NULL UNIQUE,
  `is_valid` tinyint(1) NOT NULL DEFAULT 1,
  `other1` varchar(30),
  `other2` varchar(30),
  `other3` varchar(30),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```
插入标准模板：
``` sql
TRUNCATE major;
INSERT INTO `major` (`major`) VALUES ('计算机技术'),('软件工程'),('电子信息'),('工程管理');
```

### 3、department（教职工部门表）：存储教职工部门的所有信息
建表操作：
``` sql
DROP TABLE IF EXISTS `department`;
CREATE TABLE IF NOT EXISTS `department` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT UNIQUE,
  `department` varchar(30) NOT NULL UNIQUE,
  `is_valid` tinyint(1) NOT NULL DEFAULT 1,
  `other1` varchar(30),
  `other2` varchar(30),
  `other3` varchar(30),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```
插入标准模板：
``` sql
TRUNCATE department;
INSERT INTO `department` (`department`) VALUES ('计算机部门'),('软件部门'),('电子部门'),('工程部门');
```

### 4、plugin_content（服务协议表）：存储服务对应的协议信息
建表操作：
``` sql
DROP TABLE IF EXISTS `plugin_content`;
CREATE TABLE IF NOT EXISTS `department` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT UNIQUE,
  `title` varchar(30) NOT NULL UNIQUE,
  `content` mediumtext NOT NULL,
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_valid` tinyint(1) NOT NULL DEFAULT 1,
  `other1` varchar(30),
  `other2` varchar(30),
  `other3` varchar(30),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```
插入标准模板：
``` sql
TRUNCATE plugin_content;
INSERT INTO `plugin_content` (`title`, `content`) VALUES ('固定车证入校须知', '1、遵守北京大学软件与微电子学院校园交通管理，接受并配合门卫人员和校园巡查人员的检查，服从指挥。 2、进入校园后，文明行车，限速15公里/小时行驶，避让一切行人和非机动车，不鸣笛，不使用远光灯，不占道停车，不占消防通道。 3、妥善保管通行证，专车专用，不伪造车证，不为可能导致伪造的扫描、复印、拍照等行为提供便利。车证正向放置。 4、不运送与本校无工作关系的人员入校，不为校园违规行为提供便利。 5、如学校需要，及时挪走车辆。 6、如果老师需要，请及时挪走车辆 本人郑重声明：已阅知并承诺履行上述条款，如有违反，自愿接受限制入校禁令。'),('临时车辆入校须知', '1、遵守北京大学软件与微电子学院校园交通管理，接受并配合门卫人员和校园巡查人员的检查，服从指挥。 2、进入校园后，文明行车，限速15公里/小时行驶，避让一切行人和非机动车，不鸣笛，不使用远光灯，不占道停车，不占消防通道。 3、妥善保管通行证，专车专用，不伪造车证，不为可能导致伪造的扫描、复印、拍照等行为提供便利。车证正向放置。 4、不运送与本校无工作关系的人员入校，不为校园违规行为提供便利。 5、临时车辆不得停靠超过3个小时。 6、如学校需要，及时挪走车辆。 本人郑重声明：已阅知并承诺履行上述条款，如有违反，自愿接受限制入校禁令。'),( '临时人员入校须知', '1、遵守北京大学软件与微电子学院校园交通管理，接受并配合门卫人员和校园巡查人员的检查，服从指挥。 2、进入校园后，文明行车，限速15公里/小时行驶，避让一切行人和非机动车，不鸣笛，不使用远光灯，不占道停车，不占消防通道。 3、妥善保管通行证，专车专用，不伪造车证，不为可能导致伪造的扫描、复印、拍照等行为提供便利。车证正向放置。 4、校外人员不得允许，不得在校留宿。 5、如学校需要，及时离开学校。 本人郑重声明：已阅知并承诺履行上述条款，如有违反，自愿接受限制入校禁令。');
```
