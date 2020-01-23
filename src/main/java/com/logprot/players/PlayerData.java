package com.logprot.players;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

/**
 * Holds the needed player data
 */
public class PlayerData
{
    public PlayerEntity player;
    public BlockPos     loginPos;
    public int          invulTime;

    public PlayerData(final PlayerEntity player, final BlockPos loginPos, final int invulTime)
    {
        this.player = player;
        this.loginPos = loginPos;
        this.invulTime = invulTime;
    }
}
