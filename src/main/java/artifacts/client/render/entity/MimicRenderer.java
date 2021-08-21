package artifacts.client.render.entity;

import artifacts.Artifacts;
import artifacts.client.render.entity.model.MimicModel;
import artifacts.common.entity.MimicEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class MimicRenderer extends MobRenderer<MimicEntity, MimicModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/mimic.png");

    public MimicRenderer(EntityRendererManager manager) {
        super(manager, new MimicModel(), 0.45F);
        addLayer(new MimicChestLayer(this));
    }

    @Override
    public void render(MimicEntity mimic, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        super.render(mimic, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getEntityTexture(MimicEntity entity) {
        return TEXTURE;
    }
}
