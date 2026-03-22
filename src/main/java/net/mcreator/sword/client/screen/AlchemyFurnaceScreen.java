package net.mcreator.sword.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.mcreator.sword.SwordMod;
import net.mcreator.sword.inventory.AlchemyFurnaceScreenHandler;

public class AlchemyFurnaceScreen extends AbstractContainerScreen<AlchemyFurnaceScreenHandler> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(SwordMod.MODID, "textures/gui/alchemy_furnace.png");

    public AlchemyFurnaceScreen(AlchemyFurnaceScreenHandler menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.isCrafting()) {
            int progress = menu.getScaledProgress();
            this.blit(poseStack, x + 89, y + 34, 176, 14, progress + 1, 16);
        }

        int fireTime = menu.getScaledFireTime();
        if (fireTime > 0) {
            this.blit(poseStack, x + 14, y + 56 - fireTime, 176, 14 - fireTime, 14, fireTime);
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        renderTooltip(poseStack, mouseX, mouseY);
    }
}
