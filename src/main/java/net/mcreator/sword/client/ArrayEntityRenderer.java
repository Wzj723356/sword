package net.mcreator.sword.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ArrayEntityRenderer extends EntityRenderer<net.mcreator.sword.entity.ArrayEntity> {
    
    public ArrayEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
    
    @Override
    public ResourceLocation getTextureLocation(net.mcreator.sword.entity.ArrayEntity entity) {
        return null;
    }
}