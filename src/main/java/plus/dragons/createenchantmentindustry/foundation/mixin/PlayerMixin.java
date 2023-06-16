package plus.dragons.createenchantmentindustry.foundation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import io.github.fabricators_of_create.porting_lib.fake_players.FakePlayer;
import io.github.fabricators_of_create.porting_lib.util.ToolActions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(Player.class)
public class PlayerMixin {
    @ModifyVariable(method = "attack",
            at = @At("STORE"), ordinal = 3)
    private boolean enableSweepingEdgeForDeployer(boolean value){
        var self = (Player)(Object) this;
        if(self instanceof FakePlayer fakePlayer){
            ItemStack itemstack = fakePlayer.getItemInHand(InteractionHand.MAIN_HAND);
            return itemstack.canPerformAction(ToolActions.SWORD_SWEEP);
        }
        return value;
    }
}
