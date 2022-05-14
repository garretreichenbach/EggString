package thederpgamer.eggstring.items.eggs;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import thederpgamer.eggstring.items.ItemManager;
import thederpgamer.eggstring.world.EggGenSettings;
import thederpgamer.eggstring.world.WorldManager;

/**
 * <Description>
 *
 * @author Garret Reichenbach
 * @version 1.0 - [05/13/2022]
 */
public class SmallWorldEgg extends EggItem implements WorldEggInterface {

	public SmallWorldEgg() {
		super(new Properties().tab(ItemManager.INVENTORY_TAB));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if(!level.isClientSide) {
			ThrownEgg thrownegg = new ThrownEgg(level, player);
			thrownegg.setItem(itemstack);
			thrownegg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
			level.addFreshEntity(thrownegg);
			generate(itemstack, player, level);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		if(!player.getAbilities().instabuild) itemstack.shrink(1);
		return InteractionResultHolder.fail(itemstack);
	}

	@Override
	public void generate(ItemStack itemStack, Player player, Level level) {
		try {
			CompoundTag tag = itemStack.getTag();
			assert tag != null;
			String settings = tag.getString("gen_settings");
			WorldManager.generateEggWorld(player, level, WorldManager.SMALL, new EggGenSettings(settings));
		} catch(Exception exception) {
			exception.printStackTrace();
			player.displayClientMessage(new TranslatableComponent("messages.eggstring.invalid_item"), false);
		}
	}
}
