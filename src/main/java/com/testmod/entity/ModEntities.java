package com.testmod.entity;

import com.testmod.TestModLfairclo;
import com.testmod.entity.custom.BomberPlaneEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<BomberPlaneEntity> BOMBER_PLANE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(TestModLfairclo.MOD_ID, "bomber_plane"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, BomberPlaneEntity::new).dimensions(EntityDimensions.fixed(1f,1f)).build());


}
