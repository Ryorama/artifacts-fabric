package artifacts.mixin.item.heliumflamingo.client;

import artifacts.Artifacts;
import artifacts.common.init.ModComponents;
import artifacts.common.init.ModItems;
import artifacts.common.item.curio.belt.HeliumFlamingoItem;
import artifacts.common.trinkets.TrinketsHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin extends GuiComponent {

	@Unique private final ResourceLocation FLAMINGO_ICONS_TEXTURE = Artifacts.id("textures/gui/icons.png");

	@Shadow private int screenHeight;
	@Shadow private int screenWidth;

	@Shadow protected abstract int getVisibleVehicleHeartRows(int i);

	@Shadow protected abstract LivingEntity getPlayerVehicleWithHealth();

	@Shadow protected abstract int getVehicleMaxHearts(LivingEntity livingEntity);

	@Shadow protected abstract Player getCameraPlayer();

	@Inject(method = "renderPlayerHealth", require = 0, at = @At(value = "TAIL"))
	private void renderFlamingoAir(PoseStack matrices, CallbackInfo ci) {
		Player player = this.getCameraPlayer();

		if (/*TODO: ModConfig.server.isCosmetic(HeliumFlamingoItem.this) ||*/ player == null || !TrinketsHelper.isEquipped(ModItems.HELIUM_FLAMINGO, player)) {
			return;
		}

		ModComponents.SWIM_ABILITIES.maybeGet(player).ifPresent(swimAbilities -> {
			int left = this.screenWidth / 2 + 91;
			int top = this.screenHeight + getStatusBarHeightOffset(player);

			int swimTime = Math.abs(swimAbilities.getSwimTime());
			if (swimTime == 0) {
				return;
			}
			int maxProgressTime = swimAbilities.getSwimTime() > 0 ? HeliumFlamingoItem.MAX_FLIGHT_TIME : HeliumFlamingoItem.RECHARGE_TIME;

			float progress = 1 - swimTime / (float) maxProgressTime;

			matrices.pushPose();
			RenderSystem.setShaderTexture(0, FLAMINGO_ICONS_TEXTURE);
			int full = Mth.ceil((progress - 2D / maxProgressTime) * 10);
			int partial = Mth.ceil(progress * 10) - full;

			for (int i = 0; i < full + partial; ++i) {
				// matrixStack, x, y, z, u, v, width, height, textureWidth, textureHeight
				GuiComponent.blit(matrices, left - i * 8 - 9, top, 0, (i < full ? 0 : 9), 0, 9, 9, 32, 16);
			}
			matrices.popPose();
		});
	}

	/**
	 * Calculate offset for our status bar height, taking rendering of other status bars into account
	 */
	@Unique
	private int getStatusBarHeightOffset(Player player) {
		int offset = -49; // Base offset

		LivingEntity livingEntity = this.getPlayerVehicleWithHealth();
		int maxHearts = this.getVehicleMaxHearts(livingEntity);

		// Offset if hunger is rendered
		if (maxHearts == 0) {
			offset -= 10;
		}

		// Ofset if mount health is rendered
		offset -= (this.getVisibleVehicleHeartRows(maxHearts) - 1) * 10;

		// Offset if air is rendered
		int maxAir = player.getMaxAirSupply();
		int playerAir = Math.min(player.getAirSupply(), maxAir);
		if (player.isEyeInFluid(FluidTags.WATER) || playerAir < maxAir) {
			offset -= 10;
		}

		return offset;
	}
}
