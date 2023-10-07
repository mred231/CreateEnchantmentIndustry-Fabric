package plus.dragons.createenchantmentindustry.foundation.mixin;

import static plus.dragons.createenchantmentindustry.EnchantmentIndustry.REGISTRATE;
import plus.dragons.createenchantmentindustry.entry.CeiBlocks;
import plus.dragons.createenchantmentindustry.entry.CeiItems;
import plus.dragons.createenchantmentindustry.entry.CeiFluids;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.AllCreativeModeTabs.RegistrateDisplayItemsGenerator;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

@Mixin(RegistrateDisplayItemsGenerator.class)
public abstract class RegistrateDisplayItemsGeneratorMixin implements CreativeModeTab.DisplayItemsGenerator {

	@SuppressWarnings("unchecked")
	private static void addOrdering(List orderings, String name, Item item, Item anchor) {
		Class<?> cls;
		Method method;
		Object ordering;

		try {
			cls = Class.forName("com.simibubi.create.AllCreativeModeTabs$RegistrateDisplayItemsGenerator$ItemOrdering");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		try {
			method = cls.getMethod(name, Item.class, Item.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

		try {
			ordering = method.invoke(null, item, anchor);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		orderings.add(ordering);
	}

	@Inject(method = "makeOrderings", at = @At(value = "TAIL"), cancellable = true, remap = false)
	private static void injectMakeOrderingsReturn(CallbackInfoReturnable<List<?>> cir)
	{
		List orderings = cir.getReturnValue();

		Map<Item, Item> afterOrderings = Map.of(
			CeiBlocks.DISENCHANTER.asItem(), AllBlocks.ITEM_DRAIN.asItem(),
			CeiBlocks.PRINTER.asItem(), AllBlocks.SPOUT.asItem(),
			CeiItems.ENCHANTING_GUIDE.asItem(), AllBlocks.BLAZE_BURNER.asItem(),
			CeiItems.HYPER_EXP_BOTTLE.asItem(), AllItems.SUPER_GLUE.asItem(),
			AllFluids.HONEY.get().getBucket().asItem(), CeiFluids.INK.get().getBucket().asItem(),
			AllFluids.CHOCOLATE.get().getBucket().asItem(), CeiFluids.INK.get().getBucket().asItem()
		);

		afterOrderings.forEach((item, anchor) -> {
			addOrdering(orderings, "after", item, anchor);
		});

		cir.setReturnValue(orderings);
	}

	@Inject(method = "collectBlocks", at = @At(value = "TAIL"), cancellable = true)
	private void injectCollectBlocks(ResourceKey<CreativeModeTab> tab, Predicate<Item> exclusionPredicate, CallbackInfoReturnable<List<Item>> cir) {
		List<Item> items = cir.getReturnValue();
		for (RegistryEntry<Block> entry : REGISTRATE.getAll(Registries.BLOCK)) {
			if (!REGISTRATE.isInCreativeTab(entry, tab))
				continue;
			Item item = entry.get().asItem();
			if (item == Items.AIR)
				continue;
			if (!exclusionPredicate.test(item))
				items.add(item);
		}
		// Seems like a bug? upstream that the normal fluid buckets are unavailable
		for (RegistryEntry<Fluid> entry : com.simibubi.create.Create.REGISTRATE.getAll(Registries.FLUID)) {
			var item = entry.get().getBucket().asItem();
			if (item instanceof BucketItem)
				items.add(item);
		}
		cir.setReturnValue(new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items)));
	}

	@Inject(method = "collectItems", at = @At(value = "TAIL"), cancellable = true)
	private void injectCollectItems(ResourceKey<CreativeModeTab> tab, Predicate<Item> is3d, boolean special, Predicate<Item> exclusionPredicate, CallbackInfoReturnable<List<Item>> cir) {
		List<Item> items = cir.getReturnValue();
		for (RegistryEntry<Item> entry : REGISTRATE.getAll(Registries.ITEM)) {
			if (!REGISTRATE.isInCreativeTab(entry, tab))
				continue;
			Item item = entry.get();
			if (item instanceof BlockItem)
				continue;
			if (is3d.test(item) != special)
				continue;
			if (!exclusionPredicate.test(item))
				items.add(item);
		}
		cir.setReturnValue(new ReferenceArrayList<>(items));
	}
}
