package net.mcreator.sword.network;

import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.server.level.ServerPlayer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.mcreator.sword.SwordMod;

public class CultivationPacketHandler {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(
            new ResourceLocation(SwordMod.MODID, "cultivation_data_request"),
            (server, player, handler, buffer, responseSender) -> {
                CultivationDataRequestPacket packet = new CultivationDataRequestPacket(buffer);
                packet.handle(new CultivationPacket.Context(player.level(), player));
            }
        );
        
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayer player = handler.getPlayer();
            sendCultivationDataToClient(player);
            sendVersionToClient(player);
        });
    }
    
    public static void sendVersionToClient(ServerPlayer player) {
        VersionSyncPacket packet = new VersionSyncPacket();
        FriendlyByteBuf buffer = PacketByteBufs.create();
        packet.encode(buffer);
        ServerPlayNetworking.send(player, packet.getId(), buffer);
    }
    
    public static void sendCultivationDataToClient(ServerPlayer player) {
        CultivationDataSyncPacket packet = new CultivationDataSyncPacket(
            net.mcreator.sword.cultivation.CultivationManager.getCultivationData(player)
        );
        FriendlyByteBuf buffer = PacketByteBufs.create();
        packet.encode(buffer);
        ServerPlayNetworking.send(player, packet.getId(), buffer);
    }
}