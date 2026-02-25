package net.mcreator.sword.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.mcreator.sword.cultivation.CultivationManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;

public class CultivationArrayBlock extends Block {
    public CultivationArrayBlock() {
        super(Block.Properties.of().strength(2.0F, 6.0F).lightLevel(state -> 8).noCollission().noOcclusion());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        if (!level.isClientSide) {
            CultivationManager.addExperience(player, 50);
            
            if (CultivationManager.checkLevelUp(player)) {
                player.displayClientMessage(Component.literal("阵法修炼！境界提升！当前境界：" + CultivationManager.getCultivationData(player).getRealm().getDisplayName()), true);
            } else {
                player.displayClientMessage(Component.literal("阵法修炼！获得50点修仙经验"), true);
            }
            
            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                CultivationManager.syncToClient(serverPlayer);
            }
            
            if (level instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 80; i++) {
                    double angle = i * Math.PI * 2 / 80;
                    double radius = 2.0;
                    double x = pos.getX() + 0.5 + Math.cos(angle) * radius;
                    double z = pos.getZ() + 0.5 + Math.sin(angle) * radius;
                    
                    serverLevel.sendParticles(ParticleTypes.END_ROD,
                        x, pos.getY() + 0.5, z,
                        1, 0, 0.05, 0, 0.02);
                }
                
                for (int i = 0; i < 30; i++) {
                    serverLevel.sendParticles(ParticleTypes.ENCHANT,
                        pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 3.0,
                        pos.getY() + 0.5 + level.random.nextDouble() * 2.0,
                        pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 3.0,
                        1, 0, 0.1, 0, 0);
                }
            }
        }
        
        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, net.minecraft.util.RandomSource random) {
        if (level.isClientSide) {
            for (int i = 0; i < 3; i++) {
                double angle = random.nextDouble() * Math.PI * 2;
                double radius = 1.0 + random.nextDouble() * 1.5;
                double x = pos.getX() + 0.5 + Math.cos(angle) * radius;
                double z = pos.getZ() + 0.5 + Math.sin(angle) * radius;
                
                level.addParticle(ParticleTypes.END_ROD, x, pos.getY() + 0.5, z, 0, 0.03, 0);
            }
            
            for (int i = 0; i < 2; i++) {
                double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 1.0;
                double y = pos.getY() + 0.1 + random.nextDouble() * 0.5;
                double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 1.0;
                
                level.addParticle(ParticleTypes.ENCHANT, x, y, z, 0, 0.02, 0);
            }
        }
    }
}
