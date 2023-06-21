package plus.dragons.createenchantmentindustry.entry;

import static plus.dragons.createenchantmentindustry.EnchantmentIndustry.REGISTRATE;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.AllFluids;
import com.simibubi.create.infrastructure.item.CreateCreativeModeTab;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import plus.dragons.createdragonlib.event.FillCreateItemGroupEvent;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter.EnchantingGuideItem;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.experience.ExperienceRotorItem;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.experience.HyperExperienceBottleItem;

public class CeiItems {

    static {
        REGISTRATE.creativeModeTab(() -> AllCreativeModeTabs.BASE_CREATIVE_TAB);
    }

    public static final ItemEntry<EnchantingGuideItem> ENCHANTING_GUIDE = REGISTRATE.item("enchanting_guide", EnchantingGuideItem::new)
            .properties(prop -> prop.stacksTo(1))
            .register();

    public static final ItemEntry<HyperExperienceBottleItem> HYPER_EXP_BOTTLE = REGISTRATE.item("hyper_experience_bottle", HyperExperienceBottleItem::new)
            .properties(prop -> prop.rarity(Rarity.RARE))
            .lang("Bottle O' Hyper Enchanting")
            .tag(CeiTags.ItemTag.UPRIGHT_ON_BELT.tag)
            .register();

    public static final ItemEntry<ExperienceRotorItem> EXPERIENCE_ROTOR = REGISTRATE.item("experience_rotor", ExperienceRotorItem::new)
            .register();

    public static InteractionResult fillCreateItemGroup(CreateCreativeModeTab itemGroup, NonNullList<ItemStack> items) {
        if (itemGroup == AllCreativeModeTabs.BASE_CREATIVE_TAB) {
			FillCreateItemGroupEvent.Inserter inserter = new FillCreateItemGroupEvent.Inserter(items);
			inserter.addInsertion(AllBlocks.ITEM_DRAIN.get(), CeiBlocks.DISENCHANTER.asStack());
			inserter.addInsertion(AllBlocks.SPOUT.get(), CeiBlocks.PRINTER.asStack());
			inserter.addInsertion(AllBlocks.BLAZE_BURNER.get(), ENCHANTING_GUIDE.asStack());
			//inserter.addInsertion(AllItems.ELECTRON_TUBE.get(), EXPERIENCE_ROTOR.asStack()); Hide it for now
			inserter.addInsertion(AllFluids.CHOCOLATE.get().getBucket(), CeiFluids.INK.get().getBucket().getDefaultInstance());
			inserter.addInsertion(AllFluids.CHOCOLATE.get().getBucket(), HYPER_EXP_BOTTLE.asStack());
			inserter.doneInsertion();
			return InteractionResult.SUCCESS;
        }
		return InteractionResult.PASS;
    }

    public static void register() {}

}
