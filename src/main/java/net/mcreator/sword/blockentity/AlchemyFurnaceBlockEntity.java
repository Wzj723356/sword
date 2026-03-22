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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.mcreator.sword.blocks.AlchemyFurnaceBlock;
import net.mcreator.sword.init.SwordModBlockEntities;
import net.mcreator.sword.recipes.AlchemyRecipe;

import javax.annotation.Nullable;
import java.util.Optional;

public class AlchemyFurnaceBlockEntity extends BlockEntity implements WorldlyContainer {
    private NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
    private int progress = 0;
    private int maxProgress = 200;
    private int fireTime = 0;
    private int maxFireTime = 1600;

    private static final int[] SLOTS_FOR_UP = new int[]{0, 1, 2, 3};
    private static final int[] SLOTS_FOR_DOWN = new int[]{4};
    private static final int[] SLOTS_FOR_SIDES = new int[]{5};

    public AlchemyFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(SwordModBlockEntities.ALCHEMY_FURNACE, pos, state);
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
            return index >= 0 && index <= 3;
        } else if (direction == Direction.DOWN) {
            return false;
        } else {
            return index == 5;
        }
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN) {
            return index == 4;
        }
        return false;
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

    public static void tick(Level level, BlockPos pos, BlockState state, AlchemyFurnaceBlockEntity blockEntity) {
        if (level.isClientSide) {
            return;
        }

        boolean isLit = false;
        boolean hasFire = blockEntity.fireTime > 0;

        if (hasFire) {
            blockEntity.fireTime--;
            isLit = true;
        }

        if (blockEntity.hasRecipe()) {
            if (!hasFire) {
                ItemStack fuelStack = blockEntity.items.get(5);
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
                    blockEntity.craftItem();
                    blockEntity.resetProgress();
                }
            }
        } else {
            blockEntity.resetProgress();
        }

        if (isLit != state.getValue(AlchemyFurnaceBlock.LIT)) {
            level.setBlock(pos, state.setValue(AlchemyFurnaceBlock.LIT, isLit), 3);
        }

        blockEntity.setChanged();
    }

    private boolean hasRecipe() {
        Optional<AlchemyRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }
        
        ItemStack output = recipe.get().getResultItem();
        ItemStack outputSlot = items.get(4);
        
        return outputSlot.isEmpty() || 
               (outputSlot.getItem() == output.getItem() && 
                outputSlot.getCount() + output.getCount() <= outputSlot.getMaxStackSize());
    }

    private Optional<AlchemyRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(5);
        for (int i = 0; i < 5; i++) {
            inventory.setItem(i, items.get(i));
        }
        
        return level.getRecipeManager()
                .getRecipeFor(AlchemyRecipe.Type.INSTANCE, inventory, level)
                .map(recipe -> recipe);
    }

    private void craftItem() {
        Optional<AlchemyRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack output = recipe.get().getResultItem();
        ItemStack outputSlot = items.get(4);

        if (outputSlot.isEmpty()) {
            items.set(4, output.copy());
        } else {
            outputSlot.grow(output.getCount());
        }

        for (int i = 0; i < 5; i++) {
            items.get(i).shrink(1);
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private int getFireTime(ItemStack stack) {
        return net.fabricmc.fabric.impl.content.registry.FuelRegistryImpl.INSTANCE.get(stack.getItem());
    }

    public void takeOutPill(Player player) {
        BlockState state = level.getBlockState(worldPosition);
        if (!state.getValue(AlchemyFurnaceBlock.OPEN)) {
            player.displayClientMessage(Component.literal("请先打开炼丹炉盖子（潜行+右键）"), true);
            return;
        }
        
        ItemStack result = items.get(4).copy();
        if (!result.isEmpty()) {
            player.getInventory().add(result);
            items.set(4, ItemStack.EMPTY);
            player.displayClientMessage(Component.literal("成功取出: " + result.getDisplayName().getString()), true);
            setChanged();
        } else {
            player.displayClientMessage(Component.literal("炼丹炉中没有丹药"), true);
        }
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
