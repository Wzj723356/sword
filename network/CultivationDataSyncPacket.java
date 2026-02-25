package net.mcreator.sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.sword.SwordMod;
import net.mcreator.sword.cultivation.CultivationData;
import net.mcreator.sword.cultivation.CultivationRealm;
import net.mcreator.sword.cultivation.SpiritualRoot;

public class CultivationDataSyncPacket implements CultivationPacket {
    private final String realmName;
    private final int currentExp;
    private final String rootName;
    private final boolean hasStartedCultivation;
    private final int spiritualPower;
    private final int maxSpiritualPower;
    
    public CultivationDataSyncPacket(CultivationData data) {
        this.realmName = data.getRealm().name();
        this.currentExp = data.getCurrentExp();
        this.rootName = data.getSpiritualRoot().name();
        this.hasStartedCultivation = data.hasStartedCultivation();
        this.spiritualPower = data.getSpiritualPower();
        this.maxSpiritualPower = data.getMaxSpiritualPower();
    }
    
    public CultivationDataSyncPacket(FriendlyByteBuf buffer) {
        this.realmName = buffer.readUtf();
        this.currentExp = buffer.readInt();
        this.rootName = buffer.readUtf();
        this.hasStartedCultivation = buffer.readBoolean();
        this.spiritualPower = buffer.readInt();
        this.maxSpiritualPower = buffer.readInt();
    }
    
    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(realmName);
        buffer.writeInt(currentExp);
        buffer.writeUtf(rootName);
        buffer.writeBoolean(hasStartedCultivation);
        buffer.writeInt(spiritualPower);
        buffer.writeInt(maxSpiritualPower);
    }
    
    @Override
    public void handle(Context context) {
        CultivationData data = new CultivationData();
        data.setRealm(CultivationRealm.valueOf(realmName));
        data.setCurrentExp(currentExp);
        data.setSpiritualRoot(SpiritualRoot.valueOf(rootName));
        data.setHasStartedCultivation(hasStartedCultivation);
        data.setSpiritualPower(spiritualPower);
        data.setMaxSpiritualPower(maxSpiritualPower);
        
        CultivationDataCache.setCachedData(data);
    }
    
    @Override
    public ResourceLocation getId() {
        return new ResourceLocation(SwordMod.MODID, "cultivation_data_sync");
    }
    
    public CultivationData getData() {
        CultivationData data = new CultivationData();
        data.setRealm(CultivationRealm.valueOf(realmName));
        data.setCurrentExp(currentExp);
        data.setSpiritualRoot(SpiritualRoot.valueOf(rootName));
        data.setHasStartedCultivation(hasStartedCultivation);
        data.setSpiritualPower(spiritualPower);
        data.setMaxSpiritualPower(maxSpiritualPower);
        return data;
    }
}