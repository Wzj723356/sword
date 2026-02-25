package net.mcreator.sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.sword.SwordMod;
import net.mcreator.sword.cultivation.CultivationManager;
import net.minecraft.server.level.ServerPlayer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

public class CultivationDataRequestPacket implements CultivationPacket {
    public CultivationDataRequestPacket() {
    }
    
    public CultivationDataRequestPacket(FriendlyByteBuf buffer) {
    }
    
    @Override
    public void encode(FriendlyByteBuf buffer) {
    }
    
    @Override
    public void handle(Context context) {
        if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
            CultivationDataSyncPacket syncPacket = new CultivationDataSyncPacket(
                CultivationManager.getCultivationData(serverPlayer)
            );
            FriendlyByteBuf buffer = PacketByteBufs.create();
            syncPacket.encode(buffer);
            ServerPlayNetworking.send(serverPlayer, syncPacket.getId(), buffer);
        }
    }
    
    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(SwordMod.MODID, "cultivation_data_request");
    }
}