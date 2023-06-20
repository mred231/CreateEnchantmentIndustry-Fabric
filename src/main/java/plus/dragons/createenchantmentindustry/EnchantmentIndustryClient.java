package plus.dragons.createenchantmentindustry;

import com.simibubi.create.foundation.config.ui.BaseConfigScreen;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.inventory.InventoryMenu;
import plus.dragons.createdragonlib.event.TextureStitchPreEvent;
import plus.dragons.createdragonlib.event.ViewPointFogColorEvent;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter.BlazeEnchanterRenderer;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.ink.InkRenderingCamera;
import plus.dragons.createenchantmentindustry.entry.CeiBlockPartials;
import plus.dragons.createenchantmentindustry.foundation.config.CeiConfigs;
import plus.dragons.createenchantmentindustry.foundation.ponder.content.CeiPonderIndex;
public class EnchantmentIndustryClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		CeiBlockPartials.register();

		CeiPonderIndex.register();
		CeiPonderIndex.registerTags();

		BaseConfigScreen.setDefaultActionFor(EnchantmentIndustry.ID, screen -> screen
				.withTitles(null, null, "Gameplay Settings")
				.withSpecs(null, null, CeiConfigs.SERVER_SPEC)
		);

		ViewPointFogColorEvent.CallBack.EVENT.register(InkRenderingCamera::cameraEffect);

		TextureStitchPreEvent.CallBack.EVENT.register((location, sprites) -> {
			if (location.equals(InventoryMenu.BLOCK_ATLAS)) {
				sprites.add(BlazeEnchanterRenderer.BOOK_MATERIAL.texture());
			}
		});
	}
}
