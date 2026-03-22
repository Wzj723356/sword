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

public class SectCreationToken extends Item {
    public SectCreationToken() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            SectData sectData = SectData.get(serverPlayer.getServer());
            
            if (sectData.getPlayerSect(player.getUUID()) != null) {
                player.displayClientMessage(Component.literal("你已经加入了一个宗门，请先退出当前宗门"), true);
                return InteractionResultHolder.fail(stack);
            }
            
            net.mcreator.sword.network.SectCreationPacket.openSectCreationScreen(serverPlayer);
        }
        
        return InteractionResultHolder.success(stack);
    }
}
