package com.logprot.players;

import com.logprot.Logprot;
import com.logprot.Utils.BlockPosUtils;
import com.logprot.event.PlayerEventHandler;
import net.minecraft.entity.player.PlayerEntity;
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

    private boolean debug = Logprot.getConfig().getCommon().debugOutput.get();

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
        if (playerDataMap.containsKey(player.getUUID()))
        {
            return;
        }
        playerDataMap.put(player.getUUID(), new PlayerData(player, player.getPosition(), Logprot.getConfig().getCommon().invulTime.get()));
        if (debug)
        {
            Logprot.LOGGER.info("Player:" + player.hashCode() + " now has protection for " + Logprot.getConfig().getCommon().invulTime.get() + " ticks");
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

        Iterator<Map.Entry<UUID, PlayerData>> iterator = playerDataMap.entrySet().iterator();

        while (iterator.hasNext())
        {
            Map.Entry<UUID, PlayerData> entry = iterator.next();

            if (BlockPosUtils.dist2DSQ(entry.getValue().loginPos, entry.getValue().player.blockPosition()) > maxDist
                  || entry.getValue().invulTime-- <= 0)
            {
                if (debug)
                {
                    Logprot.LOGGER.info("Player:" + entry.getKey().hashCode() + " got his protection removed");
                }

                entry.getValue().player.hurtTime = 0;
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
    public boolean isPlayerImmune(final PlayerEntity playerEntity)
    {
        return playerDataMap.containsKey(playerEntity.getUUID());
    }
}
