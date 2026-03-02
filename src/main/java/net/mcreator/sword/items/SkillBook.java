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

public class SkillBook extends Item {
    private final CultivationTechnique technique;

    public SkillBook(CultivationTechnique technique) {
        super(new Item.Properties().stacksTo(1));
        this.technique = technique;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            CultivationData data = CultivationManager.getCultivationData(player);
            
            if (!data.hasStartedCultivation()) {
                player.displayClientMessage(Component.literal("尚未开始修仙，无法学习"), true);
                return InteractionResultHolder.fail(stack);
            }
            
            if (!data.canLearnTechnique(technique)) {
                player.displayClientMessage(Component.literal("境界不足，无法学习 " + technique.getDisplayName()), true);
                return InteractionResultHolder.fail(stack);
            }
            
            // 被动功法
            if (technique.isPassiveSkill()) {
                if (data.getTechnique() == technique) {
                    player.displayClientMessage(Component.literal("已经学会了 " + technique.getDisplayName()), true);
                    return InteractionResultHolder.fail(stack);
                }
                data.setTechnique(technique);
                player.displayClientMessage(Component.literal("学会了功法 " + technique.getDisplayName() + "！"), true);
            }
            // 主动技能
            else if (technique.isActiveSkill()) {
                if (data.hasLearnedSkill(technique)) {
                    player.displayClientMessage(Component.literal("已经学会了技能 " + technique.getDisplayName()), true);
                    return InteractionResultHolder.fail(stack);
                }
                data.learnSkill(technique);
                player.displayClientMessage(Component.literal("学会了技能 " + technique.getDisplayName() + "！"), true);
            }
            
            player.displayClientMessage(Component.literal(technique.getDescription()), false);
            CultivationManager.saveCultivationData(player, data);
            
            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                CultivationManager.syncToClient(serverPlayer);
            }
            
            stack.shrink(1);
        }
        
        return InteractionResultHolder.success(stack);
    }

    @Override
    public Component getName(ItemStack stack) {
        String type = technique.isActiveSkill() ? "技能书" : "功法书";
        return Component.literal(technique.getDisplayName() + " " + type);
    }

    public CultivationTechnique getTechnique() {
        return technique;
    }
}