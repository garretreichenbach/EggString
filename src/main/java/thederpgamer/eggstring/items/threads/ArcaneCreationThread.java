package thederpgamer.eggstring.items.threads;

import net.minecraft.world.item.Item;
import thederpgamer.eggstring.items.ItemManager;

/**
 * <Description>
 *
 * @author Garret Reichenbach
 * @version 1.0 - [05/13/2022]
 */
public class ArcaneCreationThread extends Item implements CreationThreadInterface {

	public ArcaneCreationThread() {
		super(new Properties().tab(ItemManager.INVENTORY_TAB));
	}
}
