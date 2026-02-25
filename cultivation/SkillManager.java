package net.mcreator.sword.cultivation;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.mcreator.sword.cultivation.CultivationManager;
import net.mcreator.sword.cultivation.CultivationData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillManager {
    private static final Map<UUID, Map<CultivationTechnique, Integer>> cooldowns = new HashMap<>();
    private static final Map<UUID, Integer> shieldTimers = new HashMap<>();
    private static final Map<UUID, Integer> defenseBoostTimers = new HashMap<>();
    private static final Map<UUID, Integer> damageBoostTimers = new HashMap<>();

    public static boolean canUseSkill(Player player, CultivationTechnique technique) {
        if (!technique.isActiveSkill()) return false;
        
        CultivationData data = CultivationManager.getCultivationData(player);
        if (!data.hasStartedCultivation()) return false;
        
        if (data.getSpiritualPower() < technique.getSpiritualPowerCost()) {
            player.displayClientMessage(Component.literal("灵力不足！需要 " + technique.getSpiritualPowerCost() + " 点灵力"), true);
            return false;
        }
        
        if (isOnCooldown(player, technique)) {
            int remainingCooldown = getRemainingCooldown(player, technique);
            player.displayClientMessage(Component.literal("技能冷却中！还需 " + (remainingCooldown / 20) + " 秒"), true);
            return false;
        }
        
        return true;
    }

    public static void useSkill(Player player, CultivationTechnique technique) {
        if (!canUseSkill(player, technique)) return;
        
        CultivationData data = CultivationManager.getCultivationData(player);
        Level level = player.level();
        
        data.consumeSpiritualPower(technique.getSpiritualPowerCost());
        CultivationManager.saveCultivationData(player, data);
        
        setCooldown(player, technique, technique.getCooldownTicks());
        
        switch (technique) {
            case FIRE_BALL:
                castFireBall(player, level);
                break;
            case ICE_SHARD:
                castIceShard(player, level);
                break;
            case LIGHTNING_BOLT:
                castLightningBolt(player, level);
                break;
            case WIND_BLADE:
                castWindBlade(player, level);
                break;
            case EARTH_SPIKE:
                castEarthSpike(player, level);
                break;
            case HEALING_LIGHT:
                castHealingLight(player, level);
                break;
            case SHIELD_BARRIER:
                castShieldBarrier(player, level);
                break;
            case TELEPORT:
                castTeleport(player, level);
                break;
            case FLYING_SWORD:
                castFlyingSword(player, level);
                break;
            case ELEMENTAL_BURST:
                castElementalBurst(player, level);
                break;
            case SPIRITUAL_AURA:
                castSpiritualAura(player, level);
                break;
            case BODY_FORTIFICATION:
                castBodyFortification(player, level);
                break;
            case SOUL_RESONANCE:
                castSoulResonance(player, level);
                break;
            case TIME_DILATION:
                castTimeDilation(player, level);
                break;
            default:
                break;
        }
        
        if (player instanceof ServerPlayer serverPlayer) {
            CultivationManager.syncToClient(serverPlayer);
        }
    }

    private static void castFireBall(Player player, Level level) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 startPos = player.getEyePosition().add(lookVec.scale(1.5));
        
        for (int i = 0; i < 20; i++) {
            Vec3 pos = startPos.add(lookVec.scale(i * 0.5));
            level.addParticle(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 0, 0, 0);
        }
        
        Vec3 hitPos = startPos.add(lookVec.scale(10));
        AABB area = new AABB(hitPos.x - 3, hitPos.y - 3, hitPos.z - 3, 
                           hitPos.x + 3, hitPos.y + 3, hitPos.z + 3);
        
        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity != player) {
                entity.hurt(DamageSource.playerAttack(player), 8.0F);
                entity.setSecondsOnFire(3);
            }
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("火球术！"), true);
    }

    private static void castIceShard(Player player, Level level) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 startPos = player.getEyePosition().add(lookVec.scale(1.5));
        
        for (int i = 0; i < 15; i++) {
            Vec3 pos = startPos.add(lookVec.scale(i * 0.5));
            level.addParticle(ParticleTypes.SNOWFLAKE, pos.x, pos.y, pos.z, 0, 0, 0);
        }
        
        Vec3 hitPos = startPos.add(lookVec.scale(8));
        AABB area = new AABB(hitPos.x - 2, hitPos.y - 2, hitPos.z - 2, 
                           hitPos.x + 2, hitPos.y + 2, hitPos.z + 2);
        
        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity != player) {
                entity.hurt(DamageSource.playerAttack(player), 6.0F);
                entity.setSpeed(0.2F);
            }
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("冰锥术！"), true);
    }

    private static void castLightningBolt(Player player, Level level) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 hitPos = player.getEyePosition().add(lookVec.scale(12));
        
        for (int i = 0; i < 30; i++) {
            double x = hitPos.x + (Math.random() - 0.5) * 6;
            double z = hitPos.z + (Math.random() - 0.5) * 6;
            level.addParticle(ParticleTypes.ELECTRIC_SPARK, x, hitPos.y, z, 0, 0, 0);
        }
        
        AABB area = new AABB(hitPos.x - 4, hitPos.y - 4, hitPos.z - 4, 
                           hitPos.x + 4, hitPos.y + 4, hitPos.z + 4);
        
        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity != player) {
                entity.hurt(DamageSource.playerAttack(player), 10.0F);
            }
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("雷电术！"), true);
    }

    private static void castWindBlade(Player player, Level level) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 startPos = player.getEyePosition().add(lookVec.scale(1.5));
        
        for (int i = 0; i < 25; i++) {
            Vec3 pos = startPos.add(lookVec.scale(i * 0.6));
            level.addParticle(ParticleTypes.SWEEP_ATTACK, pos.x, pos.y, pos.z, 0, 0, 0);
        }
        
        Vec3 hitPos = startPos.add(lookVec.scale(15));
        AABB area = new AABB(hitPos.x - 2, hitPos.y - 2, hitPos.z - 2, 
                           hitPos.x + 2, hitPos.y + 2, hitPos.z + 2);
        
        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity != player) {
                entity.hurt(DamageSource.playerAttack(player), 7.0F);
            }
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.WIND_CHARGE_BURST, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("风刃术！"), true);
    }

    private static void castEarthSpike(Player player, Level level) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 hitPos = player.getEyePosition().add(lookVec.scale(10));
        
        for (int i = 0; i < 20; i++) {
            double x = hitPos.x + (Math.random() - 0.5) * 5;
            double z = hitPos.z + (Math.random() - 0.5) * 5;
            level.addParticle(ParticleTypes.BLOCK, x, hitPos.y, z, 0, 1, 0);
        }
        
        AABB area = new AABB(hitPos.x - 3, hitPos.y - 3, hitPos.z - 3, 
                           hitPos.x + 3, hitPos.y + 3, hitPos.z + 3);
        
        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity != player) {
                entity.hurt(DamageSource.playerAttack(player), 9.0F);
            }
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("地刺术！"), true);
    }

    private static void castHealingLight(Player player, Level level) {
        player.heal(15.0F);
        
        for (int i = 0; i < 30; i++) {
            double x = player.getX() + (Math.random() - 0.5) * 2;
            double y = player.getY() + Math.random() * 2;
            double z = player.getZ() + (Math.random() - 0.5) * 2;
            level.addParticle(ParticleTypes.HEART, x, y, z, 0, 0.1, 0);
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("治疗之光！恢复15点生命"), true);
    }

    private static void castShieldBarrier(Player player, Level level) {
        shieldTimers.put(player.getUUID(), 200);
        
        for (int i = 0; i < 40; i++) {
            double angle = (i / 40.0) * Math.PI * 2;
            double x = player.getX() + Math.cos(angle) * 1.5;
            double z = player.getZ() + Math.sin(angle) * 1.5;
            level.addParticle(ParticleTypes.TOTEM_OF_UNDYING, x, player.getY() + 1, z, 0, 0, 0);
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("护体盾！获得10秒护盾"), true);
    }

    private static void castTeleport(Player player, Level level) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 newPos = player.getEyePosition().add(lookVec.scale(8));
        
        for (int i = 0; i < 20; i++) {
            double x = player.getX() + (Math.random() - 0.5) * 2;
            double y = player.getY() + Math.random() * 2;
            double z = player.getZ() + (Math.random() - 0.5) * 2;
            level.addParticle(ParticleTypes.PORTAL, x, y, z, 0, 0, 0);
        }
        
        player.teleportTo(newPos.x, newPos.y, newPos.z);
        
        for (int i = 0; i < 20; i++) {
            double x = player.getX() + (Math.random() - 0.5) * 2;
            double y = player.getY() + Math.random() * 2;
            double z = player.getZ() + (Math.random() - 0.5) * 2;
            level.addParticle(ParticleTypes.PORTAL, x, y, z, 0, 0, 0);
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("瞬移术！"), true);
    }

    private static void castFlyingSword(Player player, Level level) {
        player.setDeltaMovement(player.getLookAngle().scale(1.5));
        
        for (int i = 0; i < 30; i++) {
            Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(i * 0.3));
            level.addParticle(ParticleTypes.SWEEP_ATTACK, pos.x, pos.y, pos.z, 0, 0, 0);
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("御剑术！"), true);
    }

    private static void castElementalBurst(Player player, Level level) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 hitPos = player.getEyePosition().add(lookVec.scale(15));
        
        for (int i = 0; i < 50; i++) {
            double x = hitPos.x + (Math.random() - 0.5) * 8;
            double y = hitPos.y + (Math.random() - 0.5) * 8;
            double z = hitPos.z + (Math.random() - 0.5) * 8;
            level.addParticle(ParticleTypes.EXPLOSION, x, y, z, 0, 0, 0);
        }
        
        AABB area = new AABB(hitPos.x - 5, hitPos.y - 5, hitPos.z - 5, 
                           hitPos.x + 5, hitPos.y + 5, hitPos.z + 5);
        
        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity != player) {
                entity.hurt(DamageSource.playerAttack(player), 15.0F);
                entity.setSecondsOnFire(5);
            }
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("元素爆发！"), true);
    }

    private static void castSpiritualAura(Player player, Level level) {
        damageBoostTimers.put(player.getUUID(), 300);
        
        for (int i = 0; i < 25; i++) {
            double angle = (i / 25.0) * Math.PI * 2;
            double x = player.getX() + Math.cos(angle) * 2;
            double z = player.getZ() + Math.sin(angle) * 2;
            level.addParticle(ParticleTypes.ENCHANT, x, player.getY() + 1, z, 0, 0.1, 0);
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.ENCHANTMENT_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("灵力光环！攻击力提升15秒"), true);
    }

    private static void castBodyFortification(Player player, Level level) {
        defenseBoostTimers.put(player.getUUID(), 225);
        
        for (int i = 0; i < 20; i++) {
            double x = player.getX() + (Math.random() - 0.5) * 2;
            double y = player.getY() + Math.random() * 2;
            double z = player.getZ() + (Math.random() - 0.5) * 2;
            level.addParticle(ParticleTypes.DAMAGE_INDICATOR, x, y, z, 0, 0.1, 0);
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.ANVIL_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("炼体术！防御力提升11秒"), true);
    }

    private static void castSoulResonance(Player player, Level level) {
        damageBoostTimers.put(player.getUUID(), 600);
        
        for (int i = 0; i < 40; i++) {
            double angle = (i / 40.0) * Math.PI * 2;
            double x = player.getX() + Math.cos(angle) * 2.5;
            double z = player.getZ() + Math.sin(angle) * 2.5;
            level.addParticle(ParticleTypes.EVOKER_FANGS, x, player.getY() + 1, z, 0, 0.1, 0);
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("灵魂共鸣！伤害大幅提升30秒"), true);
    }

    private static void castTimeDilation(Player player, Level level) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 hitPos = player.getEyePosition().add(lookVec.scale(10));
        
        for (int i = 0; i < 60; i++) {
            double x = hitPos.x + (Math.random() - 0.5) * 10;
            double y = hitPos.y + (Math.random() - 0.5) * 10;
            double z = hitPos.z + (Math.random() - 0.5) * 10;
            level.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0, 0);
        }
        
        AABB area = new AABB(hitPos.x - 6, hitPos.y - 6, hitPos.z - 6, 
                           hitPos.x + 6, hitPos.y + 6, hitPos.z + 6);
        
        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity != player) {
                entity.setSpeed(0.1F);
            }
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 1.0F, 1.0F);
        player.displayClientMessage(Component.literal("时间减缓！周围敌人行动变慢"), true);
    }

    private static void setCooldown(Player player, CultivationTechnique technique, int ticks) {
        cooldowns.computeIfAbsent(player.getUUID(), k -> new HashMap<>()).put(technique, ticks);
    }

    private static boolean isOnCooldown(Player player, CultivationTechnique technique) {
        Map<CultivationTechnique, Integer> playerCooldowns = cooldowns.get(player.getUUID());
        return playerCooldowns != null && playerCooldowns.containsKey(technique) && playerCooldowns.get(technique) > 0;
    }

    private static int getRemainingCooldown(Player player, CultivationTechnique technique) {
        Map<CultivationTechnique, Integer> playerCooldowns = cooldowns.get(player.getUUID());
        return playerCooldowns != null ? playerCooldowns.getOrDefault(technique, 0) : 0;
    }

    public static void updateCooldowns(Player player) {
        UUID uuid = player.getUUID();
        Map<CultivationTechnique, Integer> playerCooldowns = cooldowns.get(uuid);
        
        if (playerCooldowns != null) {
            playerCooldowns.entrySet().removeIf(entry -> {
                entry.setValue(entry.getValue() - 1);
                return entry.getValue() <= 0;
            });
        }
        
        if (shieldTimers.containsKey(uuid)) {
            int remaining = shieldTimers.get(uuid) - 1;
            if (remaining <= 0) {
                shieldTimers.remove(uuid);
            } else {
                shieldTimers.put(uuid, remaining);
            }
        }
        
        if (defenseBoostTimers.containsKey(uuid)) {
            int remaining = defenseBoostTimers.get(uuid) - 1;
            if (remaining <= 0) {
                defenseBoostTimers.remove(uuid);
            } else {
                defenseBoostTimers.put(uuid, remaining);
            }
        }
        
        if (damageBoostTimers.containsKey(uuid)) {
            int remaining = damageBoostTimers.get(uuid) - 1;
            if (remaining <= 0) {
                damageBoostTimers.remove(uuid);
            } else {
                damageBoostTimers.put(uuid, remaining);
            }
        }
    }

    public static boolean hasShield(Player player) {
        return shieldTimers.containsKey(player.getUUID());
    }

    public static boolean hasDefenseBoost(Player player) {
        return defenseBoostTimers.containsKey(player.getUUID());
    }

    public static boolean hasDamageBoost(Player player) {
        return damageBoostTimers.containsKey(player.getUUID());
    }

    public static float getDamageMultiplier(Player player) {
        float multiplier = 1.0F;
        if (hasDamageBoost(player)) {
            multiplier += 0.5F;
        }
        return multiplier;
    }

    public static float getDefenseMultiplier(Player player) {
        float multiplier = 1.0F;
        if (hasDefenseBoost(player)) {
            multiplier += 0.3F;
        }
        return multiplier;
    }

    public static boolean absorbDamage(Player player, float damage) {
        if (hasShield(player)) {
            shieldTimers.remove(player.getUUID());
            return true;
        }
        return false;
    }
}