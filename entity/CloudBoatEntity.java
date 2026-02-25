package net.mcreator.sword.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;

import java.util.List;

public class CloudBoatEntity extends Boat {
    private static final EntityDataAccessor<Float> DATA_DAMAGE = SynchedEntityData.defineId(CloudBoatEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> DATA_TYPE = SynchedEntityData.defineId(CloudBoatEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_PADDLE_LEFT = SynchedEntityData.defineId(CloudBoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_PADDLE_RIGHT = SynchedEntityData.defineId(CloudBoatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_BUBBLE_TIME = SynchedEntityData.defineId(CloudBoatEntity.class, EntityDataSerializers.INT);
    
    private float damage;
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    private double lerpPitch;
    private boolean bubbleTime;
    private float bubbleAngle;
    private float bubbleAngleO;

    public CloudBoatEntity(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    public CloudBoatEntity(Level level, double x, double y, double z) {
        this(net.mcreator.sword.init.SwordModEntities.CLOUD_BOAT, level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_DAMAGE, 0.0F);
        this.entityData.define(DATA_TYPE, 0);
        this.entityData.define(DATA_PADDLE_LEFT, false);
        this.entityData.define(DATA_PADDLE_RIGHT, false);
        this.entityData.define(DATA_BUBBLE_TIME, 0);
    }

    @Override
    public void tick() {
        super.tick();
        
        if (!this.level().isClientSide) {
            this.setNoGravity(true);
            
            Vec3 movement = Vec3.ZERO;
            if (this.getControllingPassenger() instanceof Player player) {
                float forward = player.xxa * 0.5F;
                float strafe = player.zza * 0.5F;
                
                if (player.isShiftKeyDown()) {
                    forward *= 2.0F;
                    strafe *= 2.0F;
                }
                
                movement = new Vec3(strafe, 0, -forward);
            }
            
            this.setDeltaMovement(movement.x, movement.y, movement.z);
            
            List<Entity> passengers = this.getPassengers();
            for (Entity passenger : passengers) {
                if (passenger instanceof Player player) {
                    player.fallDistance = 0;
                    player.setNoGravity(true);
                }
            }
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        if (!this.level().isClientSide) {
            List<Entity> passengers = this.getPassengers();
            for (Entity passenger : passengers) {
                if (passenger instanceof Player player) {
                    player.setNoGravity(false);
                }
            }
        }
        super.remove(reason);
    }

    public double getMaxSpeed() {
        return 1.5D;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < 10;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.setDamage(this.getDamage() + amount * 10.0F);
            this.markHurt();
            boolean flag = this.isInvulnerableTo(source);
            if (flag || this.getDamage() > 40.0F) {
                this.remove(RemovalReason.KILLED);
            }

            return !flag;
        }
    }

    public float getDamage() {
        return this.entityData.get(DATA_DAMAGE);
    }

    public void setDamage(float damage) {
        this.entityData.set(DATA_DAMAGE, damage);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putFloat("Damage", this.getDamage());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("Damage", 99)) {
            this.setDamage(compound.getFloat("Damage"));
        }
    }
}