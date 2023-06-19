package plus.dragons.createenchantmentindustry;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import plus.dragons.createdragonlib.advancement.AdvancementFactory;
import plus.dragons.createdragonlib.init.FillCreateItemGroupEvent;
import plus.dragons.createdragonlib.init.SafeRegistrate;
import plus.dragons.createdragonlib.lang.Lang;
import plus.dragons.createdragonlib.lang.LangFactory;
import plus.dragons.createdragonlib.tag.TagGen;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.OpenEndedPipeEffects;
import plus.dragons.createenchantmentindustry.entry.CeiBlockEntities;
import plus.dragons.createenchantmentindustry.entry.CeiBlocks;
import plus.dragons.createenchantmentindustry.entry.CeiContainerTypes;
import plus.dragons.createenchantmentindustry.entry.CeiEntityTypes;
import plus.dragons.createenchantmentindustry.entry.CeiFluids;
import plus.dragons.createenchantmentindustry.entry.CeiItems;
import plus.dragons.createenchantmentindustry.entry.CeiPackets;
import plus.dragons.createenchantmentindustry.entry.CeiRecipeTypes;
import plus.dragons.createenchantmentindustry.entry.CeiTags;
import plus.dragons.createenchantmentindustry.foundation.advancement.CeiAdvancements;
import plus.dragons.createenchantmentindustry.foundation.ponder.content.CeiPonderIndex;

public class EnchantmentIndustry implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String NAME = "Create Enchantment Industry";
    public static final String ID = "create_enchantment_industry";
    public static final SafeRegistrate REGISTRATE = new SafeRegistrate(ID);
    public static final Lang LANG = new Lang(ID);
    public static final AdvancementFactory ADVANCEMENT_FACTORY = AdvancementFactory.create(NAME, ID,
        CeiAdvancements::register);
    public static final LangFactory LANG_FACTORY = LangFactory.create(NAME, ID)
        .advancements(CeiAdvancements::register)
        .ponders(() -> {
            CeiPonderIndex.register();
            CeiPonderIndex.registerTags();
        })
        .tooltips()
        .ui();


    public static ResourceLocation genRL(String name) {
        return new ResourceLocation(ID, name);
    }

	@Override
	public void onInitialize() {
		CeiBlocks.register();
		CeiItems.register();
		CeiFluids.register();
		CeiBlockEntities.register();
		CeiContainerTypes.register();
		CeiEntityTypes.register();
		CeiRecipeTypes.register();
		CeiTags.register();

		// fabric exclusive, squeeze this in here to register before stuff is used
		REGISTRATE.register();

		CeiAdvancements.register();
		CeiPackets.registerPackets();
		CeiFluids.registerLavaReaction();
		OpenEndedPipeEffects.register();

		FillCreateItemGroupEvent.CallBack.EVENT.register(CeiItems::fillCreateItemGroup);

		ServerTickEvents.START_WORLD_TICK.register(CeiFluids::handleInkEffect);

		CeiPackets.getChannel().initServerListener();
	}

	public static void gatherData(FabricDataGenerator gen, ExistingFileHelper helper) {
		// FIXME datagen has serious problem

		ADVANCEMENT_FACTORY.datagen(gen);
		new TagGen.Builder(REGISTRATE)
				.addItemTagFactory(CeiTags::genItemTag)
				.addFluidTagFactory(CeiTags::genFluidTag)
				.build().activate();
		LANG_FACTORY.datagen(gen);

	}
}
