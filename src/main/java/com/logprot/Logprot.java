package com.logprot;

import com.logprot.config.Configuration;
import com.logprot.event.EventHandler;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MOD_ID)
public class Logprot
{
    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * The config instance.
     */
    private static Configuration config;

    public Logprot()
    {
        ModLoadingContext.get()
          .registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> org.apache.commons.lang3.tuple.Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        config = new Configuration();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        Mod.EventBusSubscriber.Bus.FORGE.bus().get().register(EventHandler.class);
    }

    public static Configuration getConfig()
    {
        return config;
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("Shields up!");
    }
}
