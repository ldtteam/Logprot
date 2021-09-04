package com.logprot.players;

import com.logprot.Logprot;
import com.logprot.Utils.BlockPosUtils;
import com.logprot.event.PlayerEventHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;

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
        playerDataMap.put(player, new PlayerData(player, player.blockPosition(), Logprot.getConfig().getCommon().invulTime.get()));
        if (debug)
        {
            Logprot.LOGGER.info("Player:" + player.getName().getString() + " now has login protection for " + Logprot.getConfig().getCommon().invulTime.get() + " ticks");
        }
        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(PlayerEventHandler.getInstance());
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

        final double maxDist = Math.pow(Logprot.getConfig().getCommon().maxDist.get(), 2);

        Iterator<Map.Entry<Player, PlayerData>> iterator = playerDataMap.entrySet().iterator();

        while (iterator.hasNext())
        {
            Map.Entry<Player, PlayerData> entry = iterator.next();

            if (BlockPosUtils.dist2DSQ(entry.getValue().loginPos, entry.getKey().blockPosition()) > maxDist
                  || entry.getValue().invulTime-- <= 0)
            {
                if (debug)
                {
                    Logprot.LOGGER.info("Player:" + entry.getKey().getName().getString() + " got his login protection removed");
                }

                entry.getKey().hurtTime = 0;
                iterator.remove();
            }
        }

        if (playerDataMap.isEmpty())
        {
            Mod.EventBusSubscriber.Bus.FORGE.bus().get().unregister(PlayerEventHandler.getInstance());
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
        return playerDataMap.containsKey(playerEntity);
    }
}
