package net.mcreator.sword.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.mcreator.sword.cultivation.CultivationData;
import net.mcreator.sword.cultivation.CultivationRealm;
import net.mcreator.sword.network.CultivationDataCache;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class CultivationHUD implements HudRenderCallback {
    private static boolean showHUD = true;
    
    public static void register() {
        HudRenderCallback.EVENT.register(new CultivationHUD());
    }
    
    public static void toggleHUD() {
        showHUD = !showHUD;
    }
    
    public static boolean isHUDVisible() {
        return showHUD;
    }
    
    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        if (!showHUD) return;
        
        Minecraft mc = Minecraft.getInstance();
        
        if (mc.player == null || !CultivationDataCache.hasCachedData()) {
            return;
        }
        
        CultivationData data = CultivationDataCache.getCachedData();
        CultivationRealm realm = data.getRealm();
        int currentExp = data.getCurrentExp();
        int spiritualPower = data.getSpiritualPower();
        int maxSpiritualPower = data.getMaxSpiritualPower();
        String rootName = data.getSpiritualRoot().getDisplayName();
        
        int x = mc.getWindow().getGuiScaledWidth() - 160;
        int y = 10;
        
        graphics.drawString(mc.font, Component.literal("境界: " + realm.getDisplayName()), x, y, 0xFFFFFF, true);
        y += 12;
        
        graphics.drawString(mc.font, Component.literal("灵根: " + rootName), x, y, 0xFFFFFF, true);
        y += 12;
        
        graphics.drawString(mc.font, Component.literal("经验: " + currentExp), x, y, 0xFFFFFF, true);
        y += 12;
        
        graphics.drawString(mc.font, Component.literal("灵力: " + spiritualPower + "/" + maxSpiritualPower), x, y, 0xFFFFFF, true);
        y += 12;
        
        int barWidth = 140;
        int barHeight = 6;
        float powerRatio = data.getSpiritualPowerRatio();
        
        graphics.fill(x, y, x + barWidth, y + barHeight, 0x80000000);
        graphics.fill(x, y, x + (int)(barWidth * powerRatio), y + barHeight, 0x80FFFFFF);
    }
}