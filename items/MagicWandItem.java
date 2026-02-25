package net.mcreator.sword.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.mcreator.sword.entity.ArrayEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.item.UseAnim;
import net.minecraft.core.BlockPos;

public class MagicWandItem extends Item {
    private final String magicType;

    public MagicWandItem(String magicType, Item.Properties properties) {
        super(properties);
        this.magicType = magicType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (level.isClientSide) {
            return InteractionResultHolder.pass(stack);
        }

        HitResult hit = player.pick(20.0, 0.0f, false);
        Vec3 target = hit.getLocation();
        
        switch (magicType) {
            case "break":
                performBreakMagic(level, player, target);
                break;
            case "detect":
                performDetectMagic(level, player);
                break;
            case "seal":
                performSealMagic(level, player, target);
                break;
        }
        
        return InteractionResultHolder.success(stack);
    }

    private void performBreakMagic(Level level, Player player, Vec3 target) {
        BlockPos targetPos = new BlockPos((int)target.x, (int)target.y, (int)target.z);
        AABB searchBox = new AABB(targetPos).inflate(15.0);
        var arrays = level.getEntitiesOfClass(ArrayEntity.class, searchBox);
        
        int breakCount = 0;
        for (ArrayEntity array : arrays) {
            array.discard();
            breakCount++;
            
            if (level instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 80; i++) {
                    serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH,
                        array.getX() + (level.random.nextDouble() - 0.5) * 4.0,
                        array.getY() + 0.5 + level.random.nextDouble() * 2.0,
                        array.getZ() + (level.random.nextDouble() - 0.5) * 4.0,
                        1, 0, 0.1, 0, 0);
                }
                
                for (int i = 0; i < 50; i++) {
                    serverLevel.sendParticles(ParticleTypes.SONIC_BOOM,
                        array.getX() + (level.random.nextDouble() - 0.5) * 3.0,
                        array.getY() + 0.5 + level.random.nextDouble() * 1.5,
                        array.getZ() + (level.random.nextDouble() - 0.5) * 3.0,
                        1, 0, 0.05, 0, 0);
                }
            }
        }
        
        player.displayClientMessage(Component.literal("超位魔法·破阵！破除了" + breakCount + "个法阵！"), true);
        
        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 100; i++) {
                double angle = i * Math.PI * 2 / 100;
                double radius = 5.0;
                double x = target.x + Math.cos(angle) * radius;
                double z = target.z + Math.sin(angle) * radius;
                
                serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH, x, target.y, z, 1, 0, 0.1, 0, 0.02);
            }
            
            for (int i = 0; i < 60; i++) {
                serverLevel.sendParticles(ParticleTypes.SONIC_BOOM,
                    target.x + (level.random.nextDouble() - 0.5) * 6.0,
                    target.y + level.random.nextDouble() * 3.0,
                    target.z + (level.random.nextDouble() - 0.5) * 6.0,
                    1, 0, 0.1, 0, 0);
            }
        }
    }

    private void performDetectMagic(Level level, Player player) {
        AABB searchBox = new AABB(player.blockPosition()).inflate(50.0);
        var arrays = level.getEntitiesOfClass(ArrayEntity.class, searchBox);
        
        if (arrays.isEmpty()) {
            player.displayClientMessage(Component.literal("超位魔法·探查：附近没有发现法阵"), true);
        } else {
            player.displayClientMessage(Component.literal("超位魔法·探查：发现" + arrays.size() + "个法阵"), true);
            
            if (level instanceof ServerLevel serverLevel) {
                for (ArrayEntity array : arrays) {
                    for (int i = 0; i < 30; i++) {
                        serverLevel.sendParticles(ParticleTypes.ENCHANT,
                            array.getX() + (level.random.nextDouble() - 0.5) * 2.0,
                            array.getY() + 0.5 + level.random.nextDouble() * 1.0,
                            array.getZ() + (level.random.nextDouble() - 0.5) * 2.0,
                            1, 0, 0.05, 0, 0);
                    }
                    
                    serverLevel.sendParticles(ParticleTypes.END_ROD,
                        array.getX(), array.getY() + 0.5, array.getZ(),
                        1, 0, 0.1, 0, 0);
                }
            }
        }
    }

    private void performSealMagic(Level level, Player player, Vec3 target) {
        BlockPos targetPos = new BlockPos((int)target.x, (int)target.y, (int)target.z);
        AABB searchBox = new AABB(targetPos).inflate(10.0);
        var arrays = level.getEntitiesOfClass(ArrayEntity.class, searchBox);
        
        int sealCount = 0;
        for (ArrayEntity array : arrays) {
            array.setSealed(true);
            sealCount++;
            
            if (level instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 60; i++) {
                    double angle = i * Math.PI * 2 / 60;
                    double radius = 3.0;
                    double x = array.getX() + Math.cos(angle) * radius;
                    double z = array.getZ() + Math.sin(angle) * radius;
                    
                    serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, x, array.getY() + 0.5, z, 1, 0, 0.05, 0, 0.02);
                }
                
                for (int i = 0; i < 40; i++) {
                    serverLevel.sendParticles(ParticleTypes.ENCHANT,
                        array.getX() + (level.random.nextDouble() - 0.5) * 3.0,
                        array.getY() + 0.5 + level.random.nextDouble() * 1.5,
                        array.getZ() + (level.random.nextDouble() - 0.5) * 3.0,
                        1, 0, 0.05, 0, 0);
                }
            }
        }
        
        player.displayClientMessage(Component.literal("超位魔法·封印！封印了" + sealCount + "个法阵！"), true);
        
        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 80; i++) {
                double angle = i * Math.PI * 2 / 80;
                double radius = 4.0;
                double x = target.x + Math.cos(angle) * radius;
                double z = target.z + Math.sin(angle) * radius;
                
                serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, x, target.y, z, 1, 0, 0.05, 0, 0.02);
            }
            
            for (int i = 0; i < 50; i++) {
                serverLevel.sendParticles(ParticleTypes.ENCHANT,
                    target.x + (level.random.nextDouble() - 0.5) * 5.0,
                    target.y + level.random.nextDouble() * 2.0,
                    target.z + (level.random.nextDouble() - 0.5) * 5.0,
                    1, 0, 0.05, 0, 0);
            }
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    public String getMagicType() {
        return magicType;
    }
}