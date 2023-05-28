package com.logprot;

import com.logprot.config.Configuration;
import com.logprot.players.PlayerManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
public class Logprot implements ModInitializer
{
    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * The config instance.
     */
    private static Configuration config;

    public static Configuration getConfig()
    {
        return config;
    }

    @Override
    public void onInitialize()
    {
        config = new Configuration();
        config.load();
        LOGGER.info("Shields up!");

        ServerEntityEvents.ENTITY_LOAD.register((e, w) ->
        {
            if (e instanceof ServerPlayer)
            {
                PlayerManager.getInstance().onPlayerLogin((Player) e);
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(world ->
        {
            PlayerManager.getInstance().updatePlayers();
        });
    }
}
