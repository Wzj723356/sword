package net.mcreator.sword.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.server.level.ServerLevel;
import net.mcreator.sword.cultivation.CultivationManager;
import net.minecraft.core.particles.ParticleTypes;

public class ArrayEntity extends Entity {
    private static final EntityDataAccessor<String> DATA_ARRAY_TYPE = SynchedEntityData.defineId(ArrayEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> DATA_SEALED = SynchedEntityData.defineId(ArrayEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_CORE_HEALTH = SynchedEntityData.defineId(ArrayEntity.class, EntityDataSerializers.INT);
    
    private int tickCounter = 0;

    public ArrayEntity(EntityType<? extends ArrayEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.blocksBuilding = false;
    }

    public ArrayEntity(Level level, double x, double y, double z, String arrayType) {
        this(net.mcreator.sword.init.SwordModEntities.ARRAY, level);
        this.setPos(x, y, z);
        this.setArrayType(arrayType);
        this.setSealed(false);
        this.setCoreHealth(100);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ARRAY_TYPE, "cultivation");
        this.entityData.define(DATA_SEALED, false);
        this.entityData.define(DATA_CORE_HEALTH, 100);
    }

    @Override
    public void tick() {
        super.tick();
        tickCounter++;
        
        if (!this.level().isClientSide) {
            
        } else {
            if (this.level() != null && tickCounter % 2 == 0) {
                spawnArrayParticles();
            }
        }
    }

    private void spawnArrayParticles() {
        String arrayType = this.getArrayType();
        double centerX = this.getX();
        double centerY = this.getY();
        double centerZ = this.getZ();
        boolean sealed = this.isSealed();
        
        if (arrayType == null) {
            return;
        }
        
        if (sealed) {
            spawnSealedParticles(centerX, centerY, centerZ);
            return;
        }
        
        switch (arrayType) {
            case "cultivation":
                spawnCultivationParticles(centerX, centerY, centerZ);
                break;
            case "teleport":
                spawnTeleportParticles(centerX, centerY, centerZ);
                break;
            case "attack":
                spawnAttackParticles(centerX, centerY, centerZ);
                break;
            case "healing":
                spawnHealingParticles(centerX, centerY, centerZ);
                break;
        }
        
        spawnCoreParticles(centerX, centerY, centerZ);
    }

    private void spawnSealedParticles(double x, double y, double z) {
        if (this.level() == null) return;
        
        for (int i = 0; i < 5; i++) {
            double angle = (i * 2 * Math.PI / 5) + (tickCounter * 0.02);
            double radius = 2.0;
            double px = x + Math.cos(angle) * radius;
            double pz = z + Math.sin(angle) * radius;
            
            level().addParticle(ParticleTypes.TOTEM_OF_UNDYING, px, y + 0.1, pz, 0, 0.03, 0);
        }
        
        if (tickCounter % 6 == 0) {
            level().addParticle(ParticleTypes.ENCHANT,
                x + (level().random.nextDouble() - 0.5) * 2.0,
                y + 0.5 + level().random.nextDouble() * 1.0,
                z + (level().random.nextDouble() - 0.5) * 2.0,
                0, 0.02, 0);
        }
    }

    private void spawnCoreParticles(double x, double y, double z) {
        if (this.level() == null) return;
        
        int health = this.getCoreHealth();
        double coreSize = 0.3 + (health / 100.0) * 0.5;
        
        for (int i = 0; i < 2; i++) {
            double angle = level().random.nextDouble() * Math.PI * 2;
            double radius = coreSize * level().random.nextDouble();
            double px = x + Math.cos(angle) * radius;
            double pz = z + Math.sin(angle) * radius;
            
            level().addParticle(ParticleTypes.END_ROD, px, y + 0.5, pz, 0, 0.05, 0);
        }
        
        if (tickCounter % 40 == 0) {
            level().addParticle(ParticleTypes.FLASH, x, y + 0.5, z, 0, 0, 0);
        }
    }

    private void spawnCultivationParticles(double x, double y, double z) {
        if (this.level() == null) return;
        
        double radius = 2.0;
        int segments = 6;
        
        for (int i = 0; i < segments; i++) {
            double angle = (i * 2 * Math.PI / segments) + (tickCounter * 0.02);
            double px = x + Math.cos(angle) * radius;
            double pz = z + Math.sin(angle) * radius;
            
            level().addParticle(ParticleTypes.END_ROD, px, y + 0.1, pz, 0, 0.03, 0);
        }
        
        for (int ring = 1; ring <= 2; ring++) {
            double ringRadius = radius * ring / 2;
            for (int i = 0; i < segments * ring; i++) {
                double angle = (i * 2 * Math.PI / (segments * ring)) - (tickCounter * 0.01 * ring);
                double px = x + Math.cos(angle) * ringRadius;
                double pz = z + Math.sin(angle) * ringRadius;
                
                level().addParticle(ParticleTypes.END_ROD, px, y + 0.1, pz, 0, 0.02, 0);
            }
        }
        
        if (tickCounter % 6 == 0) {
            level().addParticle(ParticleTypes.ENCHANT, 
                x + (level().random.nextDouble() - 0.5) * 3,
                y + 0.2 + level().random.nextDouble() * 0.5,
                z + (level().random.nextDouble() - 0.5) * 3,
                0, 0.02, 0);
        }
    }

    private void spawnTeleportParticles(double x, double y, double z) {
        if (this.level() == null) return;
        
        double radius = 2.5;
        int segments = 8;
        
        for (int i = 0; i < segments; i++) {
            double angle = (i * 2 * Math.PI / segments) + (tickCounter * 0.03);
            double px = x + Math.cos(angle) * radius;
            double pz = z + Math.sin(angle) * radius;
            
            level().addParticle(ParticleTypes.PORTAL, px, y + 0.1, pz, 0, 0.05, 0);
        }
        
        if (tickCounter % 10 == 0) {
            level().addParticle(ParticleTypes.PORTAL, x, y + 0.5, z, 0, 0.1, 0);
        }
    }

    private void spawnAttackParticles(double x, double y, double z) {
        if (this.level() == null) return;
        
        double radius = 2.0;
        int segments = 4;
        
        for (int i = 0; i < segments; i++) {
            double angle = (i * 2 * Math.PI / segments) + (tickCounter * 0.04);
            double px = x + Math.cos(angle) * radius;
            double pz = z + Math.sin(angle) * radius;
            
            level().addParticle(ParticleTypes.FLAME, px, y + 0.1, pz, 0, 0.05, 0);
        }
        
        if (tickCounter % 8 == 0) {
            level().addParticle(ParticleTypes.LAVA, x, y + 0.3, z, 0, 0.02, 0);
        }
    }

    private void spawnHealingParticles(double x, double y, double z) {
        if (this.level() == null) return;
        
        double radius = 2.0;
        int segments = 6;
        
        for (int i = 0; i < segments; i++) {
            double angle = (i * 2 * Math.PI / segments) + (tickCounter * 0.025);
            double px = x + Math.cos(angle) * radius;
            double pz = z + Math.sin(angle) * radius;
            
            level().addParticle(ParticleTypes.HEART, px, y + 0.1, pz, 0, 0.05, 0);
        }
        
        if (tickCounter % 8 == 0) {
            level().addParticle(ParticleTypes.TOTEM_OF_UNDYING, x, y + 0.5, z, 0, 0.02, 0);
        }
    }

    @Override
    public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount) {
        if (this.level().isClientSide) {
            return false;
        }
        
        if (this.isSealed()) {
            if (source.getEntity() instanceof Player player) {
                player.displayClientMessage(Component.literal("法阵已被封印，无法破坏！"), true);
            }
            return false;
        }
        
        if (source.getEntity() instanceof Player) {
            int currentHealth = this.getCoreHealth();
            this.setCoreHealth(Math.max(0, currentHealth - (int) amount));
            
            if (source.getEntity() instanceof Player player) {
                player.displayClientMessage(Component.literal("攻击阵眼！阵眼生命值：" + this.getCoreHealth() + "/100"), true);
            }
            
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 20; i++) {
                    serverLevel.sendParticles(ParticleTypes.CRIT,
                        this.getX() + (this.level().random.nextDouble() - 0.5) * 1.0,
                        this.getY() + 0.5 + this.level().random.nextDouble() * 0.5,
                        this.getZ() + (this.level().random.nextDouble() - 0.5) * 1.0,
                        1, 0, 0.1, 0, 0);
                }
            }
            
