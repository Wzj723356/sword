package net.mcreator.sword.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.mcreator.sword.SwordMod;
import net.mcreator.sword.blockentity.AlchemyFurnaceBlockEntity;
import net.mcreator.sword.blockentity.RefiningFurnaceBlockEntity;
import net.mcreator.sword.blockentity.TalismanPaperBlockEntity;

public class SwordModBlockEntities {
    public static BlockEntityType<AlchemyFurnaceBlockEntity> ALCHEMY_FURNACE;
    public static BlockEntityType<RefiningFurnaceBlockEntity> REFINING_FURNACE;
    public static BlockEntityType<TalismanPaperBlockEntity> TALISMAN_PAPER;

    public static void load() {
        ALCHEMY_FURNACE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, 
                new ResourceLocation(SwordMod.MODID, "alchemy_furnace"),
                BlockEntityType.Builder.of(AlchemyFurnaceBlockEntity::new, SwordModBlocks.ALCHEMY_FURNACE).build(null));
        REFINING_FURNACE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, 
                new ResourceLocation(SwordMod.MODID, "refining_furnace"),
                BlockEntityType.Builder.of(RefiningFurnaceBlockEntity::new, SwordModBlocks.REFINING_FURNACE).build(null));
        TALISMAN_PAPER = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, 
                new ResourceLocation(SwordMod.MODID, "talisman_paper"),
                BlockEntityType.Builder.of(TalismanPaperBlockEntity::new, SwordModBlocks.TALISMAN_PAPER).build(null));
    }
}
