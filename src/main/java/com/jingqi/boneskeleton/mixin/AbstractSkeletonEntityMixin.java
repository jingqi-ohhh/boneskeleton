package com.jingqi.boneskeleton.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.BoggedEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 核心修改：把三种骷髅类生物（普通骷髅、流浪者、沼骸）从「持弓远程」改成近战——
 * 普通骷髅拿骨头，流浪者拿鱼，沼骸拿剧毒之箭。
 *
 * initEquipment 声明在 MobEntity、updateAttackType 声明在 AbstractSkeletonEntity，
 * 流浪者和沼骸都没有覆写这两个方法，全部继承自父类，
 * 因此两个回调里都加了 instanceof 判断，只对这三种骷髅生效（凋灵骷髅原版就是近战，不受影响）。
 */
@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin {

    /**
     * 需要近战化的三种骷髅：普通骷髅、流浪者、沼骸。
     * 凋灵骷髅（WitherSkeletonEntity）原版就持剑近战，不动。
     */
    private boolean boneskeleton$isMeleeSkeleton() {
        Object self = this;
        return self instanceof SkeletonEntity
                || self instanceof StrayEntity
                || self instanceof BoggedEntity;
    }

    /**
     * 出生初始化装备时，原版会给主手塞一把弓；
     * 这里在原版逻辑执行完之后，把主手的弓替换成近战武器：
     * 沼骸拿剧毒之箭（药水箭 + 剧毒药水组件），流浪者拿鱼，普通骷髅拿骨头。
     */
    @Inject(method = "initEquipment", at = @At("RETURN"))
    private void boneskeleton$replaceBowWithMeleeWeapon(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        ItemStack weapon;
        if ((Object) this instanceof BoggedEntity) {
            weapon = new ItemStack(Items.TIPPED_ARROW);
            weapon.set(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT.with(Potions.POISON));
        } else if ((Object) this instanceof StrayEntity) {
            weapon = new ItemStack(Items.COD);
        } else if (boneskeleton$isMeleeSkeleton()) {
            weapon = new ItemStack(Items.BONE);
        } else {
            return;
        }
        ((LivingEntity) (Object) this).equipStack(EquipmentSlot.MAINHAND, weapon);
    }

    /**
     * 原版每帧在 updateAttackType 里判断「主手物品是不是弓」：
     * 是弓 → 用射箭 AI；不是 → 用近战 AI。
     * 这里把这次的 isOf 判断改成永远返回 false，
     * 原版逻辑会自动移除弓 AI、在优先级 4 加入近战 AI。
     * 这样即使骷髅被命令刷出弓或从地上捡了弓，也只会近战。
     */
    @Redirect(method = "updateAttackType", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean boneskeleton$neverBow(ItemStack stack, Item item) {
        if (!boneskeleton$isMeleeSkeleton()) {
            return stack.isOf(item);
        }
        return false;
    }
}
