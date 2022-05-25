### 项目概要
这是一个汽车租赁管理系统，提供汽车租赁管理能力

### 项目框架
该项目是基于SpringBoot2.6.7版本搭建的Web后端系统，使用了MyBatis、MyBatisPlus、Redisson、JWT等框架和组件
#### 使用的外部第三方组件
1. MySQL（持久化数据存储）
2. Redis（缓存热点数据、分布式锁）

### 项目模块
1. 用户管理：提供用户注册、用户登录接口
2. 汽车租赁管理：提供汽车查询、汽车租赁、订单查询接口

### 项目本地启动
1. 启动并初始化MySQL数据库：创建数据库并执行initialization.sql文件
2. 启动Redis缓存
3. 配置application.yml中的连接参数、以及服务启动端口等信息
4. 启动服务

### 项目打Docker镜像
1. 安装maven并执行mvn package命令
2. 复制Dockerfile文件到target目录下，和jar文件同级
3. 执行docker build -t car-booking .命令

### 项目部署
服务无状态，推荐基于K8S的deployment方式进行容器化部署，可横向弹性伸缩，保障高可用

