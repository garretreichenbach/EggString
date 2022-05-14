package thederpgamer.eggstring.items.eggs;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * <Description>
 *
 * @author Garret Reichenbach
 * @version 1.0 - [05/14/2022]
 */
public interface WorldEggInterface {
	void generate(ItemStack itemStack, Player player, Level level);
}
