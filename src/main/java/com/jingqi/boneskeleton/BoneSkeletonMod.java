package com.jingqi.boneskeleton;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 骷髅近战化模组入口。
 *
 * 主要逻辑全部在 mixin 包内：
 * - AbstractSkeletonMixin：出生装备换成近战武器 + 强制近战 AI
 *   （普通骷髅拿骨头，流浪者拿鱼）
 * - data/minecraft/loot_tables/entities/*.json：骷髅掉骨头 + 箭，流浪者掉鱼 + 迟缓之箭
 * （1.20.1 没有沼骸 Bogged，它是 1.21 才添加的生物）
 */
public class BoneSkeletonMod implements ModInitializer {

    public static final String MOD_ID = "boneskeleton";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("[BoneSkeleton] 骷髅近战化已生效：骷髅持骨头，流浪者持鱼，全部近战攻击，伤害 2.0");
    }
}
