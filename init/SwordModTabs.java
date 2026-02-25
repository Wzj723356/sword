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
						})
						.build());
	}
}