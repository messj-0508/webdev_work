# LAMP环境搭建

---

LAMP是指一组通常一起使用来运行动态网站或者服务器的自由软件名称首字母缩写，即由Linux系统、Apache服务器、MariaDB或Mysql数据库、PHPl或Python脚本语言组成的一套软件。这套软件主要目的是为了运行网站。



本教程是基于阿里云服务器的，该服务器系统为CentOS系统（Linux）。主要步骤为：安装Apache2、安装Mysql、安装php、安装phpMyAdmin。教程也多有参考，此处不累述，感谢阿里云和csdn的大佬们。

---

## 一 主要流程

1. 在终端输入命令 cat /etc/redhat-release 查看系统版本。

​         ![img](https://uploader.shimo.im/f/sVKv8B0iOBEy2jiN.png!thumbnail)       



2. 关闭防火墙。输入 systemctl status firewalld 命令查看当前防火墙的状态。如果防火墙的状态参数是active，则防火墙为开启状态。如果防火墙的状态参数是inactive，则防火墙为关闭状态。临时关闭防火墙，输入命令systemctl stop firewalld；永久关闭防火墙，输入命令systemctl disable firewalld。

​         ![img](https://uploader.shimo.im/f/lwyVxJdZD2AFfGSb.png!thumbnail)       



3. 关闭SELinux。输入getenforce命令查看当前SELinux的状态。如果SELinux状态参数是Enforcing，则SELinux为开启状态。如果SELinux状态参数是Disabled， 则SELinux为关闭状态。想临时关闭SELinux，输入命令setenforce 0；永久关闭SELinux，输入命令vi /etc/selinux/config编辑SELinux配置文件。将SELINUX=enforcing修改为SELINUX=disabled。

​         ![img](https://uploader.shimo.im/f/6e2EhhLchKgpoTZm.png!thumbnail)       

## 二 安装Apache2

1. 安装依赖包。
``` bash
    yum groupinstall " Development Tools" -y
    yum install libtool
    yum install expat-devel pcre pcre-devel openssl-devel -y
```
2. 下载解压Apache，Apr和Apr-util的源码包（源代码版本会不断升级，可以在<https://mirrors.aliyun.com/apache/httpd/>和<https://mirrors.aliyun.com/apache/apr/>获取合适的安装包地址）。以下为代码示例：
```bash
    wget https://mirrors.aliyun.com/apache/httpd/httpd-2.4.37.tar.gz
    wget https://mirrors.aliyun.com/apache/apr/apr-1.6.5.tar.gz
    wget https://mirrors.aliyun.com/apache/apr/apr-util-1.6.1.tar.gz
    tar xvf httpd-2.4.37.tar.gz -C /usr/local/src
    tar xvf apr-1.6.5.tar.gz -C /usr/local/src
    tar xvf apr-util-1.6.1.tar.gz -C /usr/local/src
```
3. 把Apr和Apr-util的文件夹移到Apache的srclib文件夹下。
```bash
    cd /usr/local/src
    mv apr-1.6.5 httpd-2.4.37/srclib/apr
    mv apr-util-1.6.1 httpd-2.4.37/srclib/apr-util
```
4. 编译。
```bash
    cd /usr/local/src/httpd-2.4.37
    ./buildconf
    ./configure --prefix=/usr/local/apache2 \
    --enable-ssl \
    --enable-so \
    --with-mpm=event \
    --with-included-apr \
    --enable-cgi \
    --enable-rewrite \
    --enable-mods-shared=most \
    --enable-mpms-shared=all
    make && make install
```
5. 设置PATH环境变量并重新加载环境变量。
```bash
    echo "export PATH=$PATH:/usr/local/apache2/bin" > /etc/profile.d/httpd.sh
    source /etc/profile.d/httpd.sh
```
6. 输入命令 httpd -v 可查看Apache的版本号，出现如下图则安装成功。

​         ![img](https://uploader.shimo.im/f/OKa6P79Gmes73lgO.png!thumbnail)       

7. 添加Apache的启动配置文件。输入命令vi /usr/lib/systemd/system/httpd.service打开Apache的启动配置文件，在配置文件中写下如下内容：
```bash
    [Unit] 
    Description=The Apache HTTP Server 
    After=network.target 


    [Service] 
    Type=forking 
    ExecStart=/usr/local/apache2/bin/apachectl -k start 
    ExecReload=/usr/local/apache2/bin/apachectl -k graceful 
    ExecStop=/usr/local/apache2/bin/apachectl -k graceful-stop 
    PIDFile=/usr/local/apache2/logs/httpd.pid 
    PrivateTmp=true 


    [Install] 
    WantedBy=multi-user.targe
```
8. 启动Apache服务并设置开机自动启动。
```bash
    systemctl start httpd
    systemctl enable httpd
```
9. 输入自己的公网IP可以看到“it works！”

## 三 安装MySQL

1. 准备编译环境。
```bash
    yum install ncurses-devel bison gnutls-devel -y
    yum install cmake -y
```
2. 准备MySQL数据存放目录。
```bash
    cd
    mkdir /mnt/data
    groupadd -r mysql
    useradd -r -g mysql -s /sbin/nologin mysql
    id mysql
```
3. 更改数据目录属主和属组。
```bash
    chown -R mysql:mysql /mnt/data
```
4. 下载稳定版源码包解压编译。
```bash
    wget https://downloads.mysql.com/archives/get/file/mysql-5.6.24.tar.gz
    tar xvf mysql-5.6.24.tar.gz -C  /usr/local/src
    cd /usr/local/src/mysql-5.6.24
    cmake . -DCMAKE_INSTALL_PREFIX=/usr/local/mysql \
    > -DMYSQL_DATADIR=/mnt/data \
    > -DSYSCONFDIR=/etc \
    > -DWITH_INNOBASE_STORAGE_ENGINE=1 \
    > -DWITH_ARCHIVE_STORAGE_ENGINE=1 \
    > -DWITH_BLACKHOLE_STORAGE_ENGINE=1 \
    > -DWITH_READLINE=1 \
    > -DWITH_SSL=system \
    > -DWITH_ZLIB=system \
    > -DWITH_LIBWRAP=0 \
    > -DMYSQL_TCP_PORT=3306 \
    > -DDEFAULT_CHARSET=utf8 \
    > -DMYSQL_UNIX_ADDR=/usr/local/mysql/mysql.sock \
    > -DDEFAULT_COLLATION=utf8_general_ci \
    > -DWITH_SYSTEMD=1 \
    > -DINSTALL_SYSTEMD_UNITDIR=/usr/lib/systemd/system 
    make && make install
```
5. 修改安装目录的属组为mysql。
```bash
    chown -R mysql:mysql /usr/local/mysql/
```

6. 初始化数据库并复制配置文件。
```bash
    cd /usr/local/mysql
    /usr/local/mysql/scripts/mysql_install_db --user=mysql --datadir=/mnt/data/
    mv /etc/my.cnf /etc/my.cnf.bak
    cp /usr/local/mysql/support-files/my-default.cnf /etc/my.cnf
```

7. 修改配置文件中的安装路径及数据目录存放路径。
```bash
    echo -e "basedir = /usr/local/mysql\ndatadir = /mnt/data\n" >> /etc/my.cnf
```

8. 输入命令vi /usr/lib/systemd/system/mysql.service打开启动配置文件，写下如下内容：
```vi
    [Unit]
    Description=MySQL Community Server
    After=network.target
    After=syslog.target


    [Install]
    WantedBy=multi-user.target
    Alias=mysql.service


    [Service]
    User=mysql
    Group=mysql
    PermissionsStartOnly=true
    ExecStart=/usr/local/mysql/bin/mysqld
    TimeoutSec=600
    Restart=always
    PrivateTmp=false
```
9. 设置PATH环境变量:
``` bash
    echo "export PATH=$PATH:/usr/local/mysql/bin" > /etc/profile.d/mysql.sh
    source /etc/profile.d/mysql.sh
```
10. 启动MySQL服务并设置开机启动。
``` bash
    systemctl start mysql
    systemctl enable mysql
```
11. 修改MySQL的root用户密码。运行以下命令，并按界面提示设置密码。
``` bash
    mysqladmin -u root password
```
12. 测试登录MySQL数据库。
``` bash
	mysql -uroot -p
```
## 四 安装PHP
1. 安装依赖包。
``` bash
	yum install libmcrypt libmcrypt-devel mhash mhash-devel libxml2 libxml2-devel bzip2 bzip2-devel
```
2. 下载稳定版源码包解压编译。
``` bash
    cd
    wget http://cn2.php.net/get/php-7.0.32.tar.bz2/from/this/mirror
    cp mirror php-7.0.32.tar.bz2
    tar xvf php-7.0.32.tar.bz2 -C /usr/local/src
    cd /usr/local/src/php-7.0.32
    ./configure --prefix=/usr/local/php \
    --with-config-file-scan-dir=/etc/php.d \
    --with-apxs2=/usr/local/apache2/bin/apxs \
    --with-config-file-path=/etc \
    --with-pdo-mysql=mysqlnd \
    --with-mysqli=/usr/local/mysql/bin/mysql_config \
    --enable-mbstring \
    --with-freetype-dir \
    --with-jpeg-dir \
    --with-png-dir \
    --with-zlib \
    --with-libxml-dir=/usr \
    --with-openssl \
    --enable-xml \
    --enable-sockets \
    --enable-fpm \
    --with-bz2
    make && make install
```
3. 复制PHP的配置文件。
``` bash 
    cp php.ini-production /etc/php.ini
```
4. 输入命令`vi /usr/local/apache2/conf/httpd.conf`打开Apache配置文件
``` bash
	1.找到ServerName参数，添加ServerName localhost:80。
	2.找到Directory参数，注释掉Require all denied，添加Require all granted。
	3.找到DirectoryIndex index.html，将它替换为DirectoryIndex index.php index.html。
	4.找到如下内容：
	  AddType application/x-compress .z
      AddType application/x-gzip .gz .tgz
      在后面添加如下内容：
      AddType application/x-httpd-php .php
      AddType application/x-httpd-php-source .phps
```
5. 添加Apache对解析PHP的支持。

``` bash
	vi /usr/local/apache2/htdocs/index.php
```
``` vi
	<?php
    phpinfo();
    ?>
```
6. 重启Apache服务
``` bash
	systemctl restart httpd
```
## 五 安装phpMyAdmin。

1. 准备phpMyAdmin数据存放目录。
``` bash
    cd
    mkdir -p /usr/local/apache2/htdocs/phpmyadmin
```
2. 下载phpMyAdmin压缩包并解压。
``` bash
    wget https://files.phpmyadmin.net/phpMyAdmin/4.0.10.20/phpMyAdmin-4.0.10.20-all-languages.zip
    unzip phpMyAdmin-4.0.10.20-all-languages.zip
```
3. 复制phpMyAdmin文件到准备好的数据存放目录。
``` bash
	mv phpMyAdmin-4.0.10.20-all-languages/*  /usr/local/apache2/htdocs/phpmyadmin
```
