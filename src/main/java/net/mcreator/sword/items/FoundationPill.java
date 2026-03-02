package net.mcreator.sword.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.mcreator.sword.cultivation.CultivationManager;

public class FoundationPill extends Item {
    public FoundationPill() {
        super(new Item.Properties().stacksTo(64));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            CultivationManager.addExperience(player, 500);
            player.displayClientMessage(Component.literal("服用筑基丹，获得500点修仙经验"), true);
            stack.shrink(1);
            
            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                CultivationManager.syncToClient(serverPlayer);
            }
        }
        
        return InteractionResultHolder.success(stack);
    }
}
