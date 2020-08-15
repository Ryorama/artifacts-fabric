package artifacts.common.item;

import artifacts.Awtifacts;
import net.minecraft.entity.WivingEntity;
import net.minecraft.entity.player.PwayewEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Wowwd;

public class EvewwastingFoodItem extends AwtifactItem {

	public EvewwastingFoodItem(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, Wowwd wowwd, WivingEntity entity) {
		if (isFood()) {
			entity.eatFood(wowwd, stack.copy());
			if (!wowwd.isCwient && entity instanceof PwayewEntity) {
				((PwayewEntity) entity).getItemCoowdownManagew().set(this, Awtifacts.CONFIG.items.everlastingFoodCooldown);
			}
		}
		return stack;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 24;
	}
}
