# BoneSkeleton 骷髅近战化（Minecraft 1.20.1 Fabric）

> 非官方粉丝作品，与 Mojang 和 Microsoft 无关。

把原版两种骷髅——**普通骷髅（Skeleton）、流浪者（Stray）**——全部改成近战生物（1.20.1 没有沼骸 Bogged（1.21 才添加）、没有干枯骷髅 Parched（1.21.11 才添加），对应版本请用 1.21+ 分支）：

- **普通骷髅**出生时主手拿**骨头**（原版是弓）
- **流浪者**主手拿**鱼**
- 两种都只进行**近战攻击**（哪怕是命令刷出弓或捡到弓也不射箭）
- 近战伤害 **2.0**（1 颗心，原版默认值，未做修改）
- 掉落物**不再有弓**，并追加了箭类掉落：
  - 骷髅：骨头 + **箭**（0-2 支）
  - 流浪者：鱼 + **迟缓之箭**
- **凋灵骷髅不受影响**（它原版就是持剑近战）

## 文件说明

| 路径 | 作用 |
|------|------|
| `src/main/java/.../mixin/AbstractSkeletonMixin.java` | 核心修改（2 处 Mixin） |
| `src/main/java/.../BoneSkeletonMod.java` | 模组入口 |
| `src/main/resources/data/minecraft/loot_tables/entities/skeleton.json` | 骷髅掉落表（骨头 + 箭） |
| `src/main/resources/data/minecraft/loot_tables/entities/stray.json` | 流浪者掉落表（鱼 + 迟缓之箭） |
| `src/main/resources/assets/boneskeleton/icon.png` | 模组图标 |

> 注意：1.20.1 的掉落表目录是复数 `loot_tables/`（1.21.2+ 才改成单数 `loot_table/`）。

## 怎么构建 jar

需要 **Java 17**（Gradle 会通过 foojay 自动下载缺失的 toolchain；中国网络下载失败时需手动安装 JDK 17）。在 `boneskeleton` 文件夹里打开命令行，运行：

```
gradlew build
```

成品在 `build\libs\boneskeleton-1.2.0+1.20.1.jar`。

## 怎么装进游戏

把 `build\libs\boneskeleton-1.2.0+1.20.1.jar` 复制到你的
**Minecraft 1.20.1 + Fabric** 实例的 `mods` 文件夹里，启动游戏即可。
服务端必备，客户端可不装（纯服务端逻辑模组）。

## 游戏内怎么测试

1. 打开一个世界（建议开创造模式，方便测试）
2. 聊天栏分别输入：
   - `/summon minecraft:skeleton`
   - `/summon minecraft:stray`
3. 检查：
   - 骷髅手里拿的是**骨头**，流浪者手里拿的是**鱼** ✔
   - 两种都会**跑过来打你**，而不是站远处射箭 ✔
   - 打你一下掉 **1 颗心**（普通难度）✔
   - 掉落物：骷髅掉**骨头 + 箭**，流浪者掉**鱼 + 迟缓之箭** ✔
4. 顺便刷一只**凋灵骷髅**（`/summon minecraft:wither_skeleton`），应该还是持剑近战的老样子 ✔（证明没误伤其他怪物）

## 想改伤害的话

原版骷髅近战伤害默认就是 2.0（普通难度 1 颗心），所以本模组没有改伤害代码。
