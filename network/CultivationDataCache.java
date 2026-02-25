package net.mcreator.sword.network;

import net.mcreator.sword.cultivation.CultivationData;

public class CultivationDataCache {
    private static CultivationData cachedData = null;
    
    public static void setCachedData(CultivationData data) {
        cachedData = data;
    }
    
    public static CultivationData getCachedData() {
        return cachedData;
    }
    
    public static boolean hasCachedData() {
        return cachedData != null;
    }
    
    public static void clearCache() {
        cachedData = null;
    }
}