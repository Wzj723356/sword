package net.mcreator.sword.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.mcreator.sword.SwordMod;
import net.mcreator.sword.sect.SectData;

public class SectCreationPacket {
    public static final ResourceLocation OPEN_SCREEN = new ResourceLocation(SwordMod.MODID, "open_sect_creation");
    public static final ResourceLocation CREATE_SECT = new ResourceLocation(SwordMod.MODID, "create_sect");
    
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(CREATE_SECT, (server, player, handler, buf, responseSender) -> {
            String sectName = buf.readUtf(32767);
            server.execute(() -> {
                if (sectName.isEmpty() || sectName.length() > 20) {
                    player.displayClientMessage(Component.literal("§c宗门名称不能为空且不能超过20个字符"), true);
                    return;
                }
                
                SectData sectData = SectData.get(player.getServer());
                
                if (sectData.getPlayerSect(player.getUUID()) != null) {
                    player.displayClientMessage(Component.literal("§c你已经加入了一个宗门"), true);
                    return;
                }
                
                SectData.Sect sect = sectData.createSect(sectName, player.getUUID());
                if (sect != null) {
                    player.displayClientMessage(Component.literal("§a成功创建宗门: " + sectName), false);
                    
                    // 消耗创建令牌
                    player.getMainHandItem().shrink(1);
                } else {
                    player.displayClientMessage(Component.literal("§c宗门名称已被使用"), true);
                }
            });
        });
    }
    
    public static void openSectCreationScreen(ServerPlayer player) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        ServerPlayNetworking.send(player, OPEN_SCREEN, buf);
    }
}
