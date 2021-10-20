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
        builder.comment("Time in ticks the logging player is invulnerable, 20 ticks = 1sec. Default is 50secs = 1000 ticks");
        invulTime = builder.defineInRange("invulnerabilityTime", 1000, 0, 50000);

        builder.comment("Max distance in blocks(2d) the invulnerability lasts, default: 10");
        maxDist = builder.defineInRange("maxDistance", 10, 1, 200);

        // Escapes the current category level
        builder.pop();

        builder.push("debug");
        builder.comment("Wether to enable debug log outputs, default: false");
        debugOutput = builder.define("debugLog", false);
    }
}
