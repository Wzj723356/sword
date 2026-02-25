package net.mcreator.sword.cultivation;

public enum CultivationRealm {
    MORTAL("凡人", 0, 0),
    QI_REFINING_1("炼气一层", 1, 100),
    QI_REFINING_2("炼气二层", 2, 200),
    QI_REFINING_3("炼气三层", 3, 300),
    QI_REFINING_4("炼气四层", 4, 400),
    QI_REFINING_5("炼气五层", 5, 500),
    QI_REFINING_6("炼气六层", 6, 600),
    QI_REFINING_7("炼气七层", 7, 700),
    QI_REFINING_8("炼气八层", 8, 800),
    QI_REFINING_9("炼气九层", 9, 900),
    FOUNDATION_ESTABLISHMENT("筑基", 10, 2000),
    GOLDEN_CORE("金丹", 11, 5000),
    NASCENT_SOUL("元婴", 12, 10000),
    SPIRITUAL_SEVERING("化神", 13, 20000),
    VOID_REFINEMENT("炼虚", 14, 50000),
    BODY_INTEGRATION("合体", 15, 100000),
    MAHAYANA("大乘", 16, 200000),
    TRIBULATION("渡劫", 17, 500000);

    private final String displayName;
    private final int level;
    private final int requiredExp;

    CultivationRealm(String displayName, int level, int requiredExp) {
        this.displayName = displayName;
        this.level = level;
        this.requiredExp = requiredExp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getLevel() {
        return level;
    }

    public int getRequiredExp() {
        return requiredExp;
    }

    public CultivationRealm getNextRealm() {
        if (this == TRIBULATION) return TRIBULATION;
        return values()[ordinal() + 1];
    }

    public boolean isMaxRealm() {
        return this == TRIBULATION;
    }

    public static CultivationRealm fromLevel(int level) {
        for (CultivationRealm realm : values()) {
            if (realm.level == level) return realm;
        }
        return MORTAL;
    }
}
