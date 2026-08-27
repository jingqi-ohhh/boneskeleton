package com.jingqi.boneskeleton.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 核心修改：把两种骷髅类生物（普通骷髅、流浪者）从「持弓远程」改成近战——
 * 普通骷髅拿骨头，流浪者拿鱼。
 * （1.20.1 没有沼骸 Bogged（1.21 才添加）、没有干枯骷髅 Parched（1.21.11 才添加），
 * 对应版本请用 1.21+ 分支）
 *
 * populateDefaultEquipmentSlots 与 reassessWeaponGoal 都声明在 AbstractSkeleton，
 * 两个子类都没有覆写这两个方法，全部继承自父类，
 * 因此两个回调里都加了 instanceof 判断，只对这两种骷髅生效（凋灵骷髅原版就是近战，不受影响）。
 */
@Mixin(AbstractSkeleton.class)
public abstract class AbstractSkeletonMixin {

    /**
     * 需要近战化的两种骷髅：普通骷髅、流浪者。
     * 凋灵骷髅（WitherSkeleton）原版就持剑近战，不动。
     */
    private boolean boneskeleton$isMeleeSkeleton() {
        Object self = this;
        return self instanceof Skeleton || self instanceof Stray;
    }

    /**
     * 出生初始化装备时，原版会给主手塞一把弓；
     * 这里在原版逻辑执行完之后，把主手的弓替换成近战武器：
     * 流浪者拿鱼，普通骷髅拿骨头。
     */
    @Inject(method = "populateDefaultEquipmentSlots", at = @At("RETURN"))
    private void boneskeleton$replaceBowWithMeleeWeapon(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        ItemStack weapon;
        if ((Object) this instanceof Stray) {
            weapon = new ItemStack(Items.COD);
        } else if (boneskeleton$isMeleeSkeleton()) {
            weapon = new ItemStack(Items.BONE);
        } else {
            return;
        }
        ((LivingEntity) (Object) this).setItemSlot(EquipmentSlot.MAINHAND, weapon);
    }

    /**
     * 原版在 reassessWeaponGoal 里判断「主手物品是不是弓」：
     * 是弓 → 用射箭 AI；不是 → 用近战 AI。
     * 这里把这次的 is 判断改成永远返回 false，
     * 原版逻辑会自动移除弓 AI、改用近战 AI。
     * 这样即使骷髅被命令刷出弓或从地上捡了弓，也只会近战。
     */
    @Redirect(method = "reassessWeaponGoal", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private boolean boneskeleton$neverBow(ItemStack stack, Item item) {
        if (!boneskeleton$isMeleeSkeleton()) {
            return stack.is(item);
        }
        return false;
    }
}
