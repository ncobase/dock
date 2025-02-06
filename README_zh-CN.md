# Dock 管理系统

Dock 是一个高性能、多租户的企业级管理系统。它支持灵活的租户隔离策略，内置完善的权限控制和安全防护，同时提供丰富的企业应用功能。系统易于扩展，可快速构建企业级应用。

[English](./README.md) | 中文

## 🚀 特性概览

- **多租户架构**

  - 支持独立数据库、共享数据库隔离租户
  - 灵活的租户菜单权限配置
  - 租户数据完全隔离
  - 多租户模板配置

- **数据库支持**

  - MySQL、Oracle、PostgreSQL、SQLServer
  - 达梦、金仓等国产数据库
  - 动态数据源、读写分离
  - 多种主键策略

- **安全特性**
  - 数据权限控制
  - 文件上传防护
  - 数据加密脱敏
  - 操作日志审计

## 💻 技术栈

- Spring Boot 3.x
- Vue 3 + Element Plus
- 多种数据库适配
- Redis + Sa-Token

## 📁 项目结构

```bash
dock
├── script                # 脚本文件
│   ├── sql               # SQL 脚本
│   ├── bin               # 执行脚本
│   └── docker            # Docker 相关
├── dock-extend           # 扩展模块
│   ├── dock-monitor      # 监控模块
│   └── dock-snailjob     # 任务调度
├── dock-modules          # 业务模块
│   ├── dock-demo         # 演示模块
│   ├── dock-generator    # 代码生成
│   ├── dock-system       # 系统模块
│   ├── dock-workflow     # 工作流
│   └── dock-job          # 定时任务
├── dock-framework        # 框架 /  通用模块，常用于其他项目
│   ├── dock-framework-deps          # 依赖管理
│   ├── dock-framework-core         # 核心模块
│   ├── dock-framework-docs          # 文档模块
│   ├── dock-framework-excel        # Excel 模块
│   ├── dock-framework-json         # JSON 模块
│   ├── dock-framework-logger          # 日志模块
│   ├── dock-framework-mail         # 邮件模块
│   ├── dock-framework-mybatis      # MyBatis 模块
│   ├── dock-framework-oss          # 对象存储
│   ├── dock-framework-redis        # 缓存模块
│   ├── dock-framework-satoken      # 认证模块
│   ├── dock-framework-security     # 安全模块
│   └── ...               # 其他框架组件
└── dock-server           # 启动模块
```

## ⚡ 快速开始

### 环境要求

- JDK 17+
- MySQL 8.0+ / Oracle / PostgreSQL / SQLServer
- Redis 6.0+
- Node.js 16+

### 开发环境部署

#### 获取代码

```bash
git clone https://github.com/ncobase/dock.git
cd dock
```

#### 导入数据库

```bash
# 创建数据库并导入对应数据库脚本
mysql -u root -p dock < ./script/sql/dock.sql
```

#### 启动后端

```bash
mvn clean package
java -jar dock-server/target/dock-server.jar
```

## 📚 功能特性

- 租户管理：多租户体系、套餐管理、租户模板
- 系统功能：用户角色、权限管理、组织架构
- 系统监控：服务监控、日志审计、性能监控
- 开发工具：代码生成、数据字典、定时任务
- 工作流程：工作流配置、表单管理、流程设计

## 📄 开源协议

本项目使用 [MIT 许可证](LICENSE)。

## ❤️ 致谢

本项目基于 [RuoYi-Vue-Plus](https://gitee.com/dromara/RuoYi-Vue-Plus) 重构开发，特此感谢。
