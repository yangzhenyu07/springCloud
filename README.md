# 分布式相关技术点

## 前提:
+ mysql
+ jdk 1.8
+ springBoot + springCloud

## demo用到的技术点:
### 系统架构
+ springBoot + 日志组件logback-spring + 多配置 + 配置文件信息加密 + 多数据源 + swagger2 
+ mybatis-plus + 令牌token + 全局异常管理(自定义异常) + 统一返回数据拦截 + 跨域 + 异步线程池配置
+ 自定义编辑事务架构 + 公用日志设计 +  优雅启停 + 自定义配置集成
### 单机
+ 上传下载 + 全局参数校验 + 单机session模拟 + 单元测试(controller、service) + fastDFS集成练习
+ 关闭挂钩 + AOP两种模式实现 + spring 事件驱动模型实战 + spring 事件监听 
### 分布式
+ springCloud + Feign + 熔断机制 + eureka +  单例应用 + db乐观锁设计 + redis分布式锁设计 + redisson分布式锁设计(一次性/可重入) 
+ redis(字符串、列表、有序集合、无序集合、哈希存储)实战演示 + redisson集成redis + 缓存实战-防缓存雪崩设计 
+ 缓存实战-布隆过滤器防缓存穿透设计 + 分布式会话 + 项目启动预处理(缓存预热) + RabbitMQ普通消息模型(字节流和对象两种模式)
+ RabbitMQ消息确认 + RabbitMQ三种消息模型(广播消息模型,订阅消息模型,直连消息模型)实战
+  基于manual机制-确认消息模式(RabbitMQ) + 死信队列模型(延迟处理)【手动确认模型】+ 定时器任务设计[线程池+分布式锁]  
+ 集成kafka(自定义分区、消息过滤、异常) 
### 数据结构/算法
+ 链表的实现 + 用链表实现栈 + 用链表实现队列 + 无序数组组成二叉树 + 冒泡 + 三路快排
  

