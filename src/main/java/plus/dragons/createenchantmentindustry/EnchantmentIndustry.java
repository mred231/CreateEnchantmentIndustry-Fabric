package plus.dragons.createenchantmentindustry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.resources.ResourceLocation;
import plus.dragons.createdragonlib.advancement.AdvancementFactory;
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
import plus.dragons.createenchantmentindustry.foundation.config.CeiConfigs;
import plus.dragons.createenchantmentindustry.foundation.ponder.content.CeiPonderIndex;

// TODO
@Mod(EnchantmentIndustry.ID)
public class EnchantmentIndustry {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String NAME = "Create: Enchantment Industry";
    public static final String ID = "create_enchantment_industry";
    public static final SafeRegistrate REGISTRATE = new SafeRegistrate(ID);
    public static final Lang LANG = new Lang(ID);
    public static final AdvancementFactory ADVANCEMENT_FACTORY = AdvancementFactory.create(NAME, ID,
        CeiAdvancements::register);
    private static final LangFactory LANG_FACTORY = LangFactory.create(NAME, ID)
        .advancements(CeiAdvancements::register)
        .ponders(() -> {
            CeiPonderIndex.register();
            CeiPonderIndex.registerTags();
        })
        .tooltips()
        .ui();

    public EnchantmentIndustry() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        CeiConfigs.register(ModLoadingContext.get());

        registerEntries(modEventBus);
        modEventBus.register(this);
        modEventBus.addListener(EventPriority.LOWEST, ADVANCEMENT_FACTORY::datagen);
        modEventBus.addListener(EventPriority.LOWEST, LANG_FACTORY::datagen);
        registerForgeEvents(forgeEventBus);
        new TagGen.Builder(REGISTRATE)
                .addItemTagFactory(CeiTags::genItemTag)
                .addFluidTagFactory(CeiTags::genFluidTag)
                .build().activate();

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> EnchantmentIndustryClient::new);
    }

    private void registerEntries(IEventBus modEventBus) {
        CeiBlocks.register();
        CeiBlockEntities.register();
        CeiContainerTypes.register();
        CeiEntityTypes.register();
        CeiFluids.register();
        CeiItems.register();
        CeiRecipeTypes.register(modEventBus);
        CeiTags.register();
        REGISTRATE.registerEventListeners(modEventBus);
    }

    private void registerForgeEvents(IEventBus forgeEventBus) {
        forgeEventBus.addListener(CeiItems::fillCreateItemGroup);
        forgeEventBus.addListener(CeiFluids::handleInkEffect);
    }

    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CeiAdvancements.register();
            CeiPackets.registerPackets();
            CeiFluids.registerLavaReaction();
            OpenEndedPipeEffects.register();
            ApotheosisCompat.addPotionMixingRecipes();
            QuarkCompat.registerPrintEntry();
        });
    }

    public static ResourceLocation genRL(String name) {
        return new ResourceLocation(ID, name);
    }

}
