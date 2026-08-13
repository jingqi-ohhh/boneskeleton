package com.jingqi.boneskeleton;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 骷髅近战化模组入口。
 *
 * 主要逻辑全部在 mixin 包内：
 * - AbstractSkeletonEntityMixin：出生装备换成近战武器 + 强制近战 AI
 *   （普通骷髅拿骨头，流浪者拿鱼，沼骸拿箭）
 * - data/minecraft/loot_table/entities/*.json：骷髅只掉骨头，流浪者只掉鱼，沼骸只掉箭
 */
public class BoneSkeletonMod implements ModInitializer {

    public static final String MOD_ID = "boneskeleton";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("[BoneSkeleton] 骷髅近战化已生效：骷髅持骨头，流浪者持鱼，沼骸持箭，全部近战攻击，伤害 2.0");
    }
}
