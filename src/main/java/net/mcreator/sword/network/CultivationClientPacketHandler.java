package net.mcreator.sword.network;

import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.mcreator.sword.SwordMod;
import net.mcreator.sword.client.CultivationUI;

public class CultivationClientPacketHandler {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(
            new ResourceLocation(SwordMod.MODID, "cultivation_data_sync"),
            (client, handler, buffer, responseSender) -> {
                CultivationDataSyncPacket packet = new CultivationDataSyncPacket(buffer);
                client.execute(() -> {
                    packet.handle(new CultivationPacket.Context(client.level, client.player));
                    CultivationUI.onCultivationDataReceived();
                });
            }
        );
        
        ClientPlayNetworking.registerGlobalReceiver(
            new ResourceLocation(SwordMod.MODID, "version_sync"),
            (client, handler, buffer, responseSender) -> {
                VersionSyncPacket packet = new VersionSyncPacket(buffer);
                client.execute(() -> {
                    packet.handle(new CultivationPacket.Context(client.level, client.player));
                });
            }
        );
    }
    
    public static void requestCultivationData() {
        CultivationDataRequestPacket packet = new CultivationDataRequestPacket();
        FriendlyByteBuf buffer = PacketByteBufs.create();
        packet.encode(buffer);
        ClientPlayNetworking.send(packet.getId(), buffer);
    }
}