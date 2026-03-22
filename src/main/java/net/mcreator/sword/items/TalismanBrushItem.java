package net.mcreator.sword.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TalismanBrushItem extends Item {
    private static final String TAG_SELECTED_TYPE = "SelectedTalismanType";
    
    public enum TalismanType {
        ATTACK("attack", "攻击符"),
        DEFENSE("defense", "防御符"),
        HEALING("healing", "治疗符"),
        LIGHTNING("lightning", "雷符"),
        FIRE("fire", "火符"),
        ICE("ice", "冰符"),
        WIND("wind", "风符"),
        TELEPORT("teleport", "传送符");
        
        private final String id;
        private final String displayName;
        
        TalismanType(String id, String displayName) {
            this.id = id;
            this.displayName = displayName;
        }
        
        public String getId() { return id; }
        public String getDisplayName() { return displayName; }
        
        public static TalismanType fromId(String id) {
            for (TalismanType type : values()) {
                if (type.id.equals(id)) return type;
            }
            return ATTACK;
        }
    }
    
    public TalismanBrushItem() {
        super(new Properties().stacksTo(1).durability(100));
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide && player.isShiftKeyDown()) {
            TalismanType currentType = getSelectedType(stack);
            TalismanType nextType = TalismanType.values()[(currentType.ordinal() + 1) % TalismanType.values().length];
            setSelectedType(stack, nextType);
            player.displayClientMessage(Component.literal("已选择: " + nextType.getDisplayName()), true);
            return InteractionResultHolder.success(stack);
        }
        
        return InteractionResultHolder.pass(stack);
    }
    
    public TalismanType getSelectedType(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return TalismanType.fromId(tag.getString(TAG_SELECTED_TYPE));
    }
    
    public void setSelectedType(ItemStack stack, TalismanType type) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString(TAG_SELECTED_TYPE, type.getId());
    }
    
    @Override
    public Component getName(ItemStack stack) {
        TalismanType type = getSelectedType(stack);
        return Component.literal("符笔 (" + type.getDisplayName() + ")");
    }
}
