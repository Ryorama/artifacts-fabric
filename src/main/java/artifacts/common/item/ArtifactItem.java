package artifacts.common.item;

import artifacts.common.config.ModConfig;
import artifacts.common.init.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import net.minecraft.item.Item.Properties;

public abstract class ArtifactItem extends Item {

    public ArtifactItem(Properties properties) {
        super(properties.maxStackSize(1).group(ModItems.CREATIVE_TAB).rarity(Rarity.RARE).isImmuneToFire());
    }

    public ArtifactItem() {
        this(new Properties());
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if (ModConfig.server != null && ModConfig.server.items.containsKey(this)) {
            return ModConfig.server.items.get(this).durability.get();
        }
        return 0;
    }

    @Override
    public boolean isDamageable() {
        return getMaxDamage(ItemStack.EMPTY) > 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        if (ModConfig.server != null && ModConfig.server.isCosmetic(this)) {
            tooltip.add(new TranslationTextComponent("artifacts.cosmetic.tooltip").mergeStyle(TextFormatting.GRAY).mergeStyle(TextFormatting.ITALIC));
        } else if (ModConfig.client.showTooltips.get()) {
            tooltip.add(new TranslationTextComponent(getTranslationKey() + ".tooltip").mergeStyle(TextFormatting.GRAY));
        }
    }
}
