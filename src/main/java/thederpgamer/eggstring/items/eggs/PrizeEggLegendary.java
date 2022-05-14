package thederpgamer.eggstring.items.eggs;

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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import thederpgamer.eggstring.items.ItemManager;

/**
 * <Description>
 *
 * @author Garret Reichenbach
 * @version 1.0 - [05/13/2022]
 */
public class PrizeEggLegendary extends EggItem implements PrizeEggInterface {

	public PrizeEggLegendary() {
		super(new Properties().tab(ItemManager.INVENTORY_TAB));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if(!level.isClientSide) {
			ThrownEgg thrownegg = new ThrownEgg(level, player) {
				@Override
				protected void onHit(HitResult hitResult) {
					HitResult.Type hitresult$type = hitResult.getType();
					if(hitresult$type == HitResult.Type.ENTITY) this.onHitEntity((EntityHitResult) hitResult);
					else if(hitresult$type == HitResult.Type.BLOCK) this.onHitBlock((BlockHitResult) hitResult);
					if(hitresult$type != HitResult.Type.MISS) this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
					this.level.broadcastEntityEvent(this, (byte)3);
					this.discard();
				}
			};
			thrownegg.setItem(itemstack);
			thrownegg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
			level.addFreshEntity(thrownegg);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		if(!player.getAbilities().instabuild) itemstack.shrink(1);
		spawnPrize(level, player);
		return InteractionResultHolder.fail(itemstack);
	}

	@Override
	public void spawnPrize(Level level, Player player) {

	}
}
