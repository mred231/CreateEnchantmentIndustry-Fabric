package plus.dragons.createenchantmentindustry.entry;

import static plus.dragons.createenchantmentindustry.EnchantmentIndustry.REGISTRATE;

import com.simibubi.create.AllCreativeModeTabs;
import com.tterrag.registrate.fabric.SimpleFlowableFluid;
import com.tterrag.registrate.util.entry.FluidEntry;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import plus.dragons.createdragonlib.fluid.FluidLavaReaction;
import plus.dragons.createenchantmentindustry.EnchantmentIndustry;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.experience.ExperienceFluid;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.experience.HyperExperienceFluid;

public class CeiFluids {

    static {
        REGISTRATE.creativeModeTab(() -> AllCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final ResourceLocation EXPERIENCE_STILL_RL = EnchantmentIndustry.genRL("fluid/experience_still");
    public static final ResourceLocation EXPERIENCE_FLOW_RL = EnchantmentIndustry.genRL("fluid/experience_flow");

    public static final FluidEntry<ExperienceFluid> EXPERIENCE = REGISTRATE.virtualFluid("experience",
            EXPERIENCE_STILL_RL, EXPERIENCE_FLOW_RL, ExperienceFluid::new)
            .lang("Liquid Experience")
			.fluidAttributes(()->new FluidVariantAttributeHandler(){
				@Override
				public int getLuminance(FluidVariant variant) {
					return 15;
				}
			})
            .tag(CeiTags.FluidTag.BLAZE_ENCHANTER_INPUT.tag, CeiTags.FluidTag.PRINTER_INPUT.tag)
            .register();

    public static final ResourceLocation HYPER_EXPERIENCE_STILL_RL = EnchantmentIndustry.genRL("fluid/hyper_experience_still");
    public static final ResourceLocation HYPER_EXPERIENCE_FLOW_RL = EnchantmentIndustry.genRL("fluid/hyper_experience_flow");

    public static final FluidEntry<HyperExperienceFluid> HYPER_EXPERIENCE = REGISTRATE.virtualFluid("hyper_experience",
            HYPER_EXPERIENCE_STILL_RL, HYPER_EXPERIENCE_FLOW_RL, HyperExperienceFluid::new)
            .lang("Liquid Hyper Experience")
			.fluidAttributes(()->new FluidVariantAttributeHandler(){
				@Override
				public int getLuminance(FluidVariant variant) {
					return 15;
				}
			})
            .tag(CeiTags.FluidTag.BLAZE_ENCHANTER_INPUT.tag, CeiTags.FluidTag.PRINTER_INPUT.tag)
            .register();

    public static final ResourceLocation INK_STILL_RL = EnchantmentIndustry.genRL("fluid/ink_still");
    public static final ResourceLocation INK_FLOW_RL = EnchantmentIndustry.genRL("fluid/ink_flow");

    public static final FluidEntry<SimpleFlowableFluid.Flowing> INK = REGISTRATE
            .fluid("ink", INK_STILL_RL, INK_FLOW_RL, SimpleFlowableFluid.Flowing::new)
            .fluidAttributes(()->new FluidVariantAttributeHandler(){
			})
			.fluidProperties(p -> p.levelDecreasePerBlock(2)
					.tickRate(25)
					.flowSpeed(4)
					.blastResistance(100f))
            .source(SimpleFlowableFluid.Source::new)
            .tag(CeiTags.FluidTag.INK.tag)
            .bucket()
            .build()
            .register();

    public static void register() {
    }

	// FIXME Ink effect
    /*public static void handleInkEffect(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.tickCount % 20 != 0) return;
        if (entity.isEyeInFluidType(INK.getType())) {
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, true, false, false));
        }
    }*/

    public static void registerLavaReaction() {
        FluidLavaReaction.register(FluidVariant.of(INK.get()),
            Blocks.OBSIDIAN.defaultBlockState(),
            Blocks.BLACKSTONE.defaultBlockState(),
            Blocks.BLACKSTONE.defaultBlockState()
        );
    }
}
