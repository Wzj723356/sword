package net.mcreator.sword.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.mcreator.sword.items.CultivationSword;
import net.mcreator.sword.items.CultivationBlade;
import net.mcreator.sword.items.CultivationSpear;
import net.mcreator.sword.cultivation.CultivationManager;

public class TrainingTargetBlock extends Block {
    public TrainingTargetBlock() {
        super(Block.Properties.of().strength(2.0F, 6.0F));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            boolean isWeapon = heldItem.getItem() instanceof CultivationSword || 
                              heldItem.getItem() instanceof CultivationBlade || 
                              heldItem.getItem() instanceof CultivationSpear;
            
            if (isWeapon) {
                CultivationManager.addExperience(player, 20);
                player.displayClientMessage(Component.literal("功法训练！获得20点修仙经验"), true);
                return InteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(Component.literal("需要手持修仙武器才能训练"), true);
                return InteractionResult.FAIL;
            }
        }
        
        return InteractionResult.SUCCESS;
    }
}
