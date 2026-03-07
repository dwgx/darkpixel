# DarkPixel

DarkPixel 是一个面向 Paper 1.21.4 的综合服务器插件，包含 AI 聊天、大厅 GUI、NPC、反作弊和多种玩家交互功能。

## 环境要求

- Java 21
- Paper 1.21.4
- Gradle Wrapper（仓库已包含）

## 运行时依赖插件

- ProtocolLib
- NBTAPI
- PacketEvents

这三个插件为强依赖，需先安装到服务器 `plugins/` 目录。

## 快速安装

1. 构建插件

Windows:

```bash
gradlew.bat build
```

Linux/macOS:

```bash
./gradlew build
```

2. 构建产物位于：

`build/libs/`

3. 将构建出的 `DarkPixel` jar 与依赖插件一起放入服务器 `plugins/` 目录，启动服务器。

## 常用命令

- `/aichat public <内容>`: 公开 AI 对话
- `/dashboard`: 打开大厅面板
- `/npc ...`: 管理大厅 NPC
- `/freeze <player> ...`: 冻结/解冻玩家
- `/darkac ...`: 反作弊管理
- `/reloadconfig`: 重载配置

## 主要配置文件

- `config.yml`: 主配置
- `minigame.yml`: 小游戏/传送点配置
- `commands.yml`: AI 命令模板
- `darkac.yml`: 反作弊配置
- `chat_history.yml`: 聊天历史

## 开发说明

- `build.gradle` 已固定 `Java 21` 目标版本。
- `plugin.yml` 使用 `depend` 声明依赖，缺失依赖时插件不会加载。
- 代码和配置文件建议使用 UTF-8 编码。
