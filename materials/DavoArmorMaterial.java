package net.mcreator.sword.materials;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;

public class DavoArmorMaterial implements ArmorMaterial {
    public static final DavoArmorMaterial INSTANCE = new DavoArmorMaterial();

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getEnchantmentValue() {
        return Integer.MAX_VALUE;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.STICK);
    }

    @Override
    public String getName() {
        return "davo";
    }

    @Override
    public float getToughness() {
        return 10.0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 1.0F;
    }
}