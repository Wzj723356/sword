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
import net.mcreator.sword.cultivation.SpiritualRoot;

import java.util.List;

public class SpiritualRootReforgingPill extends Item {
    public SpiritualRootReforgingPill() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            net.mcreator.sword.cultivation.CultivationData data = CultivationManager.getCultivationData(player);
            SpiritualRoot oldRoot = data.getSpiritualRoot();
            SpiritualRoot newRoot = SpiritualRoot.randomRoot(player.getRandom());
            
            while (newRoot == oldRoot && newRoot != SpiritualRoot.NONE) {
                newRoot = SpiritualRoot.randomRoot(player.getRandom());
            }
            
            data.setSpiritualRoot(newRoot);
            CultivationManager.saveCultivationData(player, data);
            
            player.displayClientMessage(Component.literal("灵根重铸成功！"), true);
            player.displayClientMessage(Component.literal("原灵根：" + oldRoot.getDisplayName()), false);
            player.displayClientMessage(Component.literal("新灵根：" + newRoot.getDisplayName()), false);
            
            stack.shrink(1);
            
            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                CultivationManager.syncToClient(serverPlayer);
            }
        }
        
        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.literal("灵根重铸丹"));
        tooltip.add(Component.literal("使用后重新随机灵根"));
        tooltip.add(Component.literal("极难获得，请谨慎使用"));
    }
}