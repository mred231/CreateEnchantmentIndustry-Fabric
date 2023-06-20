package plus.dragons.createenchantmentindustry.foundation.mixin;

import org.spongepowered.asm.mixin.Mixin;

import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;

@Mixin(targets = "com.simibubi.create.content.fluids.OpenEndedPipe$OpenEndFluidHandler")
public abstract class OpenEndFluidHandlerMixin extends FluidTank {
    public OpenEndFluidHandlerMixin(int capacity) {
        super(capacity);
    }

    /*@SuppressWarnings("target")
    @Final
    @Shadow(remap = false)
    OpenEndedPipe this$0;*/

    // TODO
	// What we need here:
	// In code of com.simibubi.create.content.fluids.OpenEndedPipe.OpenEndFluidHandler.insert:

	// if (canApplyEffects(stack) && !hasBlockState)
	//				maxAmount = 81; // fabric: deplete fluids 81 times faster to account for larger amounts
	//			long fill = super.insert(resource, maxAmount, transaction);
	//			if (!stack.isEmpty())
	//				TransactionCallback.onSuccess(transaction, () -> applyEffects(stack));

	// In applyEffects(stack), amount of stack should be set to fill.

}
