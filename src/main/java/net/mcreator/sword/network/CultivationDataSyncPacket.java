package net.mcreator.sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.mcreator.sword.SwordMod;
import net.mcreator.sword.cultivation.CultivationData;
import net.mcreator.sword.cultivation.CultivationRealm;
import net.mcreator.sword.cultivation.SpiritualRoot;
import java.util.Set;
import java.util.HashSet;

public class CultivationDataSyncPacket implements CultivationPacket {
    private final String realmName;
    private final int currentExp;
    private final String rootName;
    private final boolean hasStartedCultivation;
    private final int spiritualPower;
    private final int maxSpiritualPower;
    private final Set<String> learnedSkills;
    
    public CultivationDataSyncPacket(CultivationData data) {
        this.realmName = data.getRealm().name();
        this.currentExp = data.getCurrentExp();
        this.rootName = data.getSpiritualRoot().name();
        this.hasStartedCultivation = data.hasStartedCultivation();
        this.spiritualPower = data.getSpiritualPower();
        this.maxSpiritualPower = data.getMaxSpiritualPower();
        this.learnedSkills = data.getLearnedSkills();
    }
    
    public CultivationDataSyncPacket(FriendlyByteBuf buffer) {
        this.realmName = buffer.readUtf();
        this.currentExp = buffer.readInt();
        this.rootName = buffer.readUtf();
        this.hasStartedCultivation = buffer.readBoolean();
        this.spiritualPower = buffer.readInt();
        this.maxSpiritualPower = buffer.readInt();
        int skillCount = buffer.readInt();
        this.learnedSkills = new HashSet<>();
        for (int i = 0; i < skillCount; i++) {
            this.learnedSkills.add(buffer.readUtf());
        }
    }
    
    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(realmName);
        buffer.writeInt(currentExp);
        buffer.writeUtf(rootName);
        buffer.writeBoolean(hasStartedCultivation);
        buffer.writeInt(spiritualPower);
        buffer.writeInt(maxSpiritualPower);
        buffer.writeInt(learnedSkills.size());
        for (String skill : learnedSkills) {
            buffer.writeUtf(skill);
        }
    }
    
    @Override
    public void handle(Context context) {
        CultivationData data = new CultivationData();
        data.setRealm(CultivationRealm.valueOf(realmName));
        data.setCurrentExp(currentExp);
        data.setSpiritualRoot(SpiritualRoot.valueOf(rootName));
        data.setHasStartedCultivation(hasStartedCultivation);
        data.setMaxSpiritualPower(maxSpiritualPower);
        data.setSpiritualPower(spiritualPower);
        for (String skill : learnedSkills) {
            data.getLearnedSkills().add(skill);
        }
        
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
        data.setMaxSpiritualPower(maxSpiritualPower);
        data.setSpiritualPower(spiritualPower);
        for (String skill : learnedSkills) {
            data.getLearnedSkills().add(skill);
        }
        return data;
    }
}