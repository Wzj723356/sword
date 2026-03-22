package net.mcreator.sword.items;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.mcreator.sword.sect.SectData;

public class SectToken extends Item {
    public SectToken() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            SectData sectData = SectData.get(serverPlayer.getServer());
            
            SectData.Sect currentSect = sectData.getPlayerSect(player.getUUID());
            if (currentSect != null) {
                player.displayClientMessage(Component.literal("你当前在宗门: " + currentSect.getName() + 
                    " (创始人: " + (currentSect.isFounder(player.getUUID()) ? "是" : "否") + 
                    ", 长老: " + (currentSect.isElder(player.getUUID()) ? "是" : "否") + ")"), false);
                player.displayClientMessage(Component.literal("成员数: " + currentSect.getMembers().size() + 
                    ", 宗门等级: " + currentSect.getLevel() + ", 声望: " + currentSect.getReputation()), false);
            } else {
                player.displayClientMessage(Component.literal("你当前没有加入任何宗门"), false);
                player.displayClientMessage(Component.literal("可用宗门列表:"), false);
                for (SectData.Sect sect : sectData.getAllSects()) {
                    player.displayClientMessage(Component.literal("- " + sect.getName() + " (等级" + sect.getLevel() + ")"), false);
                }
            }
        }
        
        return InteractionResultHolder.success(stack);
    }
}
