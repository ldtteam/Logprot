package com.logprot.config;

import com.cupboard.config.ICommonConfig;
import com.google.gson.JsonObject;

public class CommonConfiguration implements ICommonConfig
{
    public int     invulTime           = 1000;
    public int     maxDist             = 4;
    public boolean debugOutput         = false;
    public boolean dimensionprotection = true;
    public boolean respawnprotection   = true;

    public JsonObject serialize()
    {
        final JsonObject root = new JsonObject();

        final JsonObject entry = new JsonObject();
        entry.addProperty("desc:", "Time in ticks the logging player is invulnerable, 20 ticks is 1sec. Default is 50secs so 1000 ticks");
        entry.addProperty("invulTime", invulTime);
        root.add("invulTime", entry);

        final JsonObject entry2 = new JsonObject();
        entry2.addProperty("desc:", "Max distance in blocks(2d) the invulnerability lasts, default: 4");
        entry2.addProperty("maxDist", maxDist);
        root.add("maxDist", entry2);

        final JsonObject entry3 = new JsonObject();
        entry3.addProperty("desc:",
          "Whether to enable debug log outputs, default: false");
        entry3.addProperty("debugOutput", debugOutput);
        root.add("debugOutput", entry3);

        final JsonObject entry4 = new JsonObject();
        entry4.addProperty("desc:",
          "Enables the protection for dimension changes too, default: true");
        entry4.addProperty("dimensionprotection", dimensionprotection);
        root.add("dimensionprotection", entry4);

        final JsonObject entry5 = new JsonObject();
        entry5.addProperty("desc:",
          "Enables the protection for respawning too, default: true");
        entry5.addProperty("respawnprotection", respawnprotection);
        root.add("respawnprotection", entry5);

        return root;
    }

    public void deserialize(JsonObject data)
    {
        invulTime = data.get("invulTime").getAsJsonObject().get("invulTime").getAsInt();
        maxDist = data.get("maxDist").getAsJsonObject().get("maxDist").getAsInt();
        debugOutput = data.get("debugOutput").getAsJsonObject().get("debugOutput").getAsBoolean();
        dimensionprotection = data.get("dimensionprotection").getAsJsonObject().get("dimensionprotection").getAsBoolean();
        respawnprotection = data.get("respawnprotection").getAsJsonObject().get("respawnprotection").getAsBoolean();
    }
}
