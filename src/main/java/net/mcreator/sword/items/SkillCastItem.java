package net.mcreator.sword.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.mcreator.sword.cultivation.CultivationManager;
import net.mcreator.sword.cultivation.CultivationData;
import net.mcreator.sword.cultivation.SkillManager;
import net.mcreator.sword.cultivation.CultivationTechnique;

public class SkillCastItem extends Item {
    private final CultivationTechnique technique;

    public SkillCastItem(CultivationTechnique technique) {
        super(new Item.Properties().stacksTo(1));
        this.technique = technique;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            CultivationData data = CultivationManager.getCultivationData(player);
            
            if (!data.hasStartedCultivation()) {
                player.displayClientMessage(Component.literal("尚未开始修仙，无法使用技能"), true);
                return InteractionResultHolder.fail(stack);
            }
            
            if (!data.canLearnTechnique(technique)) {
                player.displayClientMessage(Component.literal("境界不足，无法使用 " + technique.getDisplayName()), true);
                return InteractionResultHolder.fail(stack);
            }
            
            if (SkillManager.canUseSkill(player, technique)) {
                SkillManager.useSkill(player, technique);
                
                if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                    CultivationManager.syncToClient(serverPlayer);
                }
            }
        }
        
        return InteractionResultHolder.success(stack);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal(technique.getDisplayName());
    }

    public CultivationTechnique getTechnique() {
        return technique;
    }
}