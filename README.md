# BoneSkeleton 骷髅近战化（Minecraft 1.21.11 Fabric）

> 非官方粉丝作品，与 Mojang 和 Microsoft 无关。

把原版四种骷髅——**普通骷髅（Skeleton）、流浪者（Stray）、沼骸（Bogged）、干枯骷髅（Parched）**——全部改成近战生物：

- **普通骷髅**出生时主手拿**骨头**（原版是弓）
- **流浪者**主手拿**鱼**
- **沼骸**主手拿**剧毒之箭**（鼠标悬停可以看到药水属性）
- **干枯骷髅**主手拿**枯萎的灌木**
- 四种都只进行**近战攻击**（哪怕是命令刷出弓或捡到弓也不射箭）
- 近战伤害 **2.0**（1 颗心，原版默认值，未做修改）
- 掉落物**不再有弓**，并追加了箭类掉落：
  - 骷髅：骨头 + **箭**（0-2 支）
  - 流浪者：鱼 + **迟缓之箭**
  - 沼骸：箭 + **剧毒之箭**
  - 干枯骷髅：枯萎的灌木 + **虚弱之箭**
- **凋灵骷髅不受影响**（它原版就是持剑近战）

## 文件说明

| 路径 | 作用 |
|------|------|
| `src/main/java/.../mixin/AbstractSkeletonMixin.java` | 核心修改（2 处 Mixin） |
| `src/main/java/.../BoneSkeletonMod.java` | 模组入口 |
| `src/main/resources/data/minecraft/loot_table/entities/skeleton.json` | 骷髅掉落表（骨头 + 箭） |
| `src/main/resources/data/minecraft/loot_table/entities/stray.json` | 流浪者掉落表（鱼 + 迟缓之箭） |
| `src/main/resources/data/minecraft/loot_table/entities/bogged.json` | 沼骸掉落表（箭 + 剧毒之箭） |
| `src/main/resources/data/minecraft/loot_table/entities/parched.json` | 干枯骷髅掉落表（枯萎的灌木 + 虚弱之箭） |
| `src/main/resources/assets/boneskeleton/icon.png` | 模组图标 |

## 怎么构建 jar

需要 **Java 21**。在 `boneskeleton` 文件夹里打开命令行，运行：

```
gradlew build
```

成品在 `build\libs\boneskeleton-1.2.0+1.21.11.jar`。

## 怎么装进游戏

把 `build\libs\boneskeleton-1.2.0+1.21.11.jar` 复制到你的
**Minecraft 1.21.11 + Fabric** 实例的 `mods` 文件夹里，启动游戏即可。

## 游戏内怎么测试

1. 打开一个世界（建议开创造模式，方便测试）
2. 聊天栏分别输入：
   - `/summon minecraft:skeleton`
   - `/summon minecraft:stray`
   - `/summon minecraft:bogged`
   - `/summon minecraft:parched`（干枯骷髅）
3. 检查：
   - 骷髅手里拿的是**骨头**，流浪者手里拿的是**鱼**，沼骸手里拿的是**剧毒之箭**，干枯骷髅手里拿的是**枯萎的灌木** ✔
   - 四种都会**跑过来打你**，而不是站远处射箭 ✔
   - 打你一下掉 **1 颗心**（普通难度）✔
   - 掉落物：骷髅掉**骨头 + 箭**，流浪者掉**鱼 + 迟缓之箭**，沼骸掉**箭 + 剧毒之箭**，干枯骷髅掉**枯萎的灌木 + 虚弱之箭** ✔
4. 顺便刷一只**凋灵骷髅**（`/summon minecraft:wither_skeleton`），应该还是持剑近战的老样子 ✔（证明没误伤其他怪物）

## 想改伤害的话

原版骷髅近战伤害默认就是 2.0（普通难度 1 颗心），所以本模组没有改伤害代码。
以后想调整的话，告诉 Claude 目标数值，在 `AbstractSkeletonMixin`
里给骷髅的属性加上 `ATTACK_DAMAGE` 即可（僵尸空手是 3.0，可作参考）。
