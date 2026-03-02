package net.mcreator.sword.cultivation;

public enum CultivationTechnique {
    NONE("无", 0, 0, 0, "无任何功法"),
    
    SWORD_BASIC("基础剑法", 1, 0, 0, "基础剑法，提升剑类武器伤害25%"),
    SWORD_INTERMEDIATE("中级剑法", 2, 0, 0, "中级剑法，提升剑类武器伤害50%"),
    SWORD_ADVANCED("高级剑法", 3, 0, 0, "高级剑法，提升剑类武器伤害75%"),
    SWORD_MASTER("剑圣", 4, 0, 0, "剑圣境界，提升剑类武器伤害100%"),
    
    BLADE_BASIC("基础刀法", 1, 0, 0, "基础刀法，提升刀类武器伤害25%"),
    BLADE_INTERMEDIATE("中级刀法", 2, 0, 0, "中级刀法，提升刀类武器伤害50%"),
    BLADE_ADVANCED("高级刀法", 3, 0, 0, "高级刀法，提升刀类武器伤害75%"),
    BLADE_MASTER("刀神", 4, 0, 0, "刀神境界，提升刀类武器伤害100%"),
    
    SPEAR_BASIC("基础枪法", 1, 0, 0, "基础枪法，提升枪类武器伤害25%"),
    SPEAR_INTERMEDIATE("中级枪法", 2, 0, 0, "中级枪法，提升枪类武器伤害50%"),
    SPEAR_ADVANCED("高级枪法", 3, 0, 0, "高级枪法，提升枪类武器伤害75%"),
    SPEAR_MASTER("枪神", 4, 0, 0, "枪神境界，提升枪类武器伤害100%"),
    
    FIRE_BALL("火球术", 1, 15, 60, "消耗15点灵力，发射火球造成额外伤害"),
    ICE_SHARD("冰锥术", 1, 12, 50, "消耗12点灵力，发射冰锥造成额外伤害并减速"),
    LIGHTNING_BOLT("雷电术", 2, 20, 70, "消耗20点灵力，召唤雷电造成大范围伤害"),
    WIND_BLADE("风刃术", 1, 10, 40, "消耗10点灵力，发射风刃穿透敌人"),
    EARTH_SPIKE("地刺术", 2, 18, 65, "消耗18点灵力，从地面召唤地刺攻击敌人"),
    HEALING_LIGHT("治疗之光", 1, 25, 80, "消耗25点灵力，恢复自身生命值"),
    SHIELD_BARRIER("护体盾", 2, 30, 100, "消耗30点灵力，形成护盾抵挡伤害"),
    TELEPORT("瞬移术", 3, 35, 120, "消耗35点灵力，瞬间移动一段距离"),
    FLYING_SWORD("御剑术", 3, 40, 150, "消耗40点灵力，御剑飞行"),
    ELEMENTAL_BURST("元素爆发", 4, 50, 200, "消耗50点灵力，释放强大的元素攻击"),
    SPIRITUAL_AURA("灵力光环", 2, 5, 30, "消耗5点灵力，提升周围队友的攻击力"),
    BODY_FORTIFICATION("炼体术", 1, 8, 45, "消耗8点灵力，临时提升防御力"),
    SOUL_RESONANCE("灵魂共鸣", 3, 45, 180, "消耗45点灵力，与武器共鸣大幅提升伤害"),
    TIME_DILATION("时间减缓", 4, 60, 250, "消耗60点灵力，减缓周围敌人的行动速度");

    private final String displayName;
    private final int level;
    private final int spiritualPowerCost;
    private final int cooldownTicks;
    private final String description;

    CultivationTechnique(String displayName, int level, int spiritualPowerCost, int cooldownTicks, String description) {
        this.displayName = displayName;
        this.level = level;
        this.spiritualPowerCost = spiritualPowerCost;
        this.cooldownTicks = cooldownTicks;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getLevel() {
        return level;
    }

    public int getSpiritualPowerCost() {
        return spiritualPowerCost;
    }

    public int getCooldownTicks() {
        return cooldownTicks;
    }

    public String getDescription() {
        return description;
    }

    public double getDamageMultiplier() {
        return 1.0 + (level * 0.25);
    }

    public boolean isActiveSkill() {
        return spiritualPowerCost > 0;
    }

    public boolean isPassiveSkill() {
        return spiritualPowerCost == 0 && this != NONE;
    }
}
