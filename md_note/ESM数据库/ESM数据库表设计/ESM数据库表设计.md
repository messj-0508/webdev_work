# ESM数据库表设计
---
## 一、管理员相关的表结构

### 1、manage_info（管理员用户信息表）：存储管理员用户的所有信息

| 字段名               | 字段类型 | 备注 | 字段含义 |
| -------------------- | -------- | ---- | -------- |
| id | int(10) | 主键、自增 | 管理员编号 |
| user_name | varchar(30) | 非空 | 用户名 |
| user_real_name | varchar(30) | 非空 | 真实姓名 |
| user_pwd | varchar(32) | 非空 | 密码 |
| is_delete | tinyint(1) | 非空 | 是否被删除 |
| create_time | timestamp | 非空 | 记录创建时间 |
| update_time | timestamp | 非空 | 记录更新时间 |
| delete_time | timestamp | —— | 记录软删除时间 |

## 二、管理端配置相关的表结构

### 1、setting（各类用户权限表）：存储管理员用户的所有信息

| 字段名     | 字段类型    | 备注        | 字段含义             |
| -------------------- | -------- | ---- | -------- |
| id         | int(10)     | 主键、自增  | 用户类型编号         |
| type       | varchar(30) | 非空        | 用户类型             |
| f1_access  | tinyint(1)  | 非空        | 固定车证申请功能权限 |
| f2_access  | tinyint(1)  | 非空        | 临时车辆预约功能权限 |
| f3_access  | tinyint(1)  | 非空        | 临时人员预约功能权限 |
| valid_year | int(10)     | 非空，默认1 | 申请的车证有效年限   |

### 2、major（学生专业表）：存储学生专业的所有信息

| 字段名     | 字段类型    | 备注        | 字段含义             |
| -------------------- | -------- | ---- | -------- |
| id         | int(10)     | 主键、自增  | 专业编号         |
| major       | varchar(30) | 非空        | 专业名称             |
| is_valid  | tinyint(1)  | 非空，默认1  | 是否有效 |

### 3、department（教职工部门表）：存储教职工部门的所有信息

| 字段名     | 字段类型    | 备注        | 字段含义             |
| -------------------- | -------- | ---- | -------- |
| id         | int(10)     | 主键、自增  | 部门编号         |
| department | varchar(30) | 非空        | 部门名称      |
| is_valid  | tinyint(1)  | 非空        | 是否有效 |

### 4、plugin_content（服务协议表）：存储服务对应的协议信息

| 字段名     | 字段类型    | 备注        | 字段含义             |
| -------------------- | -------- | ---- | -------- |
| id         | int(10)     | 主键、自增  | 协议编号         |
| title | varchar(30) | 非空        | 协议名称      |
| content | mediumtext | 非空        | 协议内容      |
| modified_time | timestamp | 非空        | 修改时间      |
| is_valid  | tinyint(1)  | 非空        | 是否有效 |

## 三、用户端数据的表结构

### 1、application_form（车证申请单存储表）：存储固定车证申请的所有信息

| 字段名     | 字段类型    | 备注        | 字段含义             |
| -------------------- | -------- | ---- | -------- |
| id         | int(10)     | 主键、自增  | 车证申请单编号         |
| type | varchar(30) | 非空        | 用户类型      |
| usr_name  | varchar(30)  | 非空        | 用户名 |
| usr_number  | varchar(30)  | 非空        | 用户学号或职工号 |
| department  | varchar(30)  | 非空        | 用户专业或部门 |
| usr_phone  | varchar(15)  | 非空        | 用户手机号 |
| car_number  | varchar(30)  | 非空        | 车辆车牌号 |
| car_owner  | varchar(30)  | 非空        | 车辆拥有者 |
| car_card1  | varchar(100)  | 非空        | 行驶证照片1相对路径 |
| usr_card1  | varchar(100)  | 非空        | 驾驶证照片1相对路径 |
| other_img1  | varchar(100)  | 非空        | 其他照片1相对路径 |
| car_card2  | varchar(100)  | ——        | 行驶证照片2相对路径 |
| usr_card2  | varchar(100)  | ——        | 驾驶证照片2相对路径 |
| other_img2  | varchar(100)  | ——        | 其他照片2相对路径 |
| note  | varchar(200)  | ——        | 其他照片2相对路径 |
| apply_date  | timestamp  | ——        | 申请日期 |
| status  | timestamp  | ——        | 状态：0-无效 1-有效 2-初审通过 3-初审失败 4-终审通过 5-终审失败 |

### 2、car_appointment_form（临时车辆预约信息表）：存储临时车辆预约的所有信息

