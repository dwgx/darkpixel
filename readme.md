# DarkPixel

DarkPixel 是一个面向 Paper 1.21.4 的服务器插件集合。你可以把它理解成一个「把管理功能、互动玩法和聊天能力放在一起」的插件包。

## 这个仓库主要做什么

- 服务器管理相关功能
- 玩家互动和大厅类功能
- 聊天相关扩展能力
- 一些反作弊和运维辅助能力

如果你是第一次接触，建议先在测试服里跑通，再上生产服。

## 运行环境

- Java 21
- Paper 1.21.4
- Gradle Wrapper（仓库已自带）

## 依赖插件（必装）

- ProtocolLib
- NBTAPI
- PacketEvents

缺少依赖时，插件通常无法正常加载。

## 快速开始

### 1) 编译

Windows:

```bash
gradlew.bat build
```

Linux/macOS:

```bash
./gradlew build
```

### 2) 放入服务器

构建产物在：`build/libs/`

把生成的 jar 和依赖插件一起放到服务器 `plugins/` 目录，重启服务器。

## 常见命令

- `/aichat public <内容>`：公开 AI 对话
- `/dashboard`：打开大厅面板
- `/npc ...`：管理 NPC
- `/freeze <player> ...`：冻结/解冻玩家
- `/darkac ...`：反作弊相关管理
- `/reloadconfig`：重载配置

## 配置文件

- `config.yml`：主配置
- `minigame.yml`：小游戏/传送点
- `commands.yml`：命令模板
- `darkac.yml`：反作弊
- `chat_history.yml`：聊天历史

## 开发说明

- 项目使用 Gradle 构建
- 目标 Java 版本为 21
- 建议统一使用 UTF-8 编码

## 免责声明

本项目仅用于合法场景下的服务器管理与学习研究，请遵守目标平台规则与当地法律法规。
