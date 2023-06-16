package plus.dragons.createenchantmentindustry;

import com.simibubi.create.foundation.config.ui.BaseConfigScreen;

import net.fabricmc.api.ClientModInitializer;
import plus.dragons.createenchantmentindustry.entry.CeiBlockPartials;
import plus.dragons.createenchantmentindustry.foundation.config.CeiConfigs;
import plus.dragons.createenchantmentindustry.foundation.ponder.content.CeiPonderIndex;
// TODO
public class EnchantmentIndustryClient implements ClientModInitializer {


	// FIXME
    /*private void registerForgeEvents(IEventBus forgeEventBus) {
        forgeEventBus.addListener(InkRenderingCamera::handleInkFogColor);
    }*/


    // FIXME
    /*public void modelRegistry(final TextureStitchEvent.Pre event) {
        if (event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
            event.addSprite(BlazeEnchanterRenderer.BOOK_MATERIAL.texture());
        }
    }*/

	@Override
	public void onInitializeClient() {
		CeiBlockPartials.register();

		CeiPonderIndex.register();
		CeiPonderIndex.registerTags();

		BaseConfigScreen.setDefaultActionFor(EnchantmentIndustry.ID, screen -> screen
				.withTitles(null, null, "Gameplay Settings")
				.withSpecs(null, null, CeiConfigs.SERVER_SPEC)
		);
	}
}
