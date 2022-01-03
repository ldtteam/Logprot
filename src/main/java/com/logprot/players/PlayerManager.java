package com.logprot.players;

import com.logprot.Logprot;
import com.logprot.Utils.BlockPosUtils;
import net.minecraft.entity.player.PlayerEntity;

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
    private WeakHashMap<PlayerEntity, PlayerData> playerDataMap = new WeakHashMap<>();

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
    public void onPlayerLogin(final PlayerEntity player)
    {
        playerDataMap.put(player, new PlayerData(player, player.getBlockPos(), Logprot.getConfig().getCommonConfig().invulTime));
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

        Iterator<Map.Entry<PlayerEntity, PlayerData>> iterator = playerDataMap.entrySet().iterator();

        while (iterator.hasNext())
        {
            Map.Entry<PlayerEntity, PlayerData> entry = iterator.next();

            if (BlockPosUtils.dist2DSQ(entry.getValue().loginPos, entry.getKey().getBlockPos()) > maxDist
                  || entry.getValue().invulTime-- <= 0)
            {
                if (Logprot.getConfig().getCommonConfig().debugOutput)
                {
                    Logprot.LOGGER.info("Player:" + entry.getKey().getName().getString() + " got his login protection removed");
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
    public boolean isPlayerImmune(final PlayerEntity playerEntity)
    {
        if (playerDataMap.isEmpty())
        {
            return false;
        }

        return playerDataMap.containsKey(playerEntity);
    }
}
