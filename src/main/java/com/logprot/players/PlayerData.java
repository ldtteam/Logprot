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
    public int      invulTime;

    public PlayerData(final Player player, final BlockPos loginPos, final int invulTime)
    {
        this.player = player;
        this.loginPos = loginPos;
        this.invulTime = invulTime;
    }
}
