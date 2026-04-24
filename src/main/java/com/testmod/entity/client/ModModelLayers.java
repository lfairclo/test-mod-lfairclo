package com.testmod.entity.client;

import com.testmod.TestModLfairclo;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer BOMBER_PLANE =
            new EntityModelLayer(new Identifier(TestModLfairclo.MOD_ID, "bomber_plane"), "main");
}
