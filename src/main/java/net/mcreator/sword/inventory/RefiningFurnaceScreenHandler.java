package net.mcreator.sword.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.mcreator.sword.blockentity.RefiningFurnaceBlockEntity;
import net.mcreator.sword.init.SwordModMenus;

import java.util.Objects;

public class RefiningFurnaceScreenHandler extends AbstractContainerMenu {
    private final RefiningFurnaceBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public RefiningFurnaceScreenHandler(int id, Inventory inventory) {
        this(id, inventory, null, new SimpleContainerData(4));
    }

    public RefiningFurnaceScreenHandler(int id, Inventory inventory, FriendlyByteBuf buffer) {
        this(id, inventory, Objects.requireNonNull(inventory.player.level().getBlockEntity(buffer.readBlockPos())), new SimpleContainerData(4));
    }

    public RefiningFurnaceScreenHandler(int id, Inventory inventory, BlockEntity blockEntity, ContainerData data) {
        super(SwordModMenus.REFINING_FURNACE, id);
        this.blockEntity = (RefiningFurnaceBlockEntity) blockEntity;
        this.level = inventory.player.level();
        this.data = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.addSlot(new Slot(this.blockEntity, 0, 80, 17));
        this.addSlot(new Slot(this.blockEntity, 1, 53, 53));
        this.addSlot(new Slot(this.blockEntity, 2, 80, 53));
        this.addSlot(new Slot(this.blockEntity, 3, 8, 53));

        addDataSlots(data);
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    public static void openScreen(net.minecraft.server.level.ServerPlayer player, BlockPos pos) {
        final BlockPos finalPos = pos;
        player.openMenu(new net.minecraft.world.MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.literal("炼器炉");
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                return new RefiningFurnaceScreenHandler(id, inventory, player.level().getBlockEntity(finalPos), new SimpleContainerData(4));
            }
        });
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getScaledFireTime() {
        int fireTime = this.data.get(2);
        int maxFireTime = this.data.get(3);
        int fireSize = 14;

        return maxFireTime != 0 && fireTime != 0 ? fireTime * fireSize / maxFireTime : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
        
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index < 36) {
            if (!moveItemStackTo(sourceStack, 36, 40, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < 40) {
            if (!moveItemStackTo(sourceStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(net.minecraft.world.inventory.ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, blockEntity.getBlockState().getBlock());
    }
}
