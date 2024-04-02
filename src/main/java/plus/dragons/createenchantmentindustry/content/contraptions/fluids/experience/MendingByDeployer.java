package plus.dragons.createenchantmentindustry.content.contraptions.fluids.experience;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.Nullable;

public class MendingByDeployer {

	public static boolean canItemBeMended(ItemStack stack) {
		return stack.isDamaged() && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, stack) > 0;
	}

	public static int getRequiredAmountForItem(ItemStack stack) {
		return Mth.ceil(stack.getDamageValue() / getXpRepairRatio());
	}

	public static int getRemainingXp(int xpAmount, ItemStack stack) {
		return Math.max(0, xpAmount - getRequiredAmountForItem(stack));
	}

	@Nullable
	public static ItemStack mendItem(int xpAmount, ItemStack stack) {
		stack.setDamageValue(stack.getDamageValue() - (int)(xpAmount * getXpRepairRatio()));
		return stack;
	}

	private static float getXpRepairRatio(){
		// In IForgeItem:
		//default float getXpRepairRatio(ItemStack stack)
		//    {
		//        return 2f;
		//    }
		return 2f;
	}
}
