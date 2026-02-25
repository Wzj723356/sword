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

public class HealingPill extends Item {
    public HealingPill() {
        super(new Item.Properties().stacksTo(64));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            CultivationData cultivationData = CultivationManager.getCultivationData(player);
            
            if (cultivationData.hasStartedCultivation()) {
                player.heal(10.0F);
                cultivationData.addSpiritualPower(20);
                CultivationManager.saveCultivationData(player, cultivationData);
                player.displayClientMessage(Component.literal("服用回血丹，恢复10点生命和20点灵力"), true);
                stack.shrink(1);
                
                if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                    CultivationManager.syncToClient(serverPlayer);
                }
            } else {
                player.displayClientMessage(Component.literal("尚未开始修仙，无法服用回血丹"), true);
            }
        }
        
        return InteractionResultHolder.success(stack);
    }
}
