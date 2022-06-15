package com.logprot.mixin;

import com.logprot.players.PlayerManager;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class DamageMixin
{
    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    private void onDamage(final DamageSource source, final float amount, final CallbackInfoReturnable<Boolean> cir)
    {
        if (PlayerManager.getInstance().isPlayerImmune((PlayerEntity) (Object) this))
        {
            cir.setReturnValue(false);
        }
    }
}
