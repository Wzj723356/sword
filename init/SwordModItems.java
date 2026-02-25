/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.sword.init;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;

import net.mcreator.sword.SwordMod;
import net.mcreator.sword.DavoItem;
import net.mcreator.sword.DdsaItem;
import net.mcreator.sword.DavoHelmet;
import net.mcreator.sword.DavoChestplate;
import net.mcreator.sword.DavoLeggings;
import net.mcreator.sword.DavoBoots;
import net.mcreator.sword.DavoShield;
import net.mcreator.sword.items.CultivationStaff;
import net.mcreator.sword.init.SwordModBlocks;
import net.mcreator.sword.items.CultivationSword;
import net.mcreator.sword.items.CultivationBlade;
import net.mcreator.sword.items.CultivationSpear;
import net.mcreator.sword.items.LowGradeSpiritStone;
import net.mcreator.sword.items.MediumGradeSpiritStone;
import net.mcreator.sword.items.HighGradeSpiritStone;
import net.mcreator.sword.items.SupremeSpiritStone;
import net.mcreator.sword.items.HealingPill;
import net.mcreator.sword.items.SpiritGatheringPill;
import net.mcreator.sword.items.FoundationPill;
import net.mcreator.sword.items.SpiritualRootReforgingPill;
import net.mcreator.sword.items.FlyingSword;
import net.mcreator.sword.items.CloudBoatItem;
import net.mcreator.sword.items.TalismanPaper;
import net.mcreator.sword.items.ArrayItem;
import net.mcreator.sword.items.MagicWandItem;

public class SwordModItems {
	public static Item DAVO;
	public static Item DDSA;
	public static Item DAVO_HELMET;
	public static Item DAVO_CHESTPLATE;
	public static Item DAVO_LEGGINGS;
	public static Item DAVO_BOOTS;
	public static Item DAVO_SHIELD;
	public static Item CULTIVATION_STAFF;
	public static Item CULTIVATION_ARRAY;
	public static Item CULTIVATION_SWORD;
	public static Item CULTIVATION_BLADE;
	public static Item CULTIVATION_SPEAR;
	public static Item LOW_GRADE_SPIRIT_STONE;
	public static Item MEDIUM_GRADE_SPIRIT_STONE;
	public static Item HIGH_GRADE_SPIRIT_STONE;
	public static Item SUPREME_SPIRIT_STONE;
	public static Item HEALING_PILL;
	public static Item SPIRIT_GATHERING_PILL;
	public static Item FOUNDATION_PILL;
	public static Item SPIRITUAL_ROOT_REFORGING_PILL;
	public static Item FLYING_SWORD;
	public static Item TRAINING_TARGET;
	public static Item CLOUD_BOAT;
	public static Item CULTIVATION_ARRAY_ITEM;
	public static Item TELEPORT_ARRAY_ITEM;
	public static Item ATTACK_ARRAY_ITEM;
	public static Item HEALING_ARRAY_ITEM;
	public static Item TRANSCENDENT_BREAK_WAND;
	public static Item TRANSCENDENT_DETECT_WAND;
	public static Item TRANSCENDENT_SEAL_WAND;
	public static Item ATTACK_TALISMAN;
	public static Item DEFENSE_TALISMAN;
	public static Item HEALING_TALISMAN;
	public static Item LIGHTNING_TALISMAN;
	public static Item FIRE_TALISMAN;
	public static Item ICE_TALISMAN;
	public static Item WIND_TALISMAN;
	public static Item TELEPORT_TALISMAN;

