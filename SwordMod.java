/*
 *	MCreator note:
 *
 *	If you lock base mod element files, you can edit this file and the proxy files
 *	and they won't get overwritten. If you change your mod package or modid, you
 *	need to apply these changes to this file MANUALLY.
 *
 *
 *	If you do not lock base mod element files in Workspace settings, this file
 *	will be REGENERATED on each build.
 *
 */
package net.mcreator.sword;

import net.mcreator.sword.init.SwordModTabs;
import net.mcreator.sword.init.SwordModPaintings;
import net.mcreator.sword.init.SwordModItems;
import net.mcreator.sword.init.SwordModBlocks;
import net.mcreator.sword.init.SwordModEntities;
import net.mcreator.sword.network.CultivationPacketHandler;
import net.mcreator.sword.version.VersionDetector;
import net.mcreator.sword.cultivation.CultivationManager;
import net.mcreator.sword.cultivation.CultivationData;
import net.mcreator.sword.cultivation.SkillManager;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;

public class SwordMod implements ModInitializer {
	public static final String MODID = "sword";

	@Override
	public void onInitialize() {
		VersionDetector.initialize();
		
		if (!VersionDetector.shouldLoad()) {
			System.out.println("[Sword Mod] 检测到更高版本的模组已加载，跳过低版本初始化。");
			System.out.println("[Sword Mod] 当前版本: " + VersionDetector.getCurrentVersion());
			System.out.println("[Sword Mod] 最新版本: " + VersionDetector.getLatestVersion());
			return;
		}
		
		System.out.println("[Sword Mod] 初始化模组，版本: " + VersionDetector.getCurrentVersion());

		SwordModEntities.load();
		SwordModBlocks.load();
		SwordModItems.load();

		SwordModTabs.load();

		SwordModPaintings.load();

		CultivationPacketHandler.register();

		// 注册服务器tick事件监听器，处理灵力恢复和技能冷却
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayer player : server.getPlayerList().getPlayers()) {
				CultivationData cultivationData = CultivationManager.getCultivationData(player);
				if (cultivationData.hasStartedCultivation()) {
					// 每40tick（2秒）恢复一次灵力
					if (server.getTickCount() % 40 == 0) {
						// 基础恢复量 + 境界加成
						int recoveryAmount = 5 + (cultivationData.getRealm().getLevel() * 2);
						cultivationData.addSpiritualPower(recoveryAmount);
						CultivationManager.saveCultivationData(player, cultivationData);
						CultivationManager.syncToClient(player);
					}
				}
				
				// 更新技能冷却
				SkillManager.updateCooldowns(player);
			}
		});

	}
}