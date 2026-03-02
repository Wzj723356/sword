package net.mcreator.sword.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class LowGradeSpiritStone extends Item {
    public LowGradeSpiritStone() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
}
