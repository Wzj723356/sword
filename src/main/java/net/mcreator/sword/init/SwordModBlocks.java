/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.sword.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

import net.mcreator.sword.SwordMod;
import net.mcreator.sword.blocks.CultivationArrayBlock;
import net.mcreator.sword.blocks.TrainingTargetBlock;
import net.mcreator.sword.blocks.TeleportArrayBlock;
import net.mcreator.sword.blocks.AttackArrayBlock;
import net.mcreator.sword.blocks.HealingArrayBlock;
import net.mcreator.sword.blocks.AlchemyFurnaceBlock;
import net.mcreator.sword.blocks.RefiningFurnaceBlock;
import net.mcreator.sword.blocks.TalismanPaperBlock;
import net.mcreator.sword.blocks.SpiritTestingStoneBlock;

public class SwordModBlocks {
	public static Block CULTIVATION_ARRAY;
	public static Block TRAINING_TARGET;
	public static Block TELEPORT_ARRAY;
	public static Block ATTACK_ARRAY;
	public static Block HEALING_ARRAY;
	public static Block ALCHEMY_FURNACE;
	public static Block REFINING_FURNACE;
	public static Block TALISMAN_PAPER;
	public static Block SPIRIT_TESTING_STONE;

	public static void load() {
		CULTIVATION_ARRAY = register("cultivation_array", new CultivationArrayBlock());
		TRAINING_TARGET = register("training_target", new TrainingTargetBlock());
		TELEPORT_ARRAY = register("teleport_array", new TeleportArrayBlock());
		ATTACK_ARRAY = register("attack_array", new AttackArrayBlock());
		HEALING_ARRAY = register("healing_array", new HealingArrayBlock());
		ALCHEMY_FURNACE = register("alchemy_furnace", new AlchemyFurnaceBlock());
		REFINING_FURNACE = register("refining_furnace", new RefiningFurnaceBlock());
		TALISMAN_PAPER = register("talisman_paper", new TalismanPaperBlock());
		SPIRIT_TESTING_STONE = register("spirit_testing_stone", new SpiritTestingStoneBlock());
	}

	private static Block register(String registryName, Block block) {
		return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(SwordMod.MODID, registryName), block);
	}
}
