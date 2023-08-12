package com.logprot.mixin;

import com.logprot.Logprot;
import com.logprot.players.PlayerManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getActiveEffects()Ljava/util/Collection;"), method = "changeDimension")
    private void onChangeDim(
      final ServerLevel destination,
      final CallbackInfoReturnable<Entity> cir)
    {
        if (Logprot.config.getCommonConfig().dimensionprotection)
        {
            PlayerManager.getInstance().onPlayerLogin((ServerPlayer) (Object) this);
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;sendAllPlayerInfo(Lnet/minecraft/server/level/ServerPlayer;)V"), method = "teleportTo(Lnet/minecraft/server/level/ServerLevel;DDDFF)V")
    private void onTP(
      final ServerLevel serverLevel, final double d, final double e, final double f, final float g, final float h, final CallbackInfo ci)
    {
        if (Logprot.config.getCommonConfig().dimensionprotection)
        {
            PlayerManager.getInstance().onPlayerLogin((ServerPlayer) (Object) this);
        }
    }

    @Inject(at = @At(value = "RETURN"), method = "restoreFrom")
    private void onRespawn(
      final ServerPlayer serverPlayer,
      final boolean bl,
      final CallbackInfo ci)
    {
        if (Logprot.config.getCommonConfig().dimensionprotection)
        {
            PlayerManager.getInstance().onPlayerLogin((ServerPlayer) (Object) this);
        }
    }
}
