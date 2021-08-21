package artifacts.common.item.curio.necklace;

import artifacts.common.config.ModConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public class ThornPendantItem extends PendantItem {

    @Override
    protected void applyEffect(LivingEntity target, LivingEntity attacker) {
        if (attacker.attackable()) {
            int minDamage = ModConfig.server.thornPendant.minDamage.get();
            int maxDamage = ModConfig.server.thornPendant.maxDamage.get();
            attacker.attackEntityFrom(DamageSource.causeThornsDamage(target), minDamage + target.getRNG().nextInt(maxDamage - minDamage + 1));
        }
    }
}
