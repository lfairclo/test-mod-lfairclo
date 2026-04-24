package com.testmod.entity.client;

import com.testmod.TestModLfairclo;
import com.testmod.entity.custom.BomberPlaneEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class BomberPlaneRenderer extends MobEntityRenderer<BomberPlaneEntity, BomberPlaneModel<BomberPlaneEntity>> {
    private static final Identifier TEXTURE = new Identifier(TestModLfairclo.MOD_ID, "textures/entity/bomber_plane.png");
    public BomberPlaneRenderer(EntityRendererFactory.Context context) {
        super(context, new BomberPlaneModel<>(context.getPart(ModModelLayers.BOMBER_PLANE)), 10);
    }

    @Override
    public Identifier getTexture(BomberPlaneEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(BomberPlaneEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {


        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
