package net.mcreator.sword.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.mcreator.sword.init.SwordModBlocks;
import net.mcreator.sword.init.SwordModItems;

public class BlankTalismanPaperItem extends Item {
    public BlankTalismanPaperItem() {
        super(new Properties().stacksTo(64));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        
        if (player == null) return InteractionResult.PASS;
        
        ItemStack heldItem = player.getItemInHand(hand);
        ItemStack otherHandItem = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
        
        if (otherHandItem.getItem() instanceof TalismanBrushItem brush) {
            BlockPos placePos = pos.relative(direction);
            if (level.isEmptyBlock(placePos) || level.getBlockState(placePos).canBeReplaced()) {
                if (!level.isClientSide) {
                    level.setBlock(placePos, SwordModBlocks.TALISMAN_PAPER.defaultBlockState(), 3);
                    net.mcreator.sword.blockentity.TalismanPaperBlockEntity blockEntity = 
                        (net.mcreator.sword.blockentity.TalismanPaperBlockEntity) level.getBlockEntity(placePos);
                    if (blockEntity != null) {
                        blockEntity.setOwner(player.getUUID());
                        blockEntity.setTalismanType(brush.getSelectedType(otherHandItem));
                    }
                    heldItem.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        
        return InteractionResult.PASS;
    }
}
