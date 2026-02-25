package net.mcreator.sword.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.chat.Component;

public class TalismanPaper extends Item {
    private final TalismanType type;

    public TalismanPaper(TalismanType type) {
        super(new Item.Properties().stacksTo(64).rarity(type.getRarity()));
        this.type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            switch (type) {
                case ATTACK:
                    performAttack(level, player);
                    break;
                case DEFENSE:
                    performDefense(player);
                    break;
                case HEALING:
                    performHealing(player);
                    break;
                case LIGHTNING:
                    performLightning(level, player);
                    break;
                case FIRE:
                    performFire(level, player);
                    break;
                case ICE:
                    performIce(level, player);
                    break;
                case WIND:
                    performWind(level, player);
                    break;
                case TELEPORT:
                    performTeleport(level, player);
                    break;
            }
            
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        
        return InteractionResultHolder.success(stack);
    }

    private void performAttack(Level level, Player player) {
        Vec3 look = player.getLookAngle();
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(look.scale(5.0));
        
        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(5.0))) {
            if (entity != player && entity.distanceTo(player) <= 5.0) {
                entity.hurt(level.damageSources().playerAttack(player), 10.0F);
            }
        }
        
        player.displayClientMessage(Component.literal("攻击符纸发动！"), true);
    }

    private void performDefense(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 2, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 2, false, false));
        player.displayClientMessage(Component.literal("防御符纸发动！"), true);
    }

    private void performHealing(Player player) {
        player.heal(15.0F);
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1, false, false));
        player.displayClientMessage(Component.literal("治疗符纸发动！"), true);
    }

    private void performLightning(Level level, Player player) {
        Vec3 look = player.getLookAngle();
        Vec3 target = player.getEyePosition().add(look.scale(10.0));
        
        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(10.0))) {
            if (entity != player && entity.distanceTo(player) <= 10.0) {
                entity.hurt(level.damageSources().magic(), 15.0F);
                entity.setSecondsOnFire(3);
            }
        }
        
        player.displayClientMessage(Component.literal("雷符发动！"), true);
    }

    private void performFire(Level level, Player player) {
        Vec3 look = player.getLookAngle();
        Vec3 target = player.getEyePosition().add(look.scale(8.0));
        
        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(8.0))) {
            if (entity != player && entity.distanceTo(player) <= 8.0) {
                entity.setSecondsOnFire(10);
                entity.hurt(level.damageSources().onFire(), 8.0F);
            }
        }
        
        player.displayClientMessage(Component.literal("火符发动！"), true);
    }

    private void performIce(Level level, Player player) {
        Vec3 look = player.getLookAngle();
        
        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(6.0))) {
            if (entity != player && entity.distanceTo(player) <= 6.0) {
                entity.setTicksFrozen(200);
                entity.hurt(level.damageSources().magic(), 5.0F);
            }
        }
        
        player.displayClientMessage(Component.literal("冰符发动！"), true);
    }

    private void performWind(Level level, Player player) {
        Vec3 look = player.getLookAngle();
        Vec3 velocity = look.scale(2.0);
        
        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(4.0))) {
            if (entity != player && entity.distanceTo(player) <= 4.0) {
                entity.setDeltaMovement(velocity);
                entity.hurt(level.damageSources().playerAttack(player), 6.0F);
            }
        }
        
        player.displayClientMessage(Component.literal("风符发动！"), true);
    }

    private void performTeleport(Level level, Player player) {
        Vec3 look = player.getLookAngle();
        Vec3 target = player.getEyePosition().add(look.scale(15.0));
        
        player.teleportTo(target.x, target.y, target.z);
        player.displayClientMessage(Component.literal("传送符纸发动！"), true);
    }

    public TalismanType getType() {
        return type;
    }

    public enum TalismanType {
        ATTACK("攻击符纸", Rarity.COMMON),
        DEFENSE("防御符纸", Rarity.UNCOMMON),
        HEALING("治疗符纸", Rarity.UNCOMMON),
        LIGHTNING("雷符", Rarity.RARE),
        FIRE("火符", Rarity.RARE),
        ICE("冰符", Rarity.RARE),
        WIND("风符", Rarity.RARE),
        TELEPORT("传送符纸", Rarity.EPIC);

        private final String displayName;
        private final Rarity rarity;

        TalismanType(String displayName, Rarity rarity) {
            this.displayName = displayName;
            this.rarity = rarity;
        }

        public String getDisplayName() {
            return displayName;
        }

        public Rarity getRarity() {
            return rarity;
        }
    }
}