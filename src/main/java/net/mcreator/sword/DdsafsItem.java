package net.mcreator.sword;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class DdsafsItem extends Item {
    public DdsafsItem() {
        super(new Item.Properties()
                .fireResistant()
                .rarity(Rarity.EPIC)
                .stacksTo(64));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.literal("由能量球凝聚而成的棍子"));
        list.add(Component.literal("蕴含着强大的能量"));
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.EPIC;
    }
}