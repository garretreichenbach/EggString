package thederpgamer.eggstring.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import thederpgamer.eggstring.EggString;
import thederpgamer.eggstring.items.eggs.*;
import thederpgamer.eggstring.items.threads.*;

/**
 * <Description>
 *
 * @author Garret Reichenbach
 * @version 1.0 - [05/13/2022]
 */
public class ItemManager {
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EggString.MODID);
	public static final CreativeModeTab INVENTORY_TAB = new CreativeModeTab(EggString.MODID) {
		@Override
		public ItemStack makeIcon() {
			return MOD_ICON.get().getDefaultInstance();
		}

		@Override
		public boolean hasSearchBar() {
			return true;
		}
	};

	public static final RegistryObject<Item> MOD_ICON = ITEMS.register("mod_icon", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> PRIZE_EGG_COMMON = ITEMS.register("prize_egg_common", PrizeEggCommon::new);
	public static final RegistryObject<Item> PRIZE_EGG_RARE = ITEMS.register("prize_egg_rare", PrizeEggRare::new);
	public static final RegistryObject<Item> PRIZE_EGG_LEGENDARY = ITEMS.register("prize_egg_legendary", PrizeEggLegendary::new);

	public static final RegistryObject<Item> SMALL_WORLD_EGG = ITEMS.register("small_world_egg", SmallWorldEgg::new);

	public static final RegistryObject<Item> FIRE_CREATION_THREAD = ITEMS.register("fire_creation_thread", FireCreationThread::new);
	public static final RegistryObject<Item> WATER_CREATION_THREAD = ITEMS.register("water_creation_thread", WaterCreationThread::new);
	public static final RegistryObject<Item> EARTH_CREATION_THREAD = ITEMS.register("earth_creation_thread", EarthCreationThread::new);
	public static final RegistryObject<Item> ARCANE_CREATION_THREAD = ITEMS.register("arcane_creation_thread", ArcaneCreationThread::new);
	public static final RegistryObject<Item> STEEL_CREATION_THREAD = ITEMS.register("steel_creation_thread", SteelCreationThread::new);
	public static final RegistryObject<Item> AIR_CREATION_THREAD = ITEMS.register("air_creation_thread", AirCreationThread::new);
	public static final RegistryObject<Item> HOLY_CREATION_THREAD = ITEMS.register("holy_creation_thread", HolyCreationThread::new);

	public static void initialize() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
