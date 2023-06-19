package plus.dragons.createenchantmentindustry.foundation.mixin.client;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter.BlazeEnchanterRenderer;

@Mixin(TextureAtlas.class)
public abstract class TextureAtlasMixin extends AbstractTexture implements Tickable {
	@ModifyVariable(method = "prepareToStitch", at = @At("STORE"), ordinal = 0)
	private Set<ResourceLocation> textureStitch(Set<ResourceLocation> set) {
		if (((TextureAtlas)(Object)this).location().equals(InventoryMenu.BLOCK_ATLAS)) {
			set.add(BlazeEnchanterRenderer.BOOK_MATERIAL.texture());
		}
		return set;
	}
}
