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
import net.minecraft.world.phys.AABB;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import java.util.List;

public class TeleportArrayBlock extends Block {
    public TeleportArrayBlock() {
        super(Block.Properties.of().strength(3.0F, 8.0F).lightLevel(state -> 10).noCollission().noOcclusion());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        if (!level.isClientSide) {
            AABB searchBox = new AABB(pos).inflate(50.0);
            List<BlockPos> teleportArrays = new java.util.ArrayList<>();
            
            for (BlockPos checkPos : BlockPos.betweenClosed(
                new BlockPos(pos.getX() - 50, pos.getY() - 50, pos.getZ() - 50),
                new BlockPos(pos.getX() + 50, pos.getY() + 50, pos.getZ() + 50)
            )) {
                if (level.getBlockState(checkPos).getBlock() instanceof TeleportArrayBlock && !checkPos.equals(pos)) {
                    teleportArrays.add(checkPos);
                }
            }
            
            if (teleportArrays.isEmpty()) {
                player.displayClientMessage(Component.literal("没有找到其他传送阵！"), true);
            } else {
                BlockPos targetPos = teleportArrays.get(player.getRandom().nextInt(teleportArrays.size()));
                player.teleportTo(targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5);
                player.displayClientMessage(Component.literal("传送成功！"), true);
                
                if (level instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 50; i++) {
                        serverLevel.sendParticles(ParticleTypes.PORTAL, 
                            targetPos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 2,
                            targetPos.getY() + 1.0 + level.random.nextDouble() * 2,
                            targetPos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 2,
                            1, 0, 0, 0, 0);
                    }
                }
            }
        }
        
        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, net.minecraft.util.RandomSource random) {
        if (level.isClientSide) {
            for (int i = 0; i < 3; i++) {
                double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 1.5;
                double y = pos.getY() + 0.1 + random.nextDouble() * 0.3;
                double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 1.5;
                
                level.addParticle(ParticleTypes.PORTAL, x, y, z, 0, 0.1, 0);
            }
            
            for (int i = 0; i < 2; i++) {
                double angle = random.nextDouble() * Math.PI * 2;
                double radius = 0.8 + random.nextDouble() * 0.2;
                double x = pos.getX() + 0.5 + Math.cos(angle) * radius;
                double z = pos.getZ() + 0.5 + Math.sin(angle) * radius;
                
                level.addParticle(ParticleTypes.END_ROD, x, pos.getY() + 0.2, z, 0, 0.05, 0);
            }
        }
    }
}