/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.sword.init;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

import net.mcreator.sword.SwordMod;

public class SwordModPaintings {
	public static void load() {
		Registry.register(BuiltInRegistries.PAINTING_VARIANT, new ResourceLocation(SwordMod.MODID, "ffdsf"), new PaintingVariant(32, 32));
	}
}