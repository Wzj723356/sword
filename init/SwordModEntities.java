/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.sword.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;

import net.mcreator.sword.SwordMod;
import net.mcreator.sword.entity.CloudBoatEntity;
import net.mcreator.sword.entity.ArrayEntity;

public class SwordModEntities {
    public static EntityType<CloudBoatEntity> CLOUD_BOAT;
    public static EntityType<ArrayEntity> ARRAY;

    public static void load() {
        CLOUD_BOAT = register("cloud_boat", 
            EntityType.Builder.<CloudBoatEntity>of(CloudBoatEntity::new, MobCategory.MISC)
                .sized(1.375F, 0.5625F)
                .clientTrackingRange(10)
                .build("cloud_boat"));
        ARRAY = register("array", 
            EntityType.Builder.<ArrayEntity>of(ArrayEntity::new, MobCategory.MISC)
                .sized(0.0F, 0.0F)
                .clientTrackingRange(64)
                .fireImmune()
                .build("array"));
    }

    private static <T extends Entity> EntityType<T> register(String registryName, EntityType<T> entityType) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(SwordMod.MODID, registryName), entityType);
    }
}