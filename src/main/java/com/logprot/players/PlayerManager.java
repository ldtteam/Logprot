package com.logprot.players;

import com.logprot.Logprot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

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

    private boolean debug = Logprot.getConfig().getCommon().debugOutput.get();

    /**
     * Stores the logged players, allows gc deletion
     */
    private WeakHashMap<PlayerEntity, BlockPos> distanceMap = new WeakHashMap<>();

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
        distanceMap.put(player, player.getPosition());
        player.hurtResistantTime = Logprot.getConfig().getCommon().invulTime.get();
        if (debug)
        {
            Logprot.LOGGER.info("Player:" + player.getName().getFormattedText() + " now has login protection for " + Logprot.getConfig().getCommon().invulTime.get() + " ticks");
        }
    }

    /**
     * Checks the players current distance with the starting position, and removes invuln if needed.
     */
    public void checkPlayerDistances()
    {
        if (distanceMap.isEmpty())
        {
            return;
        }

        final double maxDist = Math.pow(Logprot.getConfig().getCommon().maxDist.get(), 2);

        Iterator<Map.Entry<PlayerEntity, BlockPos>> iterator = distanceMap.entrySet().iterator();

        while (iterator.hasNext())
        {
            Map.Entry<PlayerEntity, BlockPos> entry = iterator.next();

            if (entry.getKey().getPosition().distanceSq(entry.getValue()) > maxDist)
            {
                if (debug)
                {
                    Logprot.LOGGER.info("Player:" + entry.getKey().getName().getFormattedText() + " got his login protection removed due to out of distance");
                }

                entry.getKey().hurtResistantTime = 0;
                iterator.remove();
            }
        }
    }
}
