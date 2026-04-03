/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.sword.init;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

import net.mcreator.sword.SwordMod;
import net.mcreator.sword.init.SwordModItems;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

public class SwordModTabs {
	public static CreativeModeTab TAB_SHEN;

	public static void load() {
		TAB_SHEN = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(SwordMod.MODID, "shen"), 
				FabricItemGroup.builder()
						.title(Component.translatable("item_group." + SwordMod.MODID + ".shen"))
						.icon(() -> new ItemStack(SwordModItems.DAVO))
						.displayItems((parameters, entries) -> {
							entries.accept(SwordModItems.DAVO);
							entries.accept(SwordModItems.DDSA);
							entries.accept(SwordModItems.DAVO_HELMET);
							entries.accept(SwordModItems.DAVO_CHESTPLATE);
							entries.accept(SwordModItems.DAVO_LEGGINGS);
							entries.accept(SwordModItems.DAVO_BOOTS);
							entries.accept(SwordModItems.DAVO_SHIELD);
							entries.accept(SwordModItems.CULTIVATION_STAFF);
							entries.accept(SwordModItems.CULTIVATION_ARRAY_ITEM);
							entries.accept(SwordModItems.TELEPORT_ARRAY_ITEM);
							entries.accept(SwordModItems.ATTACK_ARRAY_ITEM);
							entries.accept(SwordModItems.HEALING_ARRAY_ITEM);
							entries.accept(SwordModItems.TRANSCENDENT_BREAK_WAND);
							entries.accept(SwordModItems.TRANSCENDENT_DETECT_WAND);
							entries.accept(SwordModItems.TRANSCENDENT_SEAL_WAND);
							entries.accept(SwordModItems.CULTIVATION_SWORD);
							entries.accept(SwordModItems.CULTIVATION_BLADE);
							entries.accept(SwordModItems.CULTIVATION_SPEAR);
							entries.accept(SwordModItems.LOW_GRADE_SPIRIT_STONE);
							entries.accept(SwordModItems.MEDIUM_GRADE_SPIRIT_STONE);
							entries.accept(SwordModItems.HIGH_GRADE_SPIRIT_STONE);
							entries.accept(SwordModItems.SUPREME_SPIRIT_STONE);
							entries.accept(SwordModItems.HEALING_PILL);
							entries.accept(SwordModItems.SPIRIT_GATHERING_PILL);
							entries.accept(SwordModItems.FOUNDATION_PILL);
							entries.accept(SwordModItems.SPIRITUAL_ROOT_REFORGING_PILL);
							entries.accept(SwordModItems.FLYING_SWORD);
							entries.accept(SwordModItems.TRAINING_TARGET);
							entries.accept(SwordModItems.CLOUD_BOAT);
							entries.accept(SwordModItems.ATTACK_TALISMAN);
							entries.accept(SwordModItems.DEFENSE_TALISMAN);
							entries.accept(SwordModItems.HEALING_TALISMAN);
							entries.accept(SwordModItems.LIGHTNING_TALISMAN);
							entries.accept(SwordModItems.FIRE_TALISMAN);
							entries.accept(SwordModItems.ICE_TALISMAN);
							entries.accept(SwordModItems.WIND_TALISMAN);
							entries.accept(SwordModItems.TELEPORT_TALISMAN);
							
							// 技能书（主动技能）
							entries.accept(SwordModItems.FIRE_BALL_BOOK);
							entries.accept(SwordModItems.ICE_SHARD_BOOK);
							entries.accept(SwordModItems.LIGHTNING_BOLT_BOOK);
							entries.accept(SwordModItems.WIND_BLADE_BOOK);
							entries.accept(SwordModItems.EARTH_SPIKE_BOOK);
							entries.accept(SwordModItems.HEALING_LIGHT_BOOK);
							entries.accept(SwordModItems.SHIELD_BARRIER_BOOK);
							entries.accept(SwordModItems.TELEPORT_BOOK);
							entries.accept(SwordModItems.FLYING_SWORD_BOOK);
							entries.accept(SwordModItems.ELEMENTAL_BURST_BOOK);
							entries.accept(SwordModItems.SPIRITUAL_AURA_BOOK);
							entries.accept(SwordModItems.BODY_FORTIFICATION_BOOK);
							entries.accept(SwordModItems.SOUL_RESONANCE_BOOK);
							entries.accept(SwordModItems.TIME_DILATION_BOOK);
							
							// 功法书（被动功法）
							entries.accept(SwordModItems.SWORD_BASIC_BOOK);
							entries.accept(SwordModItems.SWORD_INTERMEDIATE_BOOK);
							entries.accept(SwordModItems.SWORD_ADVANCED_BOOK);
							entries.accept(SwordModItems.SWORD_MASTER_BOOK);
							entries.accept(SwordModItems.BLADE_BASIC_BOOK);
							entries.accept(SwordModItems.BLADE_INTERMEDIATE_BOOK);
							entries.accept(SwordModItems.BLADE_ADVANCED_BOOK);
							entries.accept(SwordModItems.BLADE_MASTER_BOOK);
							entries.accept(SwordModItems.SPEAR_BASIC_BOOK);
							entries.accept(SwordModItems.SPEAR_INTERMEDIATE_BOOK);
							entries.accept(SwordModItems.SPEAR_ADVANCED_BOOK);
							entries.accept(SwordModItems.SPEAR_MASTER_BOOK);
							
							// 特殊物品
							entries.accept(SwordModItems.傻龙X1);
						})
						.build());
	}
}