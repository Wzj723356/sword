package net.mcreator.sword.cultivation;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

public class CultivationAttackHandler {
    
    // 基础灵力消耗
    private static final int BASE_SPIRITUAL_COST = 10;
    
    // 灵力攻击加持倍率计算：
    // 总倍率 = 基础倍率 + 境界加成 + 灵力比例加成
    // 基础倍率: 1.0（无加持）
    // 境界加成: 境界等级 * 0.1（例如筑基期+1.0，元婴期+1.2）
    // 灵力比例加成: (当前灵力/最大灵力) * 0.5（灵力越高，加持越强）
    
    public static void register() {
        // 使用ServerEntityEvents来监听实体事件
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            // 可以在这里添加其他实体相关的监听
        });
    }
    
    /**
     * 处理玩家攻击
     * 这个方法需要在物品的攻击方法中调用
     */
    public static void handlePlayerAttack(Player player, LivingEntity target) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }
        
        CultivationData data = CultivationManager.getCultivationData(serverPlayer);
        
        // 只有已开始修炼的玩家才有灵力加持
        if (!data.hasStartedCultivation()) {
            return;
        }
        
        // 检查灵力是否足够
        if (data.getSpiritualPower() <= 0) {
            // 灵力耗尽，不提供加持
            serverPlayer.displayClientMessage(
                Component.literal("§c灵力耗尽，无法进行灵力加持！"),
                true
            );
            return;
        }
        
        // 计算灵力加持
        float damageMultiplier = calculateSpiritualBonus(data);
        
        if (damageMultiplier > 1.0f) {
            // 消耗灵力
            int cost = calculateSpiritualCost(data);
            data.consumeSpiritualPower(cost);
            
            // 造成额外伤害
            float bonusDamage = target.getMaxHealth() * (damageMultiplier - 1.0f) * 0.1f;
            target.hurt(
                serverPlayer.level().damageSources().playerAttack(serverPlayer),
                Math.max(1.0f, bonusDamage)
            );
            
            // 保存数据
            CultivationManager.saveCultivationData(serverPlayer, data);
            CultivationManager.syncToClient(serverPlayer);
            
            // 显示灵力加持提示
            serverPlayer.displayClientMessage(
                Component.literal("§b灵力加持！伤害提升 " + String.format("%.1f", (damageMultiplier - 1.0f) * 100) + "%"),
                true
            );
        }
    }
    
    private static float calculateSpiritualBonus(CultivationData data) {
        float baseMultiplier = 1.0f;
        
        // 境界加成：境界越高，加持越强
        float realmBonus = data.getRealm().getLevel() * 0.1f;
        
        // 灵力比例加成：灵力越充足，加持越强
        float spiritualRatio = data.getSpiritualPowerRatio();
        float spiritualBonus = spiritualRatio * 0.5f;
        
        // 灵根加成：灵根越好，灵力运用越精妙
        float rootBonus = ((float)data.getSpiritualRoot().getExpMultiplier() - 1.0f) * 0.3f;
        
        // 功法加成：修炼的功法对灵力运用有加成
        float techniqueBonus = data.getTechnique().getLevel() * 0.05f;
        
        return baseMultiplier + realmBonus + spiritualBonus + rootBonus + techniqueBonus;
    }
    
    private static int calculateSpiritualCost(CultivationData data) {
        // 基础消耗 + 境界加成（境界越高，消耗越多但加成也越高）
        int baseCost = BASE_SPIRITUAL_COST;
        int realmCost = data.getRealm().getLevel() * 2;
        
        // 攻击强度加成消耗（如果使用了强力攻击，消耗更多）
        float bonusMultiplier = calculateSpiritualBonus(data);
        int bonusCost = (int) ((bonusMultiplier - 1.0f) * 5);
        
        return baseCost + realmCost + bonusCost;
    }
    
    /**
     * 获取当前灵力加持倍率（用于显示）
     */
    public static float getCurrentSpiritualMultiplier(CultivationData data) {
        if (!data.hasStartedCultivation() || data.getSpiritualPower() <= 0) {
            return 1.0f;
        }
        return calculateSpiritualBonus(data);
    }
    
    /**
     * 检查是否可以进行灵力加持
     */
    public static boolean canUseSpiritualBoost(CultivationData data) {
        return data.hasStartedCultivation() && data.getSpiritualPower() > 0;
    }
}
