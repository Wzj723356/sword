package net.mcreator.sword;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class EnergyOrbItem extends Item {
    public EnergyOrbItem() {
        super(new Item.Properties()
                .fireResistant()
                .rarity(Rarity.EPIC)
                .stacksTo(64));
    }

    public EnergyOrbItem(Item.Properties properties) {
        super(properties
                .fireResistant()
                .rarity(Rarity.EPIC)
                .stacksTo(64));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.literal("蕴含强大能量的球体"));
        list.add(Component.literal("用于合成范围攻击神剑"));
    }

    public Rarity getRarity(ItemStack itemstack) {
        return Rarity.EPIC;
    }
}