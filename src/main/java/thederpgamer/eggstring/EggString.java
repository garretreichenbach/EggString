package thederpgamer.eggstring;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thederpgamer.eggstring.items.ItemManager;

import java.util.stream.Collectors;

@Mod("EggString")
public class EggString {

	public static final String MODID = "EggString";
	private static final Logger LOGGER = LogManager.getLogger();

	public EggString() {
		//Setup
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		ItemManager.initialize();

		//Loading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);

		//Processing
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
		// some example code to dispatch IMC to another mod
		InterModComms.sendTo("EggString", "helloworld", () -> {
			LOGGER.info("Hello world from the MDK");
			return "Hello world";
		});
	}

	private void processIMC(final InterModProcessEvent event) {
		// some example code to receive and process InterModComms from other mods
		LOGGER.info("Got IMC {}", event.getIMCStream().
				map(m -> m.messageSupplier().get()).
				collect(Collectors.toList()));
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
		// do something when the server starts
		LOGGER.info("HELLO from server starting");
	}
}
