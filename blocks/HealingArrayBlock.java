package net.mcreator.sword.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;

public class HealingArrayBlock extends Block {
    public HealingArrayBlock() {
        super(Block.Properties.of().strength(2.0F, 6.0F).lightLevel(state -> 12).noCollission().noOcclusion());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        if (!level.isClientSide) {
            AABB searchBox = new AABB(pos).inflate(15.0);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, searchBox);
            
            int healCount = 0;
            for (LivingEntity entity : entities) {
                entity.heal(20.0F);
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 2, false, false));
                healCount++;
                
                if (level instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 15; i++) {
                        serverLevel.sendParticles(ParticleTypes.HEART,
                            entity.getX() + (level.random.nextDouble() - 0.5) * 1.0,
                            entity.getY() + entity.getBbHeight() + 0.5,
                            entity.getZ() + (level.random.nextDouble() - 0.5) * 1.0,
                            1, 0, 0.1, 0, 0);
                    }
                }
            }
            
            player.displayClientMessage(Component.literal("治疗法阵发动！治疗了" + healCount + "个生物"), true);
            
            if (level instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 50; i++) {
                    double angle = i * Math.PI * 2 / 50;
                    double radius = 2.5;
                    double x = pos.getX() + 0.5 + Math.cos(angle) * radius;
                    double z = pos.getZ() + 0.5 + Math.sin(angle) * radius;
                    
                    serverLevel.sendParticles(ParticleTypes.HEART,
                        x, pos.getY() + 0.5, z,
                        1, 0, 0.05, 0, 0);
                }
            }
        }
        
        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, net.minecraft.util.RandomSource random) {
        if (level.isClientSide) {
            for (int i = 0; i < 4; i++) {
                double angle = random.nextDouble() * Math.PI * 2;
                double radius = 0.5 + random.nextDouble() * 1.5;
                double x = pos.getX() + 0.5 + Math.cos(angle) * radius;
                double z = pos.getZ() + 0.5 + Math.sin(angle) * radius;
                
                level.addParticle(ParticleTypes.HEART, x, pos.getY() + 0.5, z, 0, 0.05, 0);
            }
            
            for (int i = 0; i < 3; i++) {
                double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 1.5;
                double y = pos.getY() + 0.1 + random.nextDouble() * 0.5;
                double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 1.5;
                
                level.addParticle(ParticleTypes.TOTEM_OF_UNDYING, x, y, z, 0, 0.02, 0);
            }
        }
    }
}