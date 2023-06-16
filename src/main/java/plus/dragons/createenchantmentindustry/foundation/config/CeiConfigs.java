package plus.dragons.createenchantmentindustry.foundation.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.api.fml.event.config.ModConfigEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

// TODO
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CeiConfigs {

    public static CeiServerConfig SERVER;
    public static ForgeConfigSpec SERVER_SPEC;

    public static void register(ModLoadingContext context) {
        Pair<CeiServerConfig, ForgeConfigSpec> serverConfigPair = new ForgeConfigSpec.Builder().configure(builder -> {
            CeiServerConfig config = new CeiServerConfig();
            config.registerAll(builder);
            return config;
        });
        SERVER = serverConfigPair.getKey();
        SERVER_SPEC = serverConfigPair.getValue();
        context.registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        if (SERVER_SPEC == event.getConfig().getSpec())
            SERVER.onLoad();
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        if (SERVER_SPEC == event.getConfig().getSpec())
            SERVER.onReload();
    }

}
