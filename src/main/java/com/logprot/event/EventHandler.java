package com.logprot.event;

import com.logprot.players.PlayerManager;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler
{
    /**
     * On Entity join world event.
     *
     * @param event the event.
     */
    @SubscribeEvent
    public static void onEntityAdded(final PlayerEvent.PlayerLoggedInEvent event)
    {
        PlayerManager.getInstance().onPlayerLogin(event.getPlayer());
    }
}
