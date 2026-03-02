package net.mcreator.sword.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.mcreator.sword.cultivation.CultivationManager;

import java.util.List;

public class CultivationStaff extends Item {
    public CultivationStaff() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            CultivationManager.addExperience(player, 10);
            
            if (CultivationManager.checkLevelUp(player)) {
                player.displayClientMessage(Component.literal("境界提升！当前境界：" + CultivationManager.getCultivationData(player).getRealm().getDisplayName()), true);
            } else {
                player.displayClientMessage(Component.literal("修炼成功！获得10点修仙经验"), true);
            }
            
            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                CultivationManager.syncToClient(serverPlayer);
            }
        }
        
        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.literal("修仙法杖"));
        tooltip.add(Component.literal("右键使用可修炼，获得修仙经验"));
        tooltip.add(Component.literal("根据灵根不同，经验获取速度不同"));
    }
}
