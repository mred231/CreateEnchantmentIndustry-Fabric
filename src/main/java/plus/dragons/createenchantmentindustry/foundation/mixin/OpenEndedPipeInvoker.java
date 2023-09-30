package plus.dragons.createenchantmentindustry.foundation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.simibubi.create.content.fluids.OpenEndedPipe;

import io.github.fabricators_of_create.porting_lib.util.FluidStack;

@Mixin(OpenEndedPipe.class)
public interface OpenEndedPipeInvoker {
    @Invoker(remap = false)
    void invokeApplyEffects(FluidStack fluid);
}
