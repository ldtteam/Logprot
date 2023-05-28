package com.logprot.mixin;

import com.logprot.players.PlayerManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class DamageMixin
{
    @Inject(at = @At("HEAD"), method = "hurt", cancellable = true)
    private void onDamage(final DamageSource source, final float amount, final CallbackInfoReturnable<Boolean> cir)
    {
        if (PlayerManager.getInstance().isPlayerImmune((Player) (Object) this))
        {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("RETURN"), method = "changeDimension")
    private void onTP(
      final ServerLevel destination,
      final CallbackInfoReturnable<Entity> cir)
    {
        PlayerManager.getInstance().onPlayerTeleport((ServerPlayer) (Object) this);
    }
}
