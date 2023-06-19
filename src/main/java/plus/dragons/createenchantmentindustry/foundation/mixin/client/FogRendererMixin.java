package plus.dragons.createenchantmentindustry.foundation.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.material.FogType;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.ink.InkRenderingCamera;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
	@ModifyArgs(method = "setupColor", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V"))
	private static void inkRender(Args args, Camera activeRenderInfo, float partialTicks, ClientLevel level, int renderDistanceChunks, float bossColorModifier) {
		if(activeRenderInfo.getFluidInCamera() == FogType.POWDER_SNOW)
			return;
		if (((InkRenderingCamera) activeRenderInfo).isInInk()) {
			args.set(0, 0);
			args.set(1, 0);
			args.set(2, 0);
		}
	}
}
