package plus.dragons.createenchantmentindustry.entry;

import static plus.dragons.createenchantmentindustry.EnchantmentIndustry.REGISTRATE;

import java.util.List;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import plus.dragons.createdragonlib.event.FillCreateItemGroupEvent;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter.EnchantingGuideItem;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.experience.HyperExperienceBottleItem;

public class CeiItems {

    static {
        REGISTRATE.useCreativeTab(AllCreativeModeTabs.MAIN_TAB.key());
    }

    public static final ItemEntry<EnchantingGuideItem> ENCHANTING_GUIDE = REGISTRATE.item("enchanting_guide", EnchantingGuideItem::new)
            .properties(prop -> prop.stacksTo(1))
            .register();

    public static final ItemEntry<HyperExperienceBottleItem> HYPER_EXP_BOTTLE = REGISTRATE.item("hyper_experience_bottle", HyperExperienceBottleItem::new)
            .properties(prop -> prop.rarity(Rarity.RARE))
            .lang("Bottle O' Hyper Enchanting")
            .tag(CeiTags.ItemTag.UPRIGHT_ON_BELT.tag)
            .register();

    public static InteractionResult fillCreateItemGroup(AllCreativeModeTabs.TabInfo itemGroup, List<Item> items) {
        if (itemGroup == AllCreativeModeTabs.MAIN_TAB) {
			FillCreateItemGroupEvent.Inserter inserter = new FillCreateItemGroupEvent.Inserter(items);
			inserter.addInsertion(AllBlocks.ITEM_DRAIN.get(), CeiBlocks.DISENCHANTER.asStack());
			inserter.addInsertion(AllBlocks.SPOUT.get(), CeiBlocks.PRINTER.asStack());
			inserter.addInsertion(AllBlocks.BLAZE_BURNER.get(), ENCHANTING_GUIDE.asStack());
			inserter.addInsertion(AllItems.BUILDERS_TEA, CeiFluids.INK.get().getBucket().getDefaultInstance());
			inserter.addInsertion(AllItems.BUILDERS_TEA, HYPER_EXP_BOTTLE.asStack());
			inserter.doneInsertion();
			return InteractionResult.SUCCESS;
        }
		return InteractionResult.PASS;
    }

    public static void register() {}

}
