package com.logprot.players;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

/**
 * Holds the needed player data
 */
public class PlayerData
{
    public Player   player;
    public BlockPos loginPos;
    public long     invulTimePoint;

    public PlayerData(final Player player, final BlockPos loginPos, final long invulTimePoint)
    {
        this.player = player;
        this.loginPos = loginPos;
        this.invulTimePoint = invulTimePoint;
    }
}
