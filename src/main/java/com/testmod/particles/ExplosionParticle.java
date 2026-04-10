package com.testmod.particles;

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

    public static void spawnExplosion(World world, Vec3d pos, Color startingColor, Color endingColor) {

        for (int i = 0; i < 4; i++) {
            Random random = Random.create();

            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.5f, 6f)
                            .setEasing(Easing.CIRC_OUT)
                            .build())
                    .setTransparencyData(GenericParticleData.create(1f, 0f).build())
                    .setColorData(ColorParticleData.create(
                            new Color(255, 200, 80),
                            new Color(120, 120, 120)
                    ).build())
                    .setLifetime(20)
                    .addMotion(
                            (random.nextFloat() - 0.5) * 0.4,
                            random.nextFloat() * 0.4,
                            (random.nextFloat() - 0.5) * 0.4
                    )
                    .spawn(world, pos.x, pos.y, pos.z);
        }

    }
    public static void spawnImplosion(World world, Vec3d pos, Color startingColor, Color endingColor) {
        for (int i = 0; i < 3; i++) {

            Random random = Random.create();

            double dx = (random.nextFloat() - 0.5) * 2;
            double dy = (random.nextFloat() - 0.5) * 2;
            double dz = (random.nextFloat() - 0.5) * 2;

            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setScaleData(GenericParticleData.create(4f, 1f)
                            .setEasing(Easing.SINE_IN)
                            .build())
                    .setTransparencyData(GenericParticleData.create(1f, 0f).build())
                    .setColorData(ColorParticleData.create(
                            new Color(90, 90, 90),
                            new Color(40, 40, 40)
                    ).build())
                    .setLifetime(20)
                    .addMotion(-dx * 0.15, -dy * 0.15, -dz * 0.15)
                    .spawn(world,
                            pos.x + dx,
                            pos.y + dy,
                            pos.z + dz);
        }
    }

    public static void spawnBurst(World world, Vec3d pos, Color startingColor, Color endingColor) {
        for (int i = 0; i < 6; i++) {

            Random random = Random.create();

            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setScaleData(GenericParticleData.create(1f, 10f)
                            .setEasing(Easing.CIRC_OUT)
                            .build())
                    .setTransparencyData(GenericParticleData.create(1f, 0f).build())
                    .setColorData(ColorParticleData.create(
                            new Color(200, 200, 200),
                            new Color(60, 60, 60)
                    ).build())
                    .setLifetime(15)
                    .addMotion(
                            (random.nextFloat() - 0.5) * 0.6,
                            random.nextFloat() * 0.6,
                            (random.nextFloat() - 0.5) * 0.6
                    )
                    .spawn(world, pos.x, pos.y, pos.z);
        }
    }
}


//        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
//                .setScaleData(GenericParticleData.create(10f, 0f,100f).setEasing(Easing.
//                        SINE_IN).build())
//                .setTransparencyData(GenericParticleData.create(2f).build())
//                .setColorData(ColorParticleData.create(startingColor, endingColor).setCoefficient(1.4f).setEasing(Easing.
//                        SINE_IN).build())
//                .setLifetime(300)
//                .addMotion(0, 0.01f, 0)
//                .spawn(level, pos.x, pos.y +1, pos.z)
//                .setRandomOffset(1)
//                .repeat(level, pos.x,pos.y,pos.z, 12);



