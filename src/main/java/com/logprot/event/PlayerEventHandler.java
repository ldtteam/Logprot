package com.logprot.event;

import com.logprot.players.PlayerManager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
    public void onServerTick(final TickEvent.ServerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END)
        {
            PlayerManager.getInstance().updatePlayers();
        }
    }

    @SubscribeEvent
    public void onLivingDamageEvent(LivingDamageEvent event)
    {
        if (!(event.getEntity() instanceof Player))
        {
            return;
        }

        if (PlayerManager.getInstance().isPlayerImmune((Player) event.getEntity()))
        {
            event.setAmount(0);
        }
    }
}