            if (this.getCoreHealth() <= 0) {
                this.discard();
                if (source.getEntity() instanceof Player player) {
                    player.displayClientMessage(Component.literal("阵眼已被破坏，法阵破除！"), true);
                }
                
                if (this.level() instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 100; i++) {
                        serverLevel.sendParticles(ParticleTypes.EXPLOSION,
                            this.getX() + (this.level().random.nextDouble() - 0.5) * 3.0,
                            this.getY() + 0.5 + this.level().random.nextDouble() * 1.0,
                            this.getZ() + (this.level().random.nextDouble() - 0.5) * 3.0,
                            1, 0, 0.1, 0, 0);
                    }
                    
                    for (int i = 0; i < 80; i++) {
                        serverLevel.sendParticles(ParticleTypes.SMOKE,
                            this.getX() + (this.level().random.nextDouble() - 0.5) * 4.0,
                            this.getY() + 0.5 + this.level().random.nextDouble() * 1.5,
                            this.getZ() + (this.level().random.nextDouble() - 0.5) * 4.0,
                            1, 0, 0.05, 0, 0);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!this.level().isClientSide && player != null) {
            String arrayType = this.getArrayType();
            
            if (arrayType == null) {
                return InteractionResult.FAIL;
            }
            
            switch (arrayType) {
                case "cultivation":
                    CultivationManager.addExperience(player, 50);
                    if (CultivationManager.checkLevelUp(player)) {
                        player.displayClientMessage(Component.literal("阵法修炼！境界提升！当前境界：" + CultivationManager.getCultivationData(player).getRealm().getDisplayName()), true);
                    } else {
                        player.displayClientMessage(Component.literal("阵法修炼！获得50点修仙经验"), true);
                    }
                    triggerCultivationEffect();
                    break;
                    
                case "teleport":
                    teleportPlayer(player);
                    break;
                    
                case "attack":
                    triggerAttackEffect(player);
                    break;
                    
                case "healing":
                    triggerHealingEffect(player);
                    break;
            }
        }
        
        return InteractionResult.SUCCESS;
    }

