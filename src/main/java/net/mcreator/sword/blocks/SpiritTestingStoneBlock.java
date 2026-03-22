package net.mcreator.sword.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.mcreator.sword.cultivation.CultivationData;
import net.mcreator.sword.cultivation.CultivationManager;
import net.mcreator.sword.cultivation.SpiritualRoot;

public class SpiritTestingStoneBlock extends Block {
    public SpiritTestingStoneBlock() {
        super(Properties.of(Material.STONE)
                .strength(3.0F)
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            CultivationData data = CultivationManager.getData(player);
            
            player.displayClientMessage(Component.literal("§6========== 灵根测试结果 =========="), false);
            
            SpiritualRoot root = data.getSpiritualRoot();
            if (root != null) {
                player.displayClientMessage(Component.literal("§b主灵根: " + root.getPrimaryElement().getDisplayName()), false);
                player.displayClientMessage(Component.literal("§a灵根品质: " + root.getQuality().getDisplayName()), false);
                player.displayClientMessage(Component.literal("§e修炼速度加成: " + String.format("%.1f", root.getCultivationSpeedMultiplier() * 100) + "%"), false);
            } else {
                player.displayClientMessage(Component.literal("§c未检测到灵根，请先服用灵根重铸丹"), false);
            }
            
            player.displayClientMessage(Component.literal("§6================================"), false);
            player.displayClientMessage(Component.literal("§7修为境界: " + data.getRealm().getDisplayName() + " " + data.getStage() + "重"), false);
            player.displayClientMessage(Component.literal("§7当前修为: " + String.format("%.0f", data.getCurrentExp()) + " / " + String.format("%.0f", data.getMaxExp())), false);
            player.displayClientMessage(Component.literal("§7生命值加成: " + String.format("%.0f", data.getHealthBonus())), false);
            player.displayClientMessage(Component.literal("§7灵力值: " + String.format("%.0f", data.getMana()) + " / " + String.format("%.0f", data.getMaxMana())), false);
            player.displayClientMessage(Component.literal("§7神识范围: " + String.format("%.0f", data.getSpiritualSenseRange()) + " 格"), false);
            player.displayClientMessage(Component.literal("§6================================"), false);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
