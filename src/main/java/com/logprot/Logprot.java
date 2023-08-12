package com.logprot;

import com.cupboard.config.CupboardConfig;
import com.logprot.config.CommonConfiguration;
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
    public static CupboardConfig<CommonConfiguration> config = new CupboardConfig<>(Constants.MOD_ID, new CommonConfiguration());

    @Override
    public void onInitialize()
    {
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
