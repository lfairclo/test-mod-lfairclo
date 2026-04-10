package com.testmod.particles;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.math.random.Random;

import java.awt.*;

import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;

public class ExplosionEffect {

    private final Random random = Random.create();

    private void explosionPhase(World world, Vec3d pos) {
        for (int i = 0; i < 4; i++) {

            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.5f, 6f)
                            .setEasing(Easing.SINE_OUT)
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

    private void implosionPhase(World world, Vec3d pos) {
        for (int i = 0; i < 3; i++) {

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

    private void finalBurstPhase(World world, Vec3d pos) {
        for (int i = 0; i < 6; i++) {

            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setScaleData(GenericParticleData.create(1f, 10f)
                            .setEasing(Easing.SINE_OUT)
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