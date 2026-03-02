package net.mcreator.sword.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.mcreator.sword.cultivation.CultivationManager;
import net.mcreator.sword.cultivation.CultivationData;

public class SpiritGatheringPill extends Item {
    public SpiritGatheringPill() {
        super(new Item.Properties().stacksTo(64));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            CultivationData cultivationData = CultivationManager.getCultivationData(player);
            
            if (cultivationData.hasStartedCultivation()) {
                // 检查灵力是否回满
                if (cultivationData.getSpiritualPower() >= cultivationData.getMaxSpiritualPower()) {
                    // 灵力回满，开始修炼
                    CultivationManager.addExperience(player, 100);
                    player.displayClientMessage(Component.literal("服用聚灵丹，灵力回满开始修炼，获得100点修仙经验"), true);
                    stack.shrink(1);
                    
                    if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                        CultivationManager.syncToClient(serverPlayer);
                    }
                } else {
                    // 灵力未回满
                    player.displayClientMessage(Component.literal("灵力未回满，无法开始修炼"), true);
                }
            } else {
                player.displayClientMessage(Component.literal("尚未开始修仙，无法服用聚灵丹"), true);
            }
        }
        
        return InteractionResultHolder.success(stack);
    }
}
