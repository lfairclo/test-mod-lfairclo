package com.testmod.particles;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

import java.awt.*;

import static com.ibm.icu.impl.ValidIdentifiers.Datatype.x;

public class ExplosionParticle {

    public static void spawnExplosion(World world, Vec3d pos, Color startingColor, Color endingColor) {

        Random random = Random.create();

        WorldParticleBuilder.create(ModParticles.EXPLOSION)
                .setScaleData(GenericParticleData.create(10f, 10f).setEasing(Easing.QUINTIC_OUT).build())
                .setTransparencyData(GenericParticleData.create(0.8f, 0f).build())

                .setLifetime(2400) // Longer lifetime to see the return trip
                .addTickActor(particle -> {
                    int switchPoint = 40;
                    int fadeWindow = 300; // ticks over which the fade happens

                    if (particle.getAge() >= switchPoint) {
//                        particle.pickSprite(1); // switch to second sprite

                        // how far through the fade are we? 0.0 → 1.0
                        float fadeProgress = Math.min(1f,
                                (float)(particle.getAge() - switchPoint) / fadeWindow
                        );

                        // define your two colours as RGB 0-255

                        int r2 = 255, g1 = 200,  b1 = 80; // phase 2 colour (yewo)
                        int r1 = 120,   g2 = 120, b2 = 120; // phase 1 colour (grey)


                        float r = (r1 + (r2 - r1) * fadeProgress) / 255f;
                        float g = (g1 + (g2 - g1) * fadeProgress) / 255f;
                        float b = (b1 + (b2 - b1) * fadeProgress) / 255f;

                        particle.setColor(r, g, b);
                    }
                })
                .setRandomOffset(3)
                .repeat(world,pos.x,pos.y,pos.z, 6)
                .addMotion(1-random.nextFloat(), 1-(random.nextFloat()-0.5), 0.2)
                .spawn(world, pos.x, pos.y, pos.z);

    }
    public static void spawnExplosionBackround(World world, Vec3d pos, Color startingColor, Color endingColor) {

        Random random = Random.create();

        WorldParticleBuilder.create(ModParticles.EXPLOSION)
                .setScaleData(GenericParticleData.create(75f, 75f).setEasing(Easing.QUINTIC_OUT).build())
                .setTransparencyData(GenericParticleData.create(0.8f, 0f).setEasing(Easing.QUARTIC_OUT).build())
                .setColorData(ColorParticleData.create(new Color(255, 200, 80)).build())
                .setLifetime(2400) // Longer lifetime to see the return trip

                // Initial burst: Move them out based on their cube position
                .setRandomOffset(5)

                .addTickActor(particle -> {
                    int switchPoint = 40;
                    int fadeWindow = 300; // ticks over which the fade happens

                    if (particle.getAge() >= switchPoint) {
//                        particle.pickSprite(1); // switch to second sprite

                        // how far through the fade are we? 0.0 → 1.0
                        float fadeProgress = Math.min(1f,
                                (float)(particle.getAge() - switchPoint) / fadeWindow
                        );

                        // define your two colours as RGB 0-255

                        int r2 = 255, g1 = 200,  b1 = 80; // phase 2 colour (yewo)
                        int r1 = 120,   g2 = 120, b2 = 120; // phase 1 colour (grey)


                        float r = (r1 + (r2 - r1) * fadeProgress) / 255f;
                        float g = (g1 + (g2 - g1) * fadeProgress) / 255f;
                        float b = (b1 + (b2 - b1) * fadeProgress) / 255f;

                        particle.setColor(r, g, b);
                    }
                })

                .spawn(world, pos.x, pos.y, pos.z);

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



