package com.logprot.event;

import com.logprot.Logprot;
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

    @SubscribeEvent
    public static void onChangeDim(final PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (Logprot.getConfig().getCommon().dimensionprotection.get())
        {
            PlayerManager.getInstance().onPlayerLogin(event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onChangeDim(final PlayerEvent.Clone event)
    {
        if (event.isWasDeath() && Logprot.getConfig().getCommon().respawnprotection.get())
        {
            PlayerManager.getInstance().onPlayerLogin(event.getPlayer());
        }
    }
}
