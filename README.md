# Dock Management System

Dock is a high-performance, multi-tenant enterprise management system. It features flexible tenant isolation strategies, comprehensive permission controls and security protections, along with rich enterprise application functionalities. The system is easily extensible and can quickly build enterprise-level applications.

[English](./README.md) | [中文](./README_zh-CN.md)

## 🚀 Features

- **Multi-tenant Architecture**

  - Support independent database and shared database tenant isolation
  - Flexible tenant menu permission configuration
  - Complete tenant data isolation
  - Multi-tenant template configuration

- **Database Support**

  - MySQL, Oracle, PostgreSQL, SQLServer
  - Dynamic datasource, read-write separation
  - Multiple primary key strategies

- **Security Features**
  - Data permission control
  - File upload protection
  - Data encryption and desensitization
  - Operation log audit

## 💻 Tech Stack

- Spring Boot 3.x
- Vue 3 + Element Plus
- Multiple database adaptation
- Redis + Sa-Token

## 📁 Project Structure

```bash
dock
├── script                # Scripts
│   ├── sql               # SQL scripts
│   ├── bin               # Execution scripts
│   └── docker            # Docker related
├── dock-extend           # Extension modules
│   ├── dock-monitor      # Monitoring module
│   └── dock-snailjob     # Task scheduling
├── dock-modules          # Business modules
│   ├── dock-demo         # Demo module
│   ├── dock-generator    # Code generator
│   ├── dock-system       # System module
│   ├── dock-workflow     # Workflow
│   └── dock-job          # Scheduled tasks
├── dock-framework        # Framework / Common modules, commonly used in other projects
│   ├── dock-framework-deps          # Dependency management
│   ├── dock-framework-core         # Core module
│   ├── dock-framework-docs          # Documentation
│   ├── dock-framework-excel        # Excel module
│   ├── dock-framework-json         # JSON module
│   ├── dock-framework-logger          # Logging
│   ├── dock-framework-mail         # Mail module
│   ├── dock-framework-mybatis      # MyBatis module
│   ├── dock-framework-oss          # Object storage
│   ├── dock-framework-redis        # Cache module
│   ├── dock-framework-satoken      # Authentication
│   ├── dock-framework-security     # Security module
│   └── ...               # Other framework components
└── dock-server           # Startup module
```

## ⚡ Quick Start

### Requirements

- JDK 17+
- MySQL 8.0+ / Oracle / PostgreSQL / SQLServer
- Redis 6.0+
- Node.js 16+

### Development Setup

### Get the code

```bash
git clone https://github.com/ncobase/dock.git
cd dock
```

### Import database

```bash
# Create database and import corresponding database script
mysql -u root -p dock < ./script/sql/dock.sql
```

### Start backend

```bash
mvn clean package
java -jar dock-server/target/dock-server.jar
```

## 📚 Core Features

- Tenant Management: Multi-tenant system, package management, tenant templates
- System Functions: User roles, permission management, organizational structure
- System Monitoring: Service monitoring, log audit, performance monitoring
- Development Tools: Code generation, data dictionary, scheduled tasks
- Workflow: Workflow configuration, form management, process design

## 📄 License

This project is licensed under the [MIT License](LICENSE).

## ❤️ Acknowledgements

This project is rebuilt and developed based on [RuoYi-Vue-Plus](https://gitee.com/dromara/RuoYi-Vue-Plus). Special thanks to their excellent work.
