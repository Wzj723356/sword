package net.mcreator.sword.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.lwjgl.glfw.GLFW;
import net.mcreator.sword.cultivation.CultivationTechnique;
import net.mcreator.sword.cultivation.CultivationManager;
import net.mcreator.sword.cultivation.CultivationData;
import net.mcreator.sword.cultivation.SkillManager;
import net.mcreator.sword.network.CultivationDataCache;
import java.util.HashMap;
import java.util.Map;

public class SkillKeyBindings {
    private static final Map<CultivationTechnique, KeyMapping> skillKeys = new HashMap<>();
    private static final CultivationTechnique[] activeSkills = {
        CultivationTechnique.FIRE_BALL,
        CultivationTechnique.ICE_SHARD,
        CultivationTechnique.LIGHTNING_BOLT,
        CultivationTechnique.WIND_BLADE,
        CultivationTechnique.EARTH_SPIKE,
        CultivationTechnique.HEALING_LIGHT,
        CultivationTechnique.SHIELD_BARRIER,
        CultivationTechnique.TELEPORT,
        CultivationTechnique.FLYING_SWORD,
        CultivationTechnique.ELEMENTAL_BURST,
        CultivationTechnique.SPIRITUAL_AURA,
        CultivationTechnique.BODY_FORTIFICATION,
        CultivationTechnique.SOUL_RESONANCE,
        CultivationTechnique.TIME_DILATION
    };
    
    private static final int[] defaultKeys = {
        GLFW.GLFW_KEY_UNKNOWN, // 火球术 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN, // 冰锥术 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN, // 雷电术 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN, // 风刃术 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN, // 地刺术 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN, // 治疗之光 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN, // 护体盾 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN, // 瞬移术 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN, // 御剑术 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN, // 元素爆发 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN, // 灵力光环 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN, // 炼体术 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN, // 灵魂共鸣 - 未绑定
        GLFW.GLFW_KEY_UNKNOWN  // 时间减缓 - 未绑定
    };
    
    public static void register() {
        for (int i = 0; i < activeSkills.length; i++) {
            CultivationTechnique skill = activeSkills[i];
            KeyMapping keyMapping = new KeyMapping(
                "key.sword.skill." + skill.name().toLowerCase(),
                defaultKeys[i],
                "key.categories.sword.skills"
            );
            KeyBindingHelper.registerKeyBinding(keyMapping);
            skillKeys.put(skill, keyMapping);
        }
    }
    
    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        
        CultivationData data = CultivationDataCache.getCachedData();
        if (data == null || !data.hasStartedCultivation()) return;
        
        for (Map.Entry<CultivationTechnique, KeyMapping> entry : skillKeys.entrySet()) {
            CultivationTechnique skill = entry.getKey();
            KeyMapping keyMapping = entry.getValue();
            
            if (keyMapping.consumeClick()) {
                if (data.hasLearnedSkill(skill)) {
                    if (SkillManager.canUseSkill(mc.player, skill)) {
                        SkillManager.useSkill(mc.player, skill);
                        mc.player.displayClientMessage(
                            Component.literal("释放技能: " + skill.getDisplayName()), 
                            true
                        );
                    }
                } else {
                    mc.player.displayClientMessage(
                        Component.literal("尚未学习技能: " + skill.getDisplayName()), 
                        true
                    );
                }
            }
        }
    }
    
    public static KeyMapping getKeyMapping(CultivationTechnique skill) {
        return skillKeys.get(skill);
    }
    
    public static CultivationTechnique[] getActiveSkills() {
        return activeSkills;
    }
}
