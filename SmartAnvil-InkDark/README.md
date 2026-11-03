# Smart Anvil by InkDark — Minecraft 1.21.11 / Fabric

This is an initial source project package for the requested client-side mod.
The GUI/client Java sources use Yarn-mapped names, matching the `yarn_mappings`
configured in `gradle.properties`.

## Target
- Minecraft Java Edition 1.21.11
- Java 21
- Fabric Loader 0.19.3
- Fabric API 0.141.4+1.21.11
- Yarn mappings 1.21.11+build.6
- Mod Menu 17.0.1 (runtime dependency for the config entry)

## Current implementation status
Included in this source package:
- Fabric client entrypoint and `O` keybind to open settings.
- Mod Menu config-screen entrypoint.
- Persistent settings in `config/smartanvil.json`.
- In-game settings for mode, left/right priority, delay, enchantment-line and total-level limits, auto-removal toggle, and status messages.
- Game-independent enchantment counting, limit checks, and a basic Smart Mode ordering primitive.
- Safety guard primitives for validating a result and pausing when chest storage is unavailable.

**Important:** this package is not yet a complete automatic anvil operator. The inventory scanning, enchantment compatibility extraction from live `ItemStack` data, server-aware cost planning, slot-click state machine, chest routing/drop behavior, and end-to-end gameplay tests still need implementation and verification. The included planner primitives are not presented as a completed automation engine. No compiled JAR is included because Gradle is not installed in the current build environment and dependency downloads/build verification have not been run here.

## Build
Install JDK 21 and use Gradle 8.14+ or a compatible Gradle release for the configured Fabric Loom version. From this folder run:

- Windows: `gradle build`
- Linux/macOS: `gradle build`

The remapped mod JAR should be in `build/libs/` after a successful build. Install Fabric API and Mod Menu for Minecraft 1.21.11 in the game profile.

## Safety defaults
Automatic removal is OFF by default. Do not enable item-removal automation until the inventory/chest-routing state machine is implemented and tested in a disposable world.


## Fix in this revision

- The settings screen uses a solid `DrawContext.fill` background instead of `renderBackground`, avoiding the duplicate frame-blur crash (`Can only blur once per frame`) reported on Minecraft 1.21.11.
- This project remains a client-side configuration and enchantment-planning foundation. It does not yet perform automatic anvil slot clicks or server-authoritative item transfers.


## Enchantment priority editor

Open the Smart Anvil settings screen and choose **Edit enchantment priority**. The editor lists registered enchantments in a two-column table (enchantment name and saved priority rank), with up/down controls. The search field filters by identifier or display-style name without changing the underlying order. The order is saved to `config/smartanvil.json` in `enchantmentPriorityCsv`.
