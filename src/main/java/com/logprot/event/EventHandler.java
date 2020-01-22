package com.logprot.event;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
    public static void onEntityAdded(@NotNull final EntityJoinWorldEvent event)
    {
    }
}
