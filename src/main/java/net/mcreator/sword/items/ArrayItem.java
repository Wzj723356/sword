package net.mcreator.sword.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.core.BlockPos;
import net.mcreator.sword.entity.ArrayEntity;
import net.mcreator.sword.init.SwordModEntities;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;

public class ArrayItem extends Item {
    private final String arrayType;

    public ArrayItem(String arrayType, Item.Properties properties) {
        super(properties);
        this.arrayType = arrayType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        HitResult hit = player.pick(5.0, 0.0f, false);
        if (hit.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) hit;
            if (entityHit.getEntity() instanceof ArrayEntity array) {
                array.discard();
                player.displayClientMessage(Component.literal("法阵已被破除！"), true);
                
                if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                    for (int i = 0; i < 50; i++) {
                        serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SMOKE,
                            array.getX() + (level.random.nextDouble() - 0.5) * 3.0,
                            array.getY() + 0.5 + level.random.nextDouble() * 1.0,
                            array.getZ() + (level.random.nextDouble() - 0.5) * 3.0,
                            1, 0, 0.05, 0, 0);
                    }
                }
                
                ItemStack stack = player.getItemInHand(hand);
                return InteractionResultHolder.success(stack);
            }
        }
        
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos pos = blockHit.getBlockPos();
            
            ArrayEntity array = new ArrayEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, arrayType);
            level.addFreshEntity(array);
            
            ItemStack stack = player.getItemInHand(hand);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            
            return InteractionResultHolder.success(stack);
        }
        
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    public String getArrayType() {
        return arrayType;
    }
}