package com.logprot.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfiguration
{
    public final ForgeConfigSpec.IntValue     invulTime;
    public final ForgeConfigSpec.IntValue     maxDist;
    public final ForgeConfigSpec.BooleanValue     debugOutput;

    protected CommonConfiguration(final ForgeConfigSpec.Builder builder)
    {
        builder.push("Login-settings");
        builder.comment("Time in ticks the logging player is invulnerable, 20 ticks = 1sec. Default is 5secs = 100 ticks");
        invulTime = builder.defineInRange("invulnerabilityTime", 100, 0, 50000);

        builder.comment("Max distance in blocks(2d) the invulnerability lasts, default: 4");
        maxDist = builder.defineInRange("maxDistance", 4, 1, 200);

        // Escapes the current category level
        builder.pop();

        builder.push("debug");
        builder.comment("Wether to enable debug log outputs, default: false");
        debugOutput = builder.define("debugLog", false);
    }
}