    private void triggerCultivationEffect() {
        if (this.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 30; i++) {
                double angle = i * Math.PI * 2 / 30;
                double radius = 3.0;
                double x = this.getX() + Math.cos(angle) * radius;
                double z = this.getZ() + Math.sin(angle) * radius;
                
                serverLevel.sendParticles(ParticleTypes.END_ROD, x, this.getY() + 0.5, z, 1, 0, 0.1, 0, 0.02);
            }
            
            for (int i = 0; i < 15; i++) {
                serverLevel.sendParticles(ParticleTypes.ENCHANT,
                    this.getX() + (this.level().random.nextDouble() - 0.5) * 4.0,
                    this.getY() + 0.5 + this.level().random.nextDouble() * 2.0,
                    this.getZ() + (this.level().random.nextDouble() - 0.5) * 4.0,
                    1, 0, 0.1, 0, 0);
            }
        }
    }

    private void teleportPlayer(Player player) {
        if (player == null) return;
        
        AABB searchBox = new AABB(this.blockPosition()).inflate(50.0);
        ArrayEntity targetArray = null;
        
        for (Entity entity : this.level().getEntitiesOfClass(ArrayEntity.class, searchBox)) {
            if (entity instanceof ArrayEntity array && array != this && array.getArrayType() != null && array.getArrayType().equals("teleport")) {
                targetArray = array;
                break;
            }
        }
        
        if (targetArray != null) {
            player.teleportTo(targetArray.getX(), targetArray.getY() + 1.0, targetArray.getZ());
            player.displayClientMessage(Component.literal("传送成功！"), true);
            
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 20; i++) {
                    serverLevel.sendParticles(ParticleTypes.PORTAL,
                        targetArray.getX() + (this.level().random.nextDouble() - 0.5) * 3.0,
                        targetArray.getY() + 1.0 + this.level().random.nextDouble() * 2.0,
                        targetArray.getZ() + (this.level().random.nextDouble() - 0.5) * 3.0,
                        1, 0, 0.1, 0, 0);
                }
            }
        } else {
            player.displayClientMessage(Component.literal("没有找到其他传送阵！"), true);
        }
    }

    private void triggerAttackEffect(Player player) {
        if (player == null) return;
        
        if (this.level() instanceof ServerLevel serverLevel) {
            var enemies = this.level().getEntitiesOfClass(net.minecraft.world.entity.monster.Monster.class, new AABB(this.blockPosition()).inflate(10.0));
            
            if (enemies.isEmpty()) {
                player.displayClientMessage(Component.literal("攻击法阵发动！范围内没有敌人"), true);
                return;
            }
            
            int damageCount = 0;
            for (var enemy : enemies) {
                enemy.hurt(this.level().damageSources().playerAttack(player), 20.0F);
                damageCount++;
                
                for (int i = 0; i < 5; i++) {
                    serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH,
                        enemy.getX(),
                        enemy.getY() + enemy.getBbHeight() / 2,
                        enemy.getZ(),
                        1, 0, 0, 0, 0.05);
                }
            }
            
            player.displayClientMessage(Component.literal("攻击法阵发动！对" + damageCount + "个敌人造成伤害"), true);
            
            for (int i = 0; i < 30; i++) {
                double angle = i * Math.PI * 2 / 30;
                double radius = 3.5;
                double x = this.getX() + Math.cos(angle) * radius;
                double z = this.getZ() + Math.sin(angle) * radius;
                
                serverLevel.sendParticles(ParticleTypes.FLAME, x, this.getY() + 0.5, z, 1, 0, 0.1, 0, 0.02);
            }
        }
    }

    private void triggerHealingEffect(Player player) {
        if (player == null) return;
        
        if (this.level() instanceof ServerLevel serverLevel) {
            var entities = this.level().getEntitiesOfClass(net.minecraft.world.entity.LivingEntity.class, new AABB(this.blockPosition()).inflate(15.0));
            
            if (entities.isEmpty()) {
                player.displayClientMessage(Component.literal("治疗法阵发动！范围内没有生物"), true);
                return;
            }
            
            int healCount = 0;
            for (var entity : entities) {
                entity.heal(20.0F);
                entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.REGENERATION, 300, 2, false, false));
                healCount++;
                
                for (int i = 0; i < 5; i++) {
                    serverLevel.sendParticles(ParticleTypes.HEART,
                        entity.getX() + (this.level().random.nextDouble() - 0.5) * 1.0,
                        entity.getY() + entity.getBbHeight() + 0.5,
                        entity.getZ() + (this.level().random.nextDouble() - 0.5) * 1.0,
                        1, 0, 0.1, 0, 0);
                }
            }
            
            player.displayClientMessage(Component.literal("治疗法阵发动！治疗了" + healCount + "个生物"), true);
            
            for (int i = 0; i < 20; i++) {
                double angle = i * Math.PI * 2 / 20;
                double radius = 3.0;
                double x = this.getX() + Math.cos(angle) * radius;
                double z = this.getZ() + Math.sin(angle) * radius;
                
                serverLevel.sendParticles(ParticleTypes.HEART, x, this.getY() + 0.5, z, 1, 0, 0.05, 0, 0);
            }
        }
    }

    public String getArrayType() {
        return this.entityData.get(DATA_ARRAY_TYPE);
    }

    public void setArrayType(String type) {
        this.entityData.set(DATA_ARRAY_TYPE, type);
    }

    public boolean isSealed() {
        return this.entityData.get(DATA_SEALED);
    }

    public void setSealed(boolean sealed) {
        this.entityData.set(DATA_SEALED, sealed);
    }

    public int getCoreHealth() {
        return this.entityData.get(DATA_CORE_HEALTH);
    }

    public void setCoreHealth(int health) {
        this.entityData.set(DATA_CORE_HEALTH, health);
    }

    @Override
    protected void readAdditionalSaveData(net.minecraft.nbt.CompoundTag compound) {
        this.setArrayType(compound.getString("ArrayType"));
        this.setSealed(compound.getBoolean("Sealed"));
        this.setCoreHealth(compound.getInt("CoreHealth"));
    }

    @Override
    protected void addAdditionalSaveData(net.minecraft.nbt.CompoundTag compound) {
        compound.putString("ArrayType", this.getArrayType());
        compound.putBoolean("Sealed", this.isSealed());
        compound.putInt("CoreHealth", this.getCoreHealth());
    }
}