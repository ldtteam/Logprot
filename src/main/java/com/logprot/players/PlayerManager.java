package com.logprot.players;

import com.logprot.Logprot;
import com.logprot.Utils.BlockPosUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Iterator;
import java.util.Map;
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
    private WeakHashMap<Player, PlayerData> playerDataMap = new WeakHashMap<>();

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
        playerDataMap.put(player, new PlayerData(player, player.blockPosition(), Logprot.getConfig().getCommonConfig().invulTime));
        if (Logprot.getConfig().getCommonConfig().debugOutput)
        {
            Logprot.LOGGER.info("Player:" + player.getName().getString() + " now has login protection for " + Logprot.getConfig().getCommonConfig().invulTime + " ticks");
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

        final double maxDist = Math.pow(Logprot.getConfig().getCommonConfig().maxDist, 2);

        Iterator<Map.Entry<Player, PlayerData>> iterator = playerDataMap.entrySet().iterator();

        while (iterator.hasNext())
        {
            Map.Entry<Player, PlayerData> entry = iterator.next();

            if (!entry.getKey().isAlive())
            {
                iterator.remove();
                break;
            }

            if (BlockPosUtils.dist2DSQ(entry.getValue().loginPos, entry.getKey().blockPosition()) > maxDist)
            {
                if (Logprot.getConfig().getCommonConfig().debugOutput)
                {
                    Logprot.LOGGER.info("Player:" + entry.getKey().getName().getString() + " got his login protection removed due to moving");
                }

                entry.getKey().hurtTime = 0;
                iterator.remove();
                break;
            }

            if (entry.getValue().invulTime-- <= 0)
            {
                if (Logprot.getConfig().getCommonConfig().debugOutput)
                {
                    Logprot.LOGGER.info("Player:" + entry.getKey().getName().getString() + " got his login protection removed due to timeout");
                }

                entry.getKey().hurtTime = 0;
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
        if (playerDataMap.isEmpty())
        {
            return false;
        }

        return playerDataMap.containsKey(playerEntity);
    }

    public void onPlayerTeleport(final ServerPlayer player)
    {
        playerDataMap.put(player, new PlayerData(player, player.blockPosition(), Logprot.getConfig().getCommonConfig().invulTime));
        if (Logprot.getConfig().getCommonConfig().debugOutput)
        {
            Logprot.LOGGER.info(
              "Teleported player:" + player.getName().getString() + " now has login protection for " + Logprot.getConfig().getCommonConfig().invulTime + " ticks");
        }
    }
}
