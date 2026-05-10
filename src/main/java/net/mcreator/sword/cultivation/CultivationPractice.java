package net.mcreator.sword.cultivation;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.mcreator.sword.network.CultivationPacketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CultivationPractice {
    private static int practiceTick = 0;
    private static final int PRACTICE_INTERVAL = 20; // 每20tick（1秒）修炼一次
    
    // 基础修炼经验
    private static final int BASE_EXP = 10;
    
    // 修炼速度算法：
    // 总修炼经验 = 基础经验 * 境界加成 * 灵根加成 * 功法加成
    // 基础经验: 10点/次
    // 境界加成: 1 + (境界等级 * 0.1)
    // 灵根加成: 灵根品质决定（根据SpiritualRoot的expMultiplier）
    // 功法加成: 1 + (功法等级 * 0.15)
    
    public static void handleCultivation(Player player) {
        practiceTick++;
        
        // 每20tick（1秒）进行一次修炼
        if (practiceTick >= PRACTICE_INTERVAL) {
            practiceTick = 0;
            performCultivation(player);
        }
    }
    
    private static void performCultivation(Player player) {
        CultivationData data = CultivationManager.getCultivationData(player);
        
        if (!data.hasStartedCultivation()) {
            // 凡人第一次修炼，开始修仙之路
            startCultivation(player, data);
            return;
        }
        
        // 计算修炼经验
        int expGain = calculateCultivationExp(data);
        
        // 添加经验
        data.addExp(expGain);
        
        // 保存并同步数据
        CultivationManager.saveCultivationData(player, data);
        if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
            CultivationManager.syncToClient(serverPlayer);
        }
        
        // 显示修炼提示
        if (player.level().isClientSide()) {
            player.displayClientMessage(
                Component.literal("§a修炼中... +" + expGain + " 修仙经验"),
                true
            );
        }
    }
    
    private static int calculateCultivationExp(CultivationData data) {
        float baseExp = BASE_EXP;
        
        // 境界加成：境界越高，修炼越快
        float realmBonus = 1.0f + (data.getRealm().getLevel() * 0.1f);
        
        // 灵根加成：灵根品质决定修炼效率
        float rootBonus = (float) data.getSpiritualRoot().getExpMultiplier();
        
        // 功法加成：修炼的功法越好，效率越高
        float techniqueBonus = 1.0f + (data.getTechnique().getLevel() * 0.15f);
        
        // 计算总经验
        float totalExp = baseExp * realmBonus * rootBonus * techniqueBonus;
        
        // 添加随机波动（±10%）
        float randomFactor = 0.9f + (float) Math.random() * 0.2f;
        totalExp *= randomFactor;
        
        return Math.max(1, (int) totalExp);
    }
    
    private static void startCultivation(Player player, CultivationData data) {
        // 设置初始灵根（随机分配）
        SpiritualRoot[] roots = SpiritualRoot.values();
        // 去掉NONE
        List<SpiritualRoot> validRoots = new ArrayList<>();
        for (SpiritualRoot root : roots) {
            if (root != SpiritualRoot.NONE) {
                validRoots.add(root);
            }
        }
        
        // 随机选择灵根，高品质灵根概率较低
        SpiritualRoot chosenRoot = rollSpiritualRoot(validRoots);
        
        data.setSpiritualRoot(chosenRoot);
        data.setHasStartedCultivation(true);
        data.setRealm(CultivationRealm.QI_REFINING_1);
        data.setCurrentExp(0);
        
        CultivationManager.saveCultivationData(player, data);
        if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
            CultivationManager.syncToClient(serverPlayer);
        }
        
        if (player.level().isClientSide()) {
            player.displayClientMessage(
                Component.literal("§6恭喜你开启修仙之路！获得" + chosenRoot.getDisplayName() + "！"),
                true
            );
        }
    }
    
    private static SpiritualRoot rollSpiritualRoot(List<SpiritualRoot> validRoots) {
        double rand = Math.random();
        
        // 按灵根品质分配概率
        // 纯灵根(单灵根)：5% - 修炼最快
        // 双灵根：15%
        // 三灵根：30%
        // 四灵根：40%
        // 五灵根：10% - 修炼最慢但潜力大
        
        List<SpiritualRoot> pureRoots = new ArrayList<>();
        List<SpiritualRoot> dualRoots = new ArrayList<>();
        List<SpiritualRoot> tripleRoots = new ArrayList<>();
        List<SpiritualRoot> quadrupleRoots = new ArrayList<>();
        List<SpiritualRoot> fiveElementRoots = new ArrayList<>();
        
        for (SpiritualRoot root : validRoots) {
            switch (root.getQuality()) {
                case PURE:
                    pureRoots.add(root);
                    break;
                case DUAL:
                    dualRoots.add(root);
                    break;
                case TRIPLE:
                    tripleRoots.add(root);
                    break;
                case QUADRUPLE:
                    quadrupleRoots.add(root);
                    break;
                case FIVE_ELEMENTS:
                    fiveElementRoots.add(root);
                    break;
            }
        }
        
        Random random = new Random();
        
        // 天灵根（纯灵根）：5%概率
        if (rand < 0.05 && !pureRoots.isEmpty()) {
            return pureRoots.get(random.nextInt(pureRoots.size()));
        }
        rand -= 0.05;
        
        // 地灵根（双灵根）：15%概率
        if (rand < 0.15 && !dualRoots.isEmpty()) {
            return dualRoots.get(random.nextInt(dualRoots.size()));
        }
        rand -= 0.15;
        
        // 人灵根（三灵根）：30%概率
        if (rand < 0.30 && !tripleRoots.isEmpty()) {
            return tripleRoots.get(random.nextInt(tripleRoots.size()));
        }
        rand -= 0.30;
        
        // 普通灵根（四灵根）：40%概率
        if (rand < 0.40 && !quadrupleRoots.isEmpty()) {
            return quadrupleRoots.get(random.nextInt(quadrupleRoots.size()));
        }
        rand -= 0.40;
        
        // 劣灵根（五灵根）：10%概率
        if (!fiveElementRoots.isEmpty()) {
            return fiveElementRoots.get(random.nextInt(fiveElementRoots.size()));
        }
        
        // 默认返回单灵根
        return pureRoots.isEmpty() ? SpiritualRoot.NONE : pureRoots.get(0);
    }
    
    // 供服务器端调用的修炼方法
    public static void serverTick(Player player) {
        CultivationData data = CultivationManager.getCultivationData(player);
        if (data.hasStartedCultivation()) {
            // 被动恢复灵力（已在SwordMod中实现）
        }
    }
}
