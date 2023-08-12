package com.logprot.players;

import com.logprot.Logprot;
import com.logprot.Utils.BlockPosUtils;
import com.logprot.event.PlayerEventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * Class managing the players which logged in
 */
public class PlayerManager
{
    /**
     * Singleton instance
     */
    private static PlayerManager instance;

    /**
     * Stores the logged players, allows gc deletion
     */
    private WeakHashMap<UUID, PlayerData> playerDataMap = new WeakHashMap<>();

    private PlayerManager() {}

    /**
     * Get the instance
     *
     * @return
     */
    public static PlayerManager getInstance()
    {
        if (PlayerManager.instance == null)
        {
            PlayerManager.instance = new PlayerManager();
            Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(PlayerEventHandler.getInstance());
        }
        return PlayerManager.instance;
    }

    /**
     * Add a player on login
     *
     * @param player
     */
    public void onPlayerLogin(final Player player)
    {
        if (playerDataMap.containsKey(player.getUUID()))
        {
            return;
        }
        playerDataMap.put(player.getUUID(), new PlayerData(player, player.blockPosition(), Logprot.config.getCommonConfig().invulTime));
        if (Logprot.config.getCommonConfig().debugOutput)
        {
            Logprot.LOGGER.info("Player:" + player.getDisplayName().getString() + " now has protection for " + Logprot.config.getCommonConfig().invulTime + " ticks");
        }
    }

    /**
     * Checks the players current distance with the starting position, and removes invuln if needed.
     */
    public void updatePlayers()
    {
        if (playerDataMap.isEmpty())
        {
            return;
        }

        final double maxDist = Math.pow(Logprot.config.getCommonConfig().maxDist, 2);

        Iterator<Map.Entry<UUID, PlayerData>> iterator = playerDataMap.entrySet().iterator();

        while (iterator.hasNext())
        {
            Map.Entry<UUID, PlayerData> entry = iterator.next();

            if (!entry.getValue().player.isAlive())
            {
                iterator.remove();
                break;
            }

            if (BlockPosUtils.dist2DSQ(entry.getValue().loginPos, entry.getValue().player.blockPosition()) > maxDist)
            {
                if (Logprot.config.getCommonConfig().debugOutput)
                {
                    Logprot.LOGGER.info("Player:" + entry.getValue().player.getName().getString() + " got his login protection removed due to moving");
                }

                entry.getValue().player.hurtTime = 0;
                iterator.remove();
                break;
            }

            if (entry.getValue().invulTime-- <= 0)
            {
                if (Logprot.config.getCommonConfig().debugOutput)
                {
                    Logprot.LOGGER.info("Player:" + entry.getValue().player.getName().getString() + " got his login protection removed due to timeout");
                }

                entry.getValue().player.hurtTime = 0;
                iterator.remove();
            }
        }
    }

    /**
     * Whether the player is immune
     *
     * @param playerEntity
     * @return
     */
    public boolean isPlayerImmune(final Player playerEntity)
    {
        return playerDataMap.containsKey(playerEntity.getUUID());
    }

    public void onPlayerTeleport(final ServerPlayer player)
    {
        playerDataMap.put(player.getUUID(),
          new PlayerData(player, new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()), Logprot.config.getCommonConfig().invulTime));
        if (Logprot.config.getCommonConfig().debugOutput)
        {
            Logprot.LOGGER.info("Teleported player:" + player.getName().getString() + " now has login protection for " + Logprot.config.getCommonConfig().invulTime + " ticks");
        }
    }
}
