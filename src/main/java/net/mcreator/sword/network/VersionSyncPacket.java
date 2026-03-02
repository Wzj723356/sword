package net.mcreator.sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.sword.SwordMod;
import net.mcreator.sword.version.VersionDetector;

public class VersionSyncPacket implements CultivationPacket {
    private final String version;
    private final boolean isLatest;
    
    public VersionSyncPacket() {
        this.version = VersionDetector.getCurrentVersion();
        this.isLatest = VersionDetector.isLatestVersion();
    }
    
    public VersionSyncPacket(FriendlyByteBuf buffer) {
        this.version = buffer.readUtf();
        this.isLatest = buffer.readBoolean();
    }
    
    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(version);
        buffer.writeBoolean(isLatest);
    }
    
    @Override
    public void handle(Context context) {
        System.out.println("[Sword Mod] 收到版本同步包: " + version + " (最新: " + isLatest + ")");
    }
    
    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(SwordMod.MODID, "version_sync");
    }
    
    public String getVersion() {
        return version;
    }
    
    public boolean isLatest() {
        return isLatest;
    }
}