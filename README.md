# darkpixel

Paper 1.21.4 server utility plugin with lobby, rank, GUI, NPC, and moderation helpers.

darkpixel is a messy but ambitious Minecraft server helper plugin: part lobby toolkit, part server quality-of-life layer, part place to collect useful Paper-side systems into one plugin.

## Current Scope

- Minecraft Paper/Spigot plugin.
- Build system: Gradle.
- Main class: `src/main/java/com/darkpixel/Main.java`.
- Plugin descriptor: `src/main/resources/plugin.yml`.
- Target shown by the repository metadata: Paper 1.21.4-era server work.

## Feature Areas

The repository structure and plugin descriptor point to systems such as:

- lobby/server helper features
- GUI and NPC helpers
- rank and moderation helpers
- AI chat or assistant-style server interactions
- general server utility commands

Treat the exact command set as code-defined until the README is expanded with a full command table.

## Build

```bash
./gradlew build
```

On Windows:

```powershell
.\gradlew.bat build
```

The plugin jar should be tested on a local or staging Paper server before touching a real production server.

## Configuration And Deployment

- Check `plugin.yml` for commands, permissions, and main class wiring.
- Review resource/config templates before deployment.
- Do not commit real server addresses, API tokens, database credentials, or production-only secrets.

## Project Status

Experimental server plugin. It has real feature direction, but the public docs still need a command list, permission matrix, and supported Paper version table.

## License

MIT.
