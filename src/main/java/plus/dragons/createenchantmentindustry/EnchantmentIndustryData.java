package plus.dragons.createenchantmentindustry;

import static plus.dragons.createenchantmentindustry.EnchantmentIndustry.REGISTRATE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EnchantmentIndustryData implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		Path resources = Paths.get(System.getProperty(ExistingFileHelper.EXISTING_RESOURCES));
		// FIXME re-enable the existing file helper when porting lib's ResourcePackLoader.createPackForMod is fixed
		// Thank SnR for the way to a temporarily fix
		ExistingFileHelper helper = new ExistingFileHelper(
				Set.of(resources), Set.of("create"), false, null, null
		);
		REGISTRATE.setupDatagen(fabricDataGenerator, helper);
		EnchantmentIndustry.gatherData(fabricDataGenerator, helper);
	}
}
