package net.mcreator.sword.cultivation;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class CultivationData {
    private CultivationRealm realm;
    private int currentExp;
    private SpiritualRoot spiritualRoot;
    private boolean hasStartedCultivation;
    private int spiritualPower;
    private int maxSpiritualPower;
    private CultivationTechnique technique;

    public CultivationData() {
        this.realm = CultivationRealm.MORTAL;
        this.currentExp = 0;
        this.spiritualRoot = SpiritualRoot.NONE;
        this.hasStartedCultivation = false;
        this.spiritualPower = 0;
        this.maxSpiritualPower = 100;
        this.technique = CultivationTechnique.NONE;
    }

    public CultivationRealm getRealm() {
        return realm;
    }

    public void setRealm(CultivationRealm realm) {
        this.realm = realm;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }

    public SpiritualRoot getSpiritualRoot() {
        return spiritualRoot;
    }

    public void setSpiritualRoot(SpiritualRoot spiritualRoot) {
        this.spiritualRoot = spiritualRoot;
    }

    public boolean hasStartedCultivation() {
        return hasStartedCultivation;
    }

    public void setHasStartedCultivation(boolean hasStartedCultivation) {
        this.hasStartedCultivation = hasStartedCultivation;
    }

    public int getSpiritualPower() {
        return spiritualPower;
    }

    public void setSpiritualPower(int spiritualPower) {
        this.spiritualPower = Math.min(spiritualPower, maxSpiritualPower);
    }

    public int getMaxSpiritualPower() {
        return maxSpiritualPower;
    }

    public void setMaxSpiritualPower(int maxSpiritualPower) {
        this.maxSpiritualPower = maxSpiritualPower;
        this.spiritualPower = Math.min(spiritualPower, maxSpiritualPower);
    }

    public void addSpiritualPower(int amount) {
        this.spiritualPower = Math.min(spiritualPower + amount, maxSpiritualPower);
    }

    public void consumeSpiritualPower(int amount) {
        this.spiritualPower = Math.max(spiritualPower - amount, 0);
    }

    public float getSpiritualPowerRatio() {
        return maxSpiritualPower > 0 ? (float) spiritualPower / maxSpiritualPower : 0;
    }

    public void addExp(int amount) {
        if (!hasStartedCultivation) return;
        
        double multiplier = spiritualRoot.getExpMultiplier();
        int actualExp = (int) (amount * multiplier);
        this.currentExp += actualExp;
        
        checkLevelUp();
        
        updateMaxSpiritualPower();
    }

    private void updateMaxSpiritualPower() {
        this.maxSpiritualPower = 100 + (realm.getLevel() * 50);
    }

    public boolean checkLevelUp() {
        if (realm.isMaxRealm()) return false;
        
        CultivationRealm nextRealm = realm.getNextRealm();
        if (currentExp >= nextRealm.getRequiredExp()) {
            currentExp -= nextRealm.getRequiredExp();
            realm = nextRealm;
            return true;
        }
        return false;
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("realmLevel", realm.getLevel());
        tag.putInt("currentExp", currentExp);
        tag.putInt("spiritualRoot", spiritualRoot.ordinal());
        tag.putBoolean("hasStartedCultivation", hasStartedCultivation);
        tag.putInt("spiritualPower", spiritualPower);
        tag.putInt("maxSpiritualPower", maxSpiritualPower);
        tag.putInt("technique", technique.ordinal());
        return tag;
    }

    public void fromNBT(CompoundTag tag) {
        this.realm = CultivationRealm.fromLevel(tag.getInt("realmLevel"));
        this.currentExp = tag.getInt("currentExp");
        this.spiritualRoot = SpiritualRoot.values()[tag.getInt("spiritualRoot")];
        this.hasStartedCultivation = tag.getBoolean("hasStartedCultivation");
        this.spiritualPower = tag.getInt("spiritualPower");
        this.maxSpiritualPower = tag.getInt("maxSpiritualPower");
        int techniqueIndex = tag.getInt("technique");
        this.technique = techniqueIndex < CultivationTechnique.values().length ? 
            CultivationTechnique.values()[techniqueIndex] : CultivationTechnique.NONE;
    }

    public float getExpProgress() {
        if (realm.isMaxRealm()) return 1.0f;
        CultivationRealm nextRealm = realm.getNextRealm();
        return (float) currentExp / nextRealm.getRequiredExp();
    }

    public CultivationTechnique getTechnique() {
        return technique;
    }

    public void setTechnique(CultivationTechnique technique) {
        this.technique = technique;
    }

    public boolean canLearnTechnique(CultivationTechnique newTechnique) {
        if (newTechnique == CultivationTechnique.NONE) return true;
        return realm.getLevel() >= newTechnique.getLevel() - 1;
    }
}
