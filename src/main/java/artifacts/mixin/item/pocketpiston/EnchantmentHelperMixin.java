package artifacts.mixin.item.pocketpiston;

import artifacts.common.init.ModItems;
import artifacts.common.trinkets.TrinketsHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

	@Inject(method = "getKnockbackBonus", at = @At("RETURN"), cancellable = true)
	private static void increaseKnockback(LivingEntity entity, CallbackInfoReturnable<Integer> info) {
		// Add 1 level of knockback with a minimum of 2
		if (TrinketsHelper.isEquipped(ModItems.POCKET_PISTON, entity)) {
			info.setReturnValue(Math.max(info.getReturnValueI() + 1, 2));
		}
	}
}