	public static void load() {
		DAVO = register("davo", new DavoItem());
		DDSA = register("ddsa", new DdsaItem());
		DAVO_HELMET = register("davo_helmet", new DavoHelmet());
		DAVO_CHESTPLATE = register("davo_chestplate", new DavoChestplate());
		DAVO_LEGGINGS = register("davo_leggings", new DavoLeggings());
		DAVO_BOOTS = register("davo_boots", new DavoBoots());
		DAVO_SHIELD = register("davo_shield", new DavoShield());
		CULTIVATION_STAFF = register("cultivation_staff", new CultivationStaff());
		CULTIVATION_ARRAY = register("cultivation_array", new BlockItem(SwordModBlocks.CULTIVATION_ARRAY, new Item.Properties()));
		CULTIVATION_SWORD = register("cultivation_sword", new CultivationSword());
		CULTIVATION_BLADE = register("cultivation_blade", new CultivationBlade());
		CULTIVATION_SPEAR = register("cultivation_spear", new CultivationSpear());
		LOW_GRADE_SPIRIT_STONE = register("low_grade_spirit_stone", new LowGradeSpiritStone());
		MEDIUM_GRADE_SPIRIT_STONE = register("medium_grade_spirit_stone", new MediumGradeSpiritStone());
		HIGH_GRADE_SPIRIT_STONE = register("high_grade_spirit_stone", new HighGradeSpiritStone());
		SUPREME_SPIRIT_STONE = register("supreme_spirit_stone", new SupremeSpiritStone());
		HEALING_PILL = register("healing_pill", new HealingPill());
		SPIRIT_GATHERING_PILL = register("spirit_gathering_pill", new SpiritGatheringPill());
		FOUNDATION_PILL = register("foundation_pill", new FoundationPill());
		SPIRITUAL_ROOT_REFORGING_PILL = register("spiritual_root_reforging_pill", new SpiritualRootReforgingPill());
		FLYING_SWORD = register("flying_sword", new FlyingSword());
		TRAINING_TARGET = register("training_target", new BlockItem(SwordModBlocks.TRAINING_TARGET, new Item.Properties()));
		CLOUD_BOAT = register("cloud_boat", new CloudBoatItem());
		CULTIVATION_ARRAY_ITEM = register("cultivation_array_item", new ArrayItem("cultivation", new Item.Properties()));
		TELEPORT_ARRAY_ITEM = register("teleport_array_item", new ArrayItem("teleport", new Item.Properties()));
		ATTACK_ARRAY_ITEM = register("attack_array_item", new ArrayItem("attack", new Item.Properties()));
		HEALING_ARRAY_ITEM = register("healing_array_item", new ArrayItem("healing", new Item.Properties()));
		TRANSCENDENT_BREAK_WAND = register("transcendent_break_wand", new MagicWandItem("break", new Item.Properties().stacksTo(1).durability(1)));
		TRANSCENDENT_DETECT_WAND = register("transcendent_detect_wand", new MagicWandItem("detect", new Item.Properties().stacksTo(1).durability(1)));
		TRANSCENDENT_SEAL_WAND = register("transcendent_seal_wand", new MagicWandItem("seal", new Item.Properties().stacksTo(1).durability(1)));
		ATTACK_TALISMAN = register("attack_talisman", new TalismanPaper(TalismanPaper.TalismanType.ATTACK));
		DEFENSE_TALISMAN = register("defense_talisman", new TalismanPaper(TalismanPaper.TalismanType.DEFENSE));
		HEALING_TALISMAN = register("healing_talisman", new TalismanPaper(TalismanPaper.TalismanType.HEALING));
		LIGHTNING_TALISMAN = register("lightning_talisman", new TalismanPaper(TalismanPaper.TalismanType.LIGHTNING));
		FIRE_TALISMAN = register("fire_talisman", new TalismanPaper(TalismanPaper.TalismanType.FIRE));
		ICE_TALISMAN = register("ice_talisman", new TalismanPaper(TalismanPaper.TalismanType.ICE));
		WIND_TALISMAN = register("wind_talisman", new TalismanPaper(TalismanPaper.TalismanType.WIND));
		TELEPORT_TALISMAN = register("teleport_talisman", new TalismanPaper(TalismanPaper.TalismanType.TELEPORT));
	}

	public static void clientLoad() {
	}

	private static Item register(String registryName, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SwordMod.MODID, registryName), item);
	}

	private static void registerBlockingProperty(Item item) {
		ItemProperties.register(item, new ResourceLocation("blocking"), (ClampedItemPropertyFunction) ItemProperties.getProperty(Items.SHIELD, new ResourceLocation("blocking")));
		ItemProperties.register(item, new ResourceLocation("blocking"), (ClampedItemPropertyFunction) ItemProperties.getProperty(Items.SHIELD, new ResourceLocation("blocking")));
	}
}