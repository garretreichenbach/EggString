package thederpgamer.eggstring.items.eggs;

import net.minecraft.world.item.EggItem;
import thederpgamer.eggstring.items.ItemManager;

/**
 * <Description>
 *
 * @author Garret Reichenbach
 * @version 1.0 - [05/13/2022]
 */
public class PrizeEggBase extends EggItem {

	public PrizeEggBase() {
		super(new Properties().tab(ItemManager.INVENTORY_TAB));
	}
}
