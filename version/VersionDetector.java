package net.mcreator.sword.version;

import net.minecraft.resources.ResourceLocation;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;

import java.util.Optional;

public class VersionDetector {
    private static final String MODID = "sword";
    private static boolean isLatestVersion = true;
    private static String currentVersion = "1.1.2";
    private static String latestVersion = "1.1.2";
    
    public static void initialize() {
        currentVersion = getCurrentVersionFromMod();
        checkForOtherVersions();
    }
    
    private static String getCurrentVersionFromMod() {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(MODID);
        if (container.isPresent()) {
            ModMetadata metadata = container.get().getMetadata();
            return metadata.getVersion().getFriendlyString();
        }
        return "1.1.2";
    }
    
    private static void checkForOtherVersions() {
        String highestVersion = currentVersion;
        
        for (ModContainer container : FabricLoader.getInstance().getAllMods()) {
            ModMetadata metadata = container.getMetadata();
            String modId = metadata.getId();
            
            if (modId.startsWith(MODID + "_") || modId.equals(MODID)) {
                String version = metadata.getVersion().getFriendlyString();
                if (isNewerVersion(version, highestVersion)) {
                    highestVersion = version;
                }
            }
        }
        
        latestVersion = highestVersion;
        isLatestVersion = currentVersion.equals(latestVersion);
    }
    
    private static boolean isNewerVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        
        int length = Math.max(v1.length, v2.length);
        
        for (int i = 0; i < length; i++) {
            int num1 = i < v1.length ? parseVersionPart(v1[i]) : 0;
            int num2 = i < v2.length ? parseVersionPart(v2[i]) : 0;
            
            if (num1 > num2) return true;
            if (num1 < num2) return false;
        }
        
        return false;
    }
    
    private static int parseVersionPart(String part) {
        try {
            return Integer.parseInt(part.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public static boolean isLatestVersion() {
        return isLatestVersion;
    }
    
    public static String getCurrentVersion() {
        return currentVersion;
    }
    
    public static String getLatestVersion() {
        return latestVersion;
    }
    
    public static boolean shouldLoad() {
        return isLatestVersion;
    }
}