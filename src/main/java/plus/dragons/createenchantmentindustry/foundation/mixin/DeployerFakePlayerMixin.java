package plus.dragons.createenchantmentindustry.foundation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;


@Mixin(value = DeployerFakePlayer.class, remap = false)
public class DeployerFakePlayerMixin {

    @Inject(method = "deployerKillsDoNotSpawnXP", at = @At(value = "HEAD"), cancellable = true)
    private static void deployerKillsSpawnXpNuggets(int i, Player player, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
		if (player instanceof DeployerFakePlayer){
			cir.setReturnValue(i);
		}
    }

}
