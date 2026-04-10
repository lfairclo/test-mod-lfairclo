package com.testmod.custom.packets;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;

import java.awt.*;

public class ExplosionParticle {
    public static void spawnExampleParticles(World level, Vec3d pos, Color startingColor, Color endingColor) {
        Random random = Random.create();


        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setScaleData(GenericParticleData.create(10f, 0f,100f).setEasing(Easing.
                        SINE_IN).build())
                .setTransparencyData(GenericParticleData.create(2f).build())
                .setColorData(ColorParticleData.create(startingColor, endingColor).setCoefficient(1.4f).setEasing(Easing.
                        SINE_IN).build())
                .setLifetime(300)
                .addMotion(0, 0.01f, 0)
                .spawn(level, pos.x, pos.y +1, pos.z)
                .setRandomOffset(1)
                .repeat(level, pos.x,pos.y,pos.z, 12);

    }
}
