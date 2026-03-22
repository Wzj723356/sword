package net.mcreator.sword.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackHelper;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.mcreator.sword.blocks.RefiningFurnaceBlock;
import net.mcreator.sword.init.SwordModBlockEntities;
import net.mcreator.sword.items.CultivationSword;
import net.mcreator.sword.items.CultivationBlade;
import net.mcreator.sword.items.CultivationSpear;

import javax.annotation.Nullable;
import java.util.Random;

public class RefiningFurnaceBlockEntity extends BlockEntity implements WorldlyContainer {
    private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private int progress = 0;
    private int maxProgress = 100;
    private int fireTime = 0;
    private int maxFireTime = 1600;

    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{0};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1, 2, 3};

    private static final Random random = new Random();

    public RefiningFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(SwordModBlockEntities.REFINING_FURNACE, pos, state);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.UP) {
            return SLOTS_FOR_UP;
        } else if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        if (direction == null) return true;
        if (direction == Direction.UP) {
            return index == 0 && isRefinableItem(stack);
        } else {
            return index >= 1 && index <= 3;
        }
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN) {
            return index == 0;
        }
        return false;
    }

    private boolean isRefinableItem(ItemStack stack) {
        return stack.getItem() instanceof CultivationSword || 
               stack.getItem() instanceof CultivationBlade || 
               stack.getItem() instanceof CultivationSpear;
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ItemStackHelper.removeItem(items, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ItemStackHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        setChanged();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        if (level.getBlockEntity(getBlockPos()) != this) {
            return false;
        }
        return player.distanceToSqr(getBlockPos().getX() + 0.5D, getBlockPos().getY() + 0.5D, getBlockPos().getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Items", ItemStackHelper.saveAllItems(new CompoundTag(), items));
        nbt.putInt("progress", progress);
        nbt.putInt("fireTime", fireTime);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        ItemStackHelper.loadAllItems(nbt.getCompound("Items"), items);
        progress = nbt.getInt("progress");
        fireTime = nbt.getInt("fireTime");
    }

    public void drops() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), stack);
            }
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, RefiningFurnaceBlockEntity blockEntity) {
        if (level.isClientSide) {
            return;
        }

        boolean isLit = false;
        boolean hasFire = blockEntity.fireTime > 0;

        if (hasFire) {
            blockEntity.fireTime--;
            isLit = true;
        }

        if (blockEntity.canRefine()) {
            if (!hasFire) {
                ItemStack fuelStack = blockEntity.items.get(3);
                if (!fuelStack.isEmpty() && blockEntity.getFireTime(fuelStack) > 0) {
                    blockEntity.fireTime = blockEntity.getFireTime(fuelStack);
                    blockEntity.maxFireTime = blockEntity.fireTime;
                    fuelStack.shrink(1);
                    isLit = true;
                }
            }

            if (hasFire || blockEntity.fireTime > 0) {
                blockEntity.progress++;
                isLit = true;
                if (blockEntity.progress >= blockEntity.maxProgress) {
                    blockEntity.refineItem();
                    blockEntity.resetProgress();
                }
            }
        } else {
            blockEntity.resetProgress();
        }

        if (isLit != state.getValue(RefiningFurnaceBlock.LIT)) {
            level.setBlock(pos, state.setValue(RefiningFurnaceBlock.LIT, isLit), 3);
        }

        blockEntity.setChanged();
    }

    private boolean canRefine() {
        ItemStack weapon = items.get(0);
        if (weapon.isEmpty() || !isRefinableItem(weapon)) {
            return false;
        }
        
        ItemStack material1 = items.get(1);
        ItemStack material2 = items.get(2);
        
        return !material1.isEmpty() || !material2.isEmpty();
    }

    private void refineItem() {
        ItemStack weapon = items.get(0);
        ItemStack material1 = items.get(1);
        ItemStack material2 = items.get(2);

        CompoundTag tag = weapon.getOrCreateTag();
        
        // 获取当前强化数据
        int level = tag.getInt("RefineLevel");
        int star = tag.getInt("RefineStar");
        int potential = tag.getInt("RefinePotential");
        int breakthrough = tag.getInt("RefineBreakthrough");
        
        // 如果星级未初始化，根据武器类型设置默认星级
        if (star == 0) {
            star = getDefaultStar(weapon);
            tag.putInt("RefineStar", star);
        }
        
        boolean refined = false;
        
        // 处理材料1
        if (!material1.isEmpty()) {
            if (isSameWeapon(weapon, material1)) {
                // 同样的武器用于提升潜能
                if (potential < 6) {
                    potential++;
                    refined = true;
                }
                material1.shrink(1);
            } else {
                String materialType = getMaterialType(material1);
                if (materialType != null) {
                    switch (materialType) {
                        case "spirit_stone":
                            // 灵石提升等级，上限由星级决定
                            int maxLevel = star * 20;
                            if (level < maxLevel) {
                                level += getMaterialValue(material1);
                                if (level > maxLevel) level = maxLevel;
                                refined = true;
                            }
                            break;
                        case "breakthrough_material":
                            // 满级满潜能后可以突破
                            int maxPotential = star;
                            if (breakthrough < 5 && level >= maxLevel && potential >= maxPotential) {
                                breakthrough++;
                                level = 0; // 突破后重置等级
                                refined = true;
                            }
                            break;
                    }
                    if (refined) material1.shrink(1);
                }
            }
        }
        
        // 处理材料2（如果材料1没有成功强化）
        if (!material2.isEmpty() && !refined) {
            if (isSameWeapon(weapon, material2)) {
                // 同样的武器用于提升潜能
                if (potential < 6) {
                    potential++;
                    refined = true;
                }
                material2.shrink(1);
            } else {
                String materialType = getMaterialType(material2);
                if (materialType != null) {
                    switch (materialType) {
                        case "spirit_stone":
                            int maxLevel = star * 20;
                            if (level < maxLevel) {
                                level += getMaterialValue(material2);
                                if (level > maxLevel) level = maxLevel;
                                refined = true;
                            }
                            break;
                        case "breakthrough_material":
                            int maxPotential = star;
                            if (breakthrough < 5 && level >= maxLevel && potential >= maxPotential) {
                                breakthrough++;
                                level = 0;
                                refined = true;
                            }
                            break;
                    }
                    if (refined) material2.shrink(1);
                }
            }
        }
        
        tag.putInt("RefineLevel", level);
        tag.putInt("RefinePotential", potential);
        tag.putInt("RefineBreakthrough", breakthrough);
        // 星级不可修改，保持原值
        
        weapon.setTag(tag);
    }
    
    private int getDefaultStar(ItemStack weapon) {
        // 根据武器类型返回默认星级
        if (weapon.getItem() instanceof CultivationSword) return 3;
        if (weapon.getItem() instanceof CultivationBlade) return 3;
        if (weapon.getItem() instanceof CultivationSpear) return 3;
        return 1;
    }
    
    private boolean isSameWeapon(ItemStack weapon, ItemStack material) {
        return weapon.getItem() == material.getItem();
    }

    private String getMaterialType(ItemStack stack) {
        String itemId = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        if (itemId.contains("spirit_stone")) {
            return "spirit_stone";
        } else if (itemId.contains("star_material")) {
            return "star_material";
        } else if (itemId.contains("potential_material")) {
            return "potential_material";
        } else if (itemId.contains("breakthrough_material")) {
            return "breakthrough_material";
        }
        return null;
    }

    private int getMaterialValue(ItemStack stack) {
        String itemId = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        if (itemId.contains("low_grade")) {
            return 5;
        } else if (itemId.contains("medium_grade")) {
            return 15;
        } else if (itemId.contains("high_grade")) {
            return 50;
        } else if (itemId.contains("supreme")) {
            return 100;
        }
        return 10;
    }

    private void resetProgress() {
        progress = 0;
    }

    private int getFireTime(ItemStack stack) {
        return net.fabricmc.fabric.impl.content.registry.FuelRegistryImpl.INSTANCE.get(stack.getItem());
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public int getFireTime() {
        return fireTime;
    }

    public int getMaxFireTime() {
        return maxFireTime;
    }
}
