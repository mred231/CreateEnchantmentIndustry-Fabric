package plus.dragons.createenchantmentindustry.foundation.mixin.client;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.inventory.InventoryMenu;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter.BlazeEnchanterRenderer;

@Mixin(TextureAtlas.class)
public abstract class TextureAtlasMixin extends AbstractTexture implements Tickable {
	@Inject(
			method = "prepareToStitch",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V",
					shift = At.Shift.AFTER
			),
			slice = @Slice(
					from = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;push(Ljava/lang/String;)V"),
					to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlas;getBasicSpriteInfos(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/Set;)Ljava/util/Collection;")
			)
	)
	private void textureStitch(ResourceManager resourceManager, Stream<ResourceLocation> spriteNames, ProfilerFiller profiler, int mipLevel, CallbackInfoReturnable<TextureAtlas.Preparations> cir) {
		var set = spriteNames.peek((resourceLocation) -> {
			if (resourceLocation == null) {
				throw new IllegalArgumentException("Location cannot be null!");
			}
		}).collect(Collectors.toSet());
		if (((TextureAtlas)(Object)this).location().equals(InventoryMenu.BLOCK_ATLAS)) {
			set.add(BlazeEnchanterRenderer.BOOK_MATERIAL.texture());
		}
	}
}
