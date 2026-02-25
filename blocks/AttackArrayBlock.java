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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import java.util.List;

public class AttackArrayBlock extends Block {
    public AttackArrayBlock() {
        super(Block.Properties.of().strength(2.5F, 7.0F).lightLevel(state -> 8).noCollission().noOcclusion());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        if (!level.isClientSide) {
            AABB searchBox = new AABB(pos).inflate(10.0);
            List<Monster> enemies = level.getEntitiesOfClass(Monster.class, searchBox);
            
            int damageCount = 0;
            for (Monster enemy : enemies) {
                enemy.hurt(level.damageSources().playerAttack(player), 20.0F);
                damageCount++;
                
                if (level instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 10; i++) {
                        serverLevel.sendParticles(ParticleTypes.DRAGON_BREATH,
                            enemy.getX(),
                            enemy.getY() + enemy.getBbHeight() / 2,
                            enemy.getZ(),
                            1, 0, 0, 0, 0.05);
                    }
                }
            }
            
            player.displayClientMessage(Component.literal("攻击法阵发动！对" + damageCount + "个敌人造成伤害"), true);
            
            if (level instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 100; i++) {
                    double angle = i * Math.PI * 2 / 100;
                    double radius = 3.0;
                    double x = pos.getX() + 0.5 + Math.cos(angle) * radius;
                    double z = pos.getZ() + 0.5 + Math.sin(angle) * radius;
                    
                    serverLevel.sendParticles(ParticleTypes.FLAME,
                        x, pos.getY() + 0.5, z,
                        1, 0, 0.1, 0, 0.02);
                }
            }
        }
        
        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, net.minecraft.util.RandomSource random) {
        if (level.isClientSide) {
            for (int i = 0; i < 5; i++) {
                double angle = random.nextDouble() * Math.PI * 2;
                double radius = 1.0 + random.nextDouble() * 2.0;
                double x = pos.getX() + 0.5 + Math.cos(angle) * radius;
                double z = pos.getZ() + 0.5 + Math.sin(angle) * radius;
                
                level.addParticle(ParticleTypes.FLAME, x, pos.getY() + 0.5, z, 0, 0.05, 0);
            }
            
            for (int i = 0; i < 3; i++) {
                double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 1.0;
                double y = pos.getY() + 0.1 + random.nextDouble() * 0.5;
                double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 1.0;
                
                level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0.02, 0);
            }
        }
    }
}