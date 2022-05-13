package thederpgamer.eggstring.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import thederpgamer.eggstring.EggString;
import thederpgamer.eggstring.items.eggs.PrizeEggBase;

/**
 * <Description>
 *
 * @author Garret Reichenbach
 * @version 1.0 - [05/13/2022]
 */
public class ItemManager {

	private static final String REG_PREFIX = "eggstring";
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
	public static final RegistryObject<Item> PRIZE_EGG_BASE = ITEMS.register("prize_egg_base", PrizeEggBase::new);

	public static void initialize() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
