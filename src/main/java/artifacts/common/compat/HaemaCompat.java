package artifacts.common.compat;

import artifacts.common.item.UmbrellaItem;
import com.williambl.haema.api.VampireBurningEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class HaemaCompat implements CompatHandler {

	@Override
	public void run() {
		VampireBurningEvents.INSTANCE.getVETO().register(new VampireBurningEvents.Veto() {
			@NotNull
			@Override
			public TriState willVampireBurn(@NotNull LivingEntity player, @NotNull Level world) {
				return UmbrellaItem.isHeldUpInEitherHand(player) ? TriState.FALSE : TriState.DEFAULT;
			}

			@Override
			public int getPriority() {
				// This number is explained here: https://github.com/williambl/haema/blob/c7026d9ee18e9414b2a1e7eba8f033a68a2a44a8/src/main/kotlin/com/williambl/haema/api/VampireBurningEvents.kt#L41
				return 20;
			}
		});
	}

	@Override
	public String getModId() {
		return "haema";
	}
}
