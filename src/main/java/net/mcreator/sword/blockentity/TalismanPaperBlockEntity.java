package net.mcreator.sword.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.mcreator.sword.blocks.TalismanPaperBlock;
import net.mcreator.sword.init.SwordModBlockEntities;
import net.mcreator.sword.init.SwordModItems;
import net.mcreator.sword.items.TalismanBrushItem;

import java.util.UUID;

public class TalismanPaperBlockEntity extends BlockEntity {
    private int tickCount = 0;
    private int drawTime = 60;
    private UUID ownerUUID;
    private TalismanBrushItem.TalismanType talismanType = TalismanBrushItem.TalismanType.ATTACK;
    private boolean isDrawing = false;
    
    public TalismanPaperBlockEntity(BlockPos pos, BlockState state) {
        super(SwordModBlockEntities.TALISMAN_PAPER, pos, state);
    }
    
    public void setOwner(UUID uuid) {
        this.ownerUUID = uuid;
        this.isDrawing = true;
        setChanged();
    }
    
    public void setTalismanType(TalismanBrushItem.TalismanType type) {
        this.talismanType = type;
        setChanged();
    }
    
    public void tick() {
        if (level == null || level.isClientSide) return;
        
        if (isDrawing) {
            tickCount++;
            
            int progress = Math.min(10, (int) ((float) tickCount / drawTime * 10));
            if (level.getBlockState(worldPosition).getValue(TalismanPaperBlock.DRAWING_PROGRESS) != progress) {
                level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(TalismanPaperBlock.DRAWING_PROGRESS, progress), 3);
            }
            
            if (tickCount >= drawTime) {
                completeDrawing();
            }
        }
    }
    
    private void completeDrawing() {
        if (level == null || level.isClientSide) return;
        
        ItemStack result = createTalismanItem();
        
        ItemEntity itemEntity = new ItemEntity(level, worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5, result);
        itemEntity.setPickUpDelay(10);
        level.addFreshEntity(itemEntity);
        
        level.removeBlock(worldPosition, false);
    }
    
    private ItemStack createTalismanItem() {
        switch (talismanType) {
            case ATTACK:
                return new ItemStack(SwordModItems.ATTACK_TALISMAN);
            case DEFENSE:
                return new ItemStack(SwordModItems.DEFENSE_TALISMAN);
            case HEALING:
                return new ItemStack(SwordModItems.HEALING_TALISMAN);
            case LIGHTNING:
                return new ItemStack(SwordModItems.LIGHTNING_TALISMAN);
            case FIRE:
                return new ItemStack(SwordModItems.FIRE_TALISMAN);
            case ICE:
                return new ItemStack(SwordModItems.ICE_TALISMAN);
            case WIND:
                return new ItemStack(SwordModItems.WIND_TALISMAN);
            case TELEPORT:
                return new ItemStack(SwordModItems.TELEPORT_TALISMAN);
            default:
                return new ItemStack(SwordModItems.ATTACK_TALISMAN);
        }
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("TickCount", tickCount);
        tag.putInt("DrawTime", drawTime);
        tag.putBoolean("IsDrawing", isDrawing);
        if (ownerUUID != null) {
            tag.putUUID("Owner", ownerUUID);
        }
        tag.putString("TalismanType", talismanType.getId());
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        tickCount = tag.getInt("TickCount");
        drawTime = tag.getInt("DrawTime");
        isDrawing = tag.getBoolean("IsDrawing");
        if (tag.hasUUID("Owner")) {
            ownerUUID = tag.getUUID("Owner");
        }
        talismanType = TalismanBrushItem.TalismanType.fromId(tag.getString("TalismanType"));
    }
}
