### Aurora 1.0 
![https://img.shields.io/badge/build-success-brightgreen.svg?longCache=true&style=flat-square](https://img.shields.io/badge/build-success-brightgreen.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/license-Apache%202-blue.svg?longCache=true&style=flat-square](https://img.shields.io/badge/license-Apache%202-blue.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/springboot-2.0.4-yellow.svg?longCache=true&style=popout-square](https://img.shields.io/badge/springboot-2.0.4-yellow.svg?longCache=true&style=popout-square)
![https://img.shields.io/badge/apache%20shiro-1.4.0-green.svg?longCache=true&style=flat-square](https://img.shields.io/badge/apache%20shiro-1.4.0-green.svg?longCache=true&style=flat-square)

Aurora是一个简单高效的后台管理系统。项目基础框架采用全新的Java Web开发框架 —— Spring Boot2.0.4，消除了繁杂的XML配置，使得二次开发更为简单；数据访问层采用Spring boot Jpa，可快速高效对数据进行操作，消除了大量传统XML配置SQL的代码，分页使用Pageable；安全框架采用时下流行的Apache Shiro，可实现对按钮级别的权限控制；前端页面使用KidAdmin构建，并提供多套配色以供选择。
### 预览地址
[http://www.azj.life/index#/](http://www.azj.life/index#/)
<!--more-->
### 功能模块
系统功能模块组成如下所示：
```
|      _       _  _     ___      ____     ___        _
|     /.\     FJ  L]   F _ ",   F __ ]   F _ ",     /.\
|    //_\\   J |  | L J `-'(|  J |--| L J `-'(|    //_\\
|   / ___ \  | |  | | |  _  L  | |  | | |  _  L   / ___ \
|  / L___J \ F L__J J F |_\  L F L__J J F |_\  L / L___J \
| J__L   J__J\______/J__| \\__J\______/J__| \\__J__L   J__L
| |__L   J__|J______F|__|  J__|J______F|__|  J__|__L   J__|
|
| :: Spring Boot ::       (v2.0.4.RELEASE)
|
├─系统管理
│  ├─用户管理
│  ├─菜单管理
│  ├─角色管理
│  ├─权限管理
│  └─接口文档
├─系统监控
│  ├─Redis终端
│  ├─在线用户
│  └─系统日志
│─网络资源
│  ├─天气查询（开发中）
│  └─图床管理
│─第三方工具
│  │-支付宝支付（开发中）
│  │-百度富文本编辑器
│  └─云储存（开发中）
└─任务调度（开发中）
   ├─定时任务
   └─调度日志
```
### 技术选型
#### 后端

- 基础框架：Spring Boot 2.0.4.RELEASE

- 持久层框架：Spring boot Jpa

- 安全框架：Apache Shiro 1.4.0

- 摸板引擎：Thymeleaf 3.0.9.RELEASE

- 数据库连接池：阿里巴巴Druid 1.1.10

- 缓存框架：Redis

- 日志打印：logback

- sql语句内容打印: log4jdbc+logback

- IP地址池 ip2region

- 接口文档 swagger2

- 工具包：hutool-all

- 其他：fastjson，aop，MapStruct等。

#### 前端
 
- 基础框架：Layui（KidAdmin）

- JavaScript框架：jQuery

- 树形插件：ztree

- 树形选择框：treeselect

#### 开发环境

- 语言：Java 8

- IDE：IntelliJ IDEA 2018.2.2（Ultimate）

- 依赖管理：Maven

- 数据库：MySQL 5.5.59

- 版本管理：git

### 系统预览

- 前端模板使用kitadmin，前端框架使用layui

- 登录页面

![](https://i.imgur.com/drNepaT.png)

- 系统首页

![](https://i.imgur.com/LaU377e.png)

- 用户管理

![](https://i.imgur.com/ouBHhXm.jpg)

- 角色管理

![](https://i.imgur.com/WG7IxhL.png)

- 菜单管理，使用树形表格

![](https://i.imgur.com/GJw1PB9.png)

- 日志管理

![](https://i.imgur.com/rZbABhe.png)

- 搜索统一使用侧边栏

![](https://i.imgur.com/G3MmlGy.png)

- swagger文档

![](https://i.imgur.com/7uzRRq9.png)

- Redis终端

![](https://i.imgur.com/TvKl5SH.png)

- 富文本编辑器

![](https://i.imgur.com/NCvS23x.png)

- sql日志打印使用log4jdbc+logback，效果如下

![](https://i.imgur.com/R1xsFXk.jpg)

### 开发与部署

码云地址：https://gitee.com/hgpt/Aurora 

GitHub 地址：https://github.com/dqjdda/Aurora 

下载后以Maven项目的方式导入Eclipse或者IDEA。

项目需依赖redis，如果没安装reids请求将报错，安装步骤如下

#### 安装Redis

Redis的下载地址为[https://github.com/MicrosoftArchive/redis/releases](https://github.com/MicrosoftArchive/redis/releases)
这里我们下载 Redis-x64-xxx.zip压缩包到C盘，解压后。打开一个CMD窗口，输入如下命令：

```
C:\Users\Administrator>cd c:\Redis-x64-3.2.100
c:\Redis-x64-3.2.100>redis-server.exe redis.windows.conf
                _._
           _.-``__ ''-._
      _.-``    `.  `_.  ''-._           Redis 3.2.100 (00000000/0) 64 bit
  .-`` .-```.  ```\/    _.,_ ''-._
 (    '      ,       .-`  | `,    )     Running in standalone mode
 |`-._`-...-` __...-.``-._|'` _.-'|     Port: 6379
 |    `-._   `._    /     _.-'    |     PID: 6404
  `-._    `-._  `-./  _.-'    _.-'
 |`-._`-._    `-.__.-'    _.-'_.-'|
 |    `-._`-._        _.-'_.-'    |           http://redis.io
  `-._    `-._`-.__.-'_.-'    _.-'
 |`-._`-._    `-.__.-'    _.-'_.-'|
 |    `-._`-._        _.-'_.-'    |
  `-._    `-._`-.__.-'_.-'    _.-'
      `-._    `-.__.-'    _.-'
          `-._        _.-'
              `-.__.-'
[6404] 25 Dec 09:47:58.890 # Server started, Redis version 3.2.100 
```
然后打开另外一个CMD终端，输入：
```
C:\Users\Administrator>cd c:\Redis-x64-3.2.100
c:\Redis-x64-3.2.100>redis-cli.exe -p 6379
127.0.0.1:6379>
```
准备工作做完后，直接使用Spring Boot的入口类`me.aurora.AuroraRun`启动即可，访问地址[localhost]，账号zhengjie@tom.com，密码dqjdda1996.

部署时，首先使用使用mvn package将项目打包成jar，然后使用命令`java -jar aurora-0.0.1-SNAPSHOT.war`启动即可，当然也可以使用tomcat启动。

### docker 中部署
- 可参考链接[https://www.zhengjie.me/blog/271.html](https://www.zhengjie.me/blog/271.html)。

### 反馈交流

- 作者QQ：201507802

- 作者邮箱：zhengjie@tom.com

- 作者主页：https://www.zhengjie.me