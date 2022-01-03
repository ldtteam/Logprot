package com.logprot.config;

import com.google.gson.JsonObject;
import com.logprot.Logprot;

public class CommonConfiguration
{
    public int     invulTime   = 100;
    public int     maxDist     = 4;
    public boolean debugOutput = false;

    public JsonObject serialize()
    {
        final JsonObject root = new JsonObject();

        final JsonObject entry = new JsonObject();
        entry.addProperty("desc:", "Time in ticks the logging player is invulnerable, 20 ticks is 1sec. Default is 5secs so 100 ticks");
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

        return root;
    }

    public void deserialize(JsonObject data)
    {
        if (data == null)
        {
            Logprot.LOGGER.error("Config file was empty!");
            return;
        }

        try
        {
            invulTime = data.get("invulTime").getAsJsonObject().get("invulTime").getAsInt();
            maxDist = data.get("maxDist").getAsJsonObject().get("maxDist").getAsInt();
            debugOutput = data.get("debugOutput").getAsJsonObject().get("debugOutput").getAsBoolean();
        }
        catch (Exception e)
        {
            Logprot.LOGGER.error("Could not parse config file", e);
        }
    }
}
