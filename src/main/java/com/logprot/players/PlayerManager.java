package com.logprot.players;

import com.logprot.Logprot;
import com.logprot.Utils.BlockPosUtils;
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
        playerDataMap.put(player, new PlayerData(player, player.blockPosition(), System.currentTimeMillis() +  (Logprot.config.getCommonConfig().invulTime/20) * 1000L));
        if (Logprot.config.getCommonConfig().debugOutput)
        {
            Logprot.LOGGER.info("Player:" + player.getName().getString() + " now has login protection for " + Logprot.config.getCommonConfig().invulTime + " ticks");
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

        Iterator<Map.Entry<Player, PlayerData>> iterator = playerDataMap.entrySet().iterator();

        long currentTime = System.currentTimeMillis();

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
                if (Logprot.config.getCommonConfig().debugOutput)
                {
                    Logprot.LOGGER.info("Player:" + entry.getKey().getName().getString() + " got his login protection removed due to moving");
                }

                entry.getKey().hurtTime = 0;
                iterator.remove();
                break;
            }

            // Use timepoints instead?
            if (entry.getValue().invulTimePoint <= currentTime)
            {
                if (Logprot.config.getCommonConfig().debugOutput)
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

        updatePlayers();
        return playerDataMap.containsKey(playerEntity);
    }
}
