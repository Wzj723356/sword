package net.mcreator.sword.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class HighGradeSpiritStone extends Item {
    public HighGradeSpiritStone() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.RARE));
    }
}
