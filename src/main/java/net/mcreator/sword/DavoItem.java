package net.mcreator.sword;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;

import java.util.List;

public class DavoItem extends SwordItem {
    public DavoItem() {
        super(new Tier() {
            public int getUses() {
                return Integer.MAX_VALUE;
            }

            public float getSpeed() {
                return Float.MAX_VALUE;
            }

            public float getAttackDamageBonus() {
                return Float.MAX_VALUE;
            }

            public int getLevel() {
                return Integer.MAX_VALUE;
            }

            public int getEnchantmentValue() {
                return Integer.MAX_VALUE;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(net.minecraft.world.item.Items.DIAMOND));
            }
        }, 3, -2.0F, new Item.Properties()
                .fireResistant()
                .rarity(Rarity.EPIC));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        super.onCraftedBy(stack, level, player);
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, Integer.MAX_VALUE, 255, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 255, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, Integer.MAX_VALUE, 255, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, Integer.MAX_VALUE, 255, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.SATURATION, Integer.MAX_VALUE, 255, false, false));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        
        if (result && attacker instanceof Player player) {
            Level world = player.level();
            BlockPos pos = target.blockPosition();
            float radius = 5.0F;
            
            world.playSound(null, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.5F, 0.8F);
            
            for (int i = 0; i < 20; i++) {
                double x = pos.getX() + 0.5 + (world.random.nextDouble() - 0.5) * radius * 2;
                double y = pos.getY() + 0.5 + (world.random.nextDouble() - 0.5) * radius * 2;
                double z = pos.getZ() + 0.5 + (world.random.nextDouble() - 0.5) * radius * 2;
                world.addParticle(ParticleTypes.SWEEP_ATTACK, x, y, z, 0, 0, 0);
                world.addParticle(ParticleTypes.ENCHANT, x, y, z, 0, 0, 0);
            }
            
            for (LivingEntity entity : world.getEntitiesOfClass(LivingEntity.class, 
                    new net.minecraft.world.phys.AABB(pos).inflate(radius))) {
                if (entity != attacker && entity != target && !entity.isAlliedTo(attacker)) {
                    entity.hurt(world.damageSources().playerAttack(player), 8.0F);
                }
            }
        }
        
        return result;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockPos playerPos = player.blockPosition();
        float radius = 40.0F;
        
        level.playSound(null, playerPos, SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.PLAYERS, 2.0F, 0.5F);
        
        for (int i = 0; i < 100; i++) {
            double x = playerPos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * radius * 2;
            double y = playerPos.getY() + 0.5 + (level.random.nextDouble() - 0.5) * radius * 2;
            double z = playerPos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * radius * 2;
            level.addParticle(ParticleTypes.EXPLOSION, x, y, z, 0, 0, 0);
            level.addParticle(ParticleTypes.DRAGON_BREATH, x, y, z, 0, 0, 0);
        }
        
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, 
                new net.minecraft.world.phys.AABB(playerPos).inflate(radius))) {
            if (entity != player && !entity.isAlliedTo(player)) {
                entity.hurt(level.damageSources().playerAttack(player), Float.MAX_VALUE);
                entity.setSecondsOnFire(10);
            }
        }
        
        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.literal("神之刃：Davo"));
        list.add(Component.literal("右键可范围攻击的神剑"));
        list.add(Component.literal("上古神器"));
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.EPIC;
    }
}