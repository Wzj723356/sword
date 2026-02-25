package net.mcreator.sword.items;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.mcreator.sword.cultivation.CultivationManager;
import net.mcreator.sword.cultivation.CultivationData;
import net.mcreator.sword.cultivation.SkillManager;
import net.mcreator.sword.cultivation.CultivationTechnique;

public class CultivationSword extends SwordItem {
    public CultivationSword() {
        super(Tiers.NETHERITE, 3, -2.4F, new Item.Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            CultivationData data = CultivationManager.getCultivationData(player);
            if (data.hasStartedCultivation()) {
                CultivationTechnique technique = data.getTechnique();
                
                // 应用功法伤害加成
                if (technique.isPassiveSkill() && (technique.name().startsWith("SWORD_"))) {
                    float damageMultiplier = (float) technique.getDamageMultiplier();
                    target.hurt(DamageSource.playerAttack(player), 3.0F * (damageMultiplier - 1.0F));
                }
                
                // 应用技能伤害加成
                float skillMultiplier = SkillManager.getDamageMultiplier(player);
                if (skillMultiplier > 1.0F) {
                    target.hurt(DamageSource.playerAttack(player), 3.0F * (skillMultiplier - 1.0F));
                }
                
                // 消耗少量灵力
                if (data.getSpiritualPower() >= 2) {
                    data.consumeSpiritualPower(2);
                    CultivationManager.saveCultivationData(player, data);
                }
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