| 字段名     | 字段类型    | 备注        | 字段含义             |
| -------------------- | -------- | ---- | -------- |
| id         | int(10)     | 主键、自增  | 车证申请单编号         |
| type | varchar(30) | 非空        | 用户类型      |
| usr_name  | varchar(30)  | 非空        | 用户名 |
| usr_number  | varchar(30)  | 非空        | 用户学号或职工号 |
| department  | varchar(30)  | 非空        | 用户专业或部门 |
| usr_phone  | varchar(15)  | 非空        | 用户手机号 |
| driver_name  | varchar(30)  | 非空        | 车辆驾驶员姓名 |
| driver_phone  | varchar(30)  | 非空        | 车辆驾驶员电话 |
| car_number  | varchar(15)  | 非空        | 车辆车牌号 |
| reason  | varchar(100)  | 非空        | 入校原因 |
| appoint_date  | varchar(30)  | 非空        | 申请日期 |
| period  | varchar(30)  | 非空        | 申请时间 |
| note  | varchar(200)  | ——        | 其他照片2相对路径 |
| apply_date  | timestamp  | ——        | 操作时间 |

### 3、people_appointment_form（临时人员预约信息表）：存储临时人员预约的所有信息

| 字段名     | 字段类型    | 备注        | 字段含义             |
| -------------------- | -------- | ---- | -------- |
| id         | int(10)     | 主键、自增  | 车证申请单编号         |
| type | varchar(30) | 非空        | 用户类型      |
| usr_name  | varchar(30)  | 非空        | 用户名 |
| usr_number  | varchar(30)  | 非空        | 用户学号或职工号 |
| department  | varchar(30)  | 非空        | 用户专业或部门 |
| usr_phone  | varchar(15)  | 非空        | 用户手机号 |
| people_name  | varchar(30)  | 非空        | 入校人员姓名 |
| people_phone  | varchar(30)  | 非空        | 入校人员电话 |
| people_number  | varchar(15)  | 非空        | 入校人数 |
| reason  | varchar(100)  | 非空        | 入校原因 |
| appoint_date  | varchar(30)  | 非空        | 申请日期 |
| period  | varchar(30)  | 非空        | 申请时间 |
| note  | varchar(200)  | ——        | 其他照片2相对路径 |
| apply_date  | timestamp  | ——        | 操作时间 |

### 4、car_license（车证信息表）：存储临时人员预约的所有信息

| 字段名     | 字段类型    | 备注        | 字段含义             |
| -------------------- | -------- | ---- | -------- |
| id         | int(10)     | 主键、自增  | 车证申请单编号         |
| type | varchar(30) | 非空        | 用户类型      |
| usr_name  | varchar(30)  | 非空        | 用户名 |
| usr_number  | varchar(30)  | 非空        | 用户学号或职工号 |
| department  | varchar(30)  | 非空        | 用户专业或部门 |
| usr_phone  | varchar(15)  | 非空        | 用户手机号 |
| car_number  | varchar(30)  | 非空        | 车辆车牌号 |
| car_owner  | varchar(30)  | 非空        | 车辆拥有者 |
| car_card1  | varchar(100)  | ——        | 行驶证照片1相对路径 |
| usr_card1  | varchar(100)  | ——        | 驾驶证照片1相对路径 |
| other_img1  | varchar(100)  | ——        | 其他照片1相对路径 |
| car_card2  | varchar(100)  | ——        | 行驶证照片2相对路径 |
| usr_card2  | varchar(100)  | ——        | 驾驶证照片2相对路径 |
| other_img2  | varchar(100)  | ——        | 其他照片2相对路径 |
| note  | varchar(200)  | ——        | 其他照片2相对路径 |
| apply_date  | timestamp  | ——        | 申请日期 |
| pass_date  | timestamp  | ——        | 通过日期 |
| valid_date  | timestamp  | ——        | 有效截止日期 |
| is_valid  | tinyint(1)  | 非空        | 是否有效 |
| status  | timestamp  | ——        | 状态：0-无效 1-有效 2-挂失|

## 四、系统相关的表结构

### 1、operation（操作记录表）：记录管理员操作的所有信息

| 字段名     | 字段类型    | 备注        | 字段含义             |
| -------------------- | -------- | ---- | -------- |
| id    | int(10)     | 主键、自增   | 操作编号   |
| ip    | varchar(30) | 非空        | 操作ip地址 |
| user  | tinyint(1)  | 非空        | 操作用户   |
| oper  | tinyint(1)  | 非空        | 操作类型   |
| date  | tinyint(1)  | 非空        | 操作时间   |
