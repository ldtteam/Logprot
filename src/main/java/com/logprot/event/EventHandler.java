package com.logprot.event;

import com.logprot.players.PlayerManager;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class EventHandler
{
    /**
     * On Entity join world event.
     *
     * @param event the event.
     */
    @SubscribeEvent
    public static void onEntityAdded(@NotNull final PlayerEvent.PlayerLoggedInEvent event)
    {
        PlayerManager.getInstance().onPlayerLogin(event.getPlayer());
    }
}
