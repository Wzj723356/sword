package net.mcreator.sword.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.mcreator.sword.SwordMod;
import net.mcreator.sword.inventory.AlchemyFurnaceScreenHandler;
import net.mcreator.sword.inventory.RefiningFurnaceScreenHandler;

public class SwordModMenus {
    public static MenuType<AlchemyFurnaceScreenHandler> ALCHEMY_FURNACE;
    public static MenuType<RefiningFurnaceScreenHandler> REFINING_FURNACE;

    public static void load() {
        ALCHEMY_FURNACE = Registry.register(BuiltInRegistries.MENU, 
                new ResourceLocation(SwordMod.MODID, "alchemy_furnace"),
                new MenuType<>(AlchemyFurnaceScreenHandler::new));
        REFINING_FURNACE = Registry.register(BuiltInRegistries.MENU, 
                new ResourceLocation(SwordMod.MODID, "refining_furnace"),
                new MenuType<>(RefiningFurnaceScreenHandler::new));
    }
}
