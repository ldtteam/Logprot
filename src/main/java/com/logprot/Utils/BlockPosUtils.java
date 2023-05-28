package com.logprot.Utils;

import net.minecraft.core.BlockPos;

public abstract class BlockPosUtils
{
    /**
     * Calculates the squared distance between two blocks, 2D ignoring height
     *
     * @param a
     * @param b
     * @return
     */
    public static int dist2DSQ(final BlockPos a, final BlockPos b)
    {
        final int xDiff = a.getX() - b.getX();
        final int zDiff = a.getZ() - b.getZ();

        return xDiff * xDiff + zDiff * zDiff;
    }
}
