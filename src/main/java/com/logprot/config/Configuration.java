package com.logprot.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.logprot.Constants;
import com.logprot.Logprot;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Configuration
{
    /**
     * Loaded everywhere, not synced
     */
    private final CommonConfiguration commonConfig = new CommonConfiguration();

    /**
     * Loaded clientside, not synced
     */
    // private final ClientConfiguration clientConfig;

    /**
     * Builds configuration tree.
     */
    public Configuration()
    {
    }

    public void load()
    {
        final Path configPath = FabricLoader.getInstance().getConfigDir().normalize().resolve(Constants.MOD_ID + ".json");
        final File config = configPath.toFile();

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!config.exists())
        {
            Logprot.LOGGER.warn("Config for "+Constants.MOD_ID+" not found, recreating default");
            try
            {
                final BufferedWriter writer = Files.newBufferedWriter(configPath);
                gson.toJson(commonConfig.serialize(), JsonObject.class, writer);
                writer.close();
            }
            catch (IOException e)
            {
                Logprot.LOGGER.error("Could not write config to:" + configPath, e);
            }
        }
        else
        {
            try
            {
                commonConfig.deserialize(gson.fromJson(Files.newBufferedReader(configPath), JsonObject.class));
            }
            catch (IOException e)
            {
                Logprot.LOGGER.error("Could not read config from:" + configPath, e);
            }
        }
    }

    public CommonConfiguration getCommonConfig()
    {
        return commonConfig;
    }
}
