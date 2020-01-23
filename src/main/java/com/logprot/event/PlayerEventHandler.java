package com.logprot.event;

import com.logprot.players.PlayerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Eventhandler for the players which are currently invulnerable, removed when no players are invulnverable.
 */
public class PlayerEventHandler
{
    private static PlayerEventHandler instance = new PlayerEventHandler();

    public static PlayerEventHandler getInstance()
    {
        return instance;
    }

    @SubscribeEvent
    public void onWorldTick(@NotNull final TickEvent.WorldTickEvent event)
    {
        if (!event.world.isRemote)
        {
            PlayerManager.getInstance().updatePlayers();
        }
    }

    @SubscribeEvent
    public void onLivingDamageEvent(LivingDamageEvent event)
    {
        if (!(event.getEntity() instanceof PlayerEntity))
        {
            return;
        }

        if (PlayerManager.getInstance().isPlayerImmune((PlayerEntity) event.getEntity()))
        {
            event.setAmount(0);
        }
    }
}
