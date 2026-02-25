package net.mcreator.sword.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.mcreator.sword.cultivation.CultivationManager;
import net.mcreator.sword.cultivation.CultivationRealm;
import net.mcreator.sword.network.CultivationClientPacketHandler;
import net.mcreator.sword.network.CultivationDataCache;

public class CultivationUI {
    private static boolean waitingForData = false;
    
    public static void displayCultivationInfo() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        
        if (waitingForData) {
            mc.player.displayClientMessage(Component.literal("正在获取修为数据..."), false);
            return;
        }
        
        waitingForData = true;
        CultivationClientPacketHandler.requestCultivationData();
    }
    
    public static void onCultivationDataReceived() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        
        waitingForData = false;
        
        if (!CultivationDataCache.hasCachedData()) {
            mc.player.displayClientMessage(Component.literal("获取修为数据失败"), false);
            return;
        }
        
        var data = CultivationDataCache.getCachedData();
        CultivationRealm realm = data.getRealm();
        int currentExp = data.getCurrentExp();
        String rootName = data.getSpiritualRoot().getDisplayName();
        float progress = data.getExpProgress();
        
        Component message = Component.literal("=== 修仙信息 ===")
            .append(Component.literal("\n境界: " + realm.getDisplayName()))
            .append(Component.literal("\n灵根: " + rootName))
            .append(Component.literal("\n当前经验: " + currentExp))
            .append(Component.literal("\n升级进度: " + String.format("%.1f%%", progress * 100)));
        
        mc.player.displayClientMessage(message, false);
    }
}
