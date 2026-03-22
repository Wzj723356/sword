/*
*	MCreator note:
*
*	If you lock base mod element files, you can edit this file and the proxy files
*	and they won't get overwritten. If you change your mod package or modid, you
*	need to apply these changes to this file MANUALLY.
*
*
*	If you do not lock base mod element files in Workspace settings, this file
*	will be REGENERATED on each build.
*
*/
package net.mcreator.sword;

import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.mcreator.sword.network.CultivationClientPacketHandler;
import net.mcreator.sword.client.CultivationHUD;
import net.mcreator.sword.client.ArrayEntityRenderer;
import net.mcreator.sword.client.SkillKeyBindings;
import net.mcreator.sword.client.screen.AlchemyFurnaceScreen;
import net.mcreator.sword.client.screen.RefiningFurnaceScreen;
import net.mcreator.sword.init.SwordModEntities;
import net.mcreator.sword.init.SwordModMenus;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ClientModInitializer;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {
	private static KeyMapping toggleHUDKey;

	@Override
	public void onInitializeClient() {
		CultivationClientPacketHandler.register();
		CultivationHUD.register();
		SkillKeyBindings.register();
		
		EntityRendererRegistry.register(SwordModEntities.ARRAY, ArrayEntityRenderer::new);
		
		ScreenRegistry.register(SwordModMenus.ALCHEMY_FURNACE, AlchemyFurnaceScreen::new);
		ScreenRegistry.register(SwordModMenus.REFINING_FURNACE, RefiningFurnaceScreen::new);
		
		toggleHUDKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
			"key.sword.toggle_hud",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_K,
			"key.categories.sword"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleHUDKey.consumeClick()) {
				CultivationHUD.toggleHUD();
			}
			SkillKeyBindings.tick();
		});
	}
}