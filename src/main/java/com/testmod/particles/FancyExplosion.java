package com.testmod.particles;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.joml.Vector3f;
import team.lodestar.lodestone.handlers.screenparticle.ScreenParticleHandler;
import team.lodestar.lodestone.registry.common.particle.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.ScreenParticleBuilder;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;

import java.awt.Color;

public class FancyExplosion {

    public static void tuffExplosion(ClientWorld world, Vec3d pos, int size) {

        int pow = Math.min(Math.max(size, 1), 20); // IMPORTANT clamp (prevents orb scaling bugs)
        Random random = world.random;

        Color smokeGray = new Color(140, 140, 140);

        // =========================================================
        // 1. FLAT GROUND SHOCK CLUSTERS (NOT SPHERICAL)
        // =========================================================
        int clusters = pow * 2;

        for (int c = 0; c < clusters; c++) {

            double angle = random.nextDouble() * Math.PI * 2;
            double radius = random.nextDouble() * pow * 0.8;

            double cx = pos.x + Math.cos(angle) * radius;
            double cz = pos.z + Math.sin(angle) * radius;

            // flattened Y (key fix)
            double cy = pos.y + random.nextDouble() * (pow * 0.15);

            // =====================================================
            // FLASH BURST (ground impact)
            // =====================================================
            WorldParticleBuilder.create(ModParticles.EXPLOSION_BACKROUND)
                    .setScaleData(GenericParticleData.create(0.8f, pow * 2.5f)
                            .setEasing(Easing.CIRC_OUT)
                            .build())
                    .setTransparencyData(GenericParticleData.create(0.0f, 0.6f)
                            .setEasing(Easing.SINE_OUT)
                            .build())
                    .enableNoClip()
                    .setLifetime(20)
                    .spawn(world, cx, cy, cz);

            // =====================================================
            // 2. MAIN BURST (low horizontal explosion)
            // =====================================================
            for (int i = 0; i < 8; i++) {

                float sizeF = random.nextFloat() * (3f * pow);

                Vector3f motion = new Vector3f(
                        (float)((random.nextDouble() - 0.5) * pow * 1.2),
                        (float)(random.nextDouble() * pow * 0.2), // reduced vertical lift
                        (float)((random.nextDouble() - 0.5) * pow * 1.2)
                );

                float spin = random.nextFloat() *
                        Math.signum(motion.x + 0.001f) / (4f * pow);

                // CORE FIRE BURST
                WorldParticleBuilder.create(ModParticles.EXPLOSION)
                        .setScaleData(GenericParticleData.create(0.3f, sizeF)
                                .setEasing(Easing.EXPO_OUT)
                                .build())
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.0f)
                                .setEasing(Easing.SINE_OUT)
                                .build())
                        .setSpinData(SpinParticleData.create(spin, spin / 120f)
                                .setEasing(Easing.SINE_IN)
                                .build())
                        .setLifetime(30 *5+ pow * 2)
                        .spawn(world, cx, cy, cz);

                // SECONDARY GROWTH WAVE
                WorldParticleBuilder.create(ModParticles.EXPLOSION)
                        .setScaleData(GenericParticleData.create(0.4f, sizeF * 2f)
                                .setEasing(Easing.EXPO_OUT)
                                .build())
                        .setTransparencyData(GenericParticleData.create(0.01f, 0.0f)
                                .setEasing(Easing.SINE_OUT)
                                .build())
                        .setSpinData(SpinParticleData.create(spin, spin / 120f)
                                .setEasing(Easing.SINE_IN)
                                .build())
                        .setLifetime(35 *5+ pow * 2)
                        .spawn(world, cx, cy, cz);

                // =====================================================
                // SMOKE (ground hugging, NOT white)
                // =====================================================
                WorldParticleBuilder.create(ModParticles.NEWSMOKE)
                        .setScaleData(GenericParticleData.create(0.6f, sizeF)
                                .setEasing(Easing.EXPO_OUT)
                                .build())
                        .setTransparencyData(GenericParticleData.create(0.9f, 0.0f)
                                .setEasing(Easing.SINE_OUT)
                                .build())
                        .setSpinData(SpinParticleData.create(spin, spin / 120f)
                                .setEasing(Easing.SINE_IN)
                                .build())
                        .setColorData(ColorParticleData.create(smokeGray, smokeGray).build())
                        .enableNoClip()
                        .setLifetime(500 *5+ pow * 4)
                        .spawn(world, cx, cy, cz);
            }
        }

        // =========================================================
        // 3. CENTRAL PRESSURE CORE (delayed vertical rise)
        // =========================================================
        for (int i = 0; i < pow / 2; i++) {

            double riseX = pos.x + (random.nextDouble() - 0.5) * pow * 0.3;
            double riseZ = pos.z + (random.nextDouble() - 0.5) * pow * 0.3;
            double riseY = pos.y + i * 0.5;

            WorldParticleBuilder.create(ModParticles.EXPLOSION)
                    .setScaleData(GenericParticleData.create(0.2f, pow * 3f)
                            .setEasing(Easing.EXPO_OUT)
                            .build())
                    .setTransparencyData(GenericParticleData.create(0.15f, 0.0f)
                            .setEasing(Easing.SINE_OUT)
                            .build())
                    .enableNoClip()
                    .setLifetime(30 *5+ i * 3)
                    .spawn(world, riseX, riseY, riseZ);
        }
    }
    public static void spawnFlash() {


        final ScreenParticleHolder PARTICLE_HOLDER = new ScreenParticleHolder();

        Color flash = new Color(255, 245, 200); // warm nuclear white

        ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.SMOKE, PARTICLE_HOLDER)
                // very fast alpha spike then collapse
                .setTransparencyData(
                        GenericParticleData.create(0.0f, 1.0f)
                                .setEasing(Easing.EXPO_OUT)
                                .build()
                )

                // huge full-screen scale burst
                .setScaleData(
                        GenericParticleData.create(0.0f, 3000.0f)
                                .setEasing(Easing.QUINTIC_OUT)
                                .build()
                )

                // color stays bright, slightly warm
                .setColorData(
                        ColorParticleData.create(flash, flash)
                                .setEasing(Easing.SINE_OUT)
                                .setCoefficient(1.2f)
                                .build()
                )

                // extremely short lifespan = flash effect
                .setLifetime(3)

                // slight randomness so it doesn't look like a flat overlay
                .setRandomOffset(0.02f)

                .spawn(0, 0);
        ScreenParticleHandler.renderParticles(PARTICLE_HOLDER);

    }
}



//package com.testmod.particles;
//
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.client.world.ClientWorld;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.util.math.random.Random;
//import net.minecraft.world.World;
//import org.joml.Vector3f;
//import team.lodestar.lodestone.registry.common.particle.LodestoneScreenParticleRegistry;
//import team.lodestar.lodestone.systems.easing.Easing;
//import team.lodestar.lodestone.systems.particle.builder.ScreenParticleBuilder;
//import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
//import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
//import team.lodestar.lodestone.systems.particle.data.GenericParticleDataBuilder;
//import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
//import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
//import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;
//
//import java.awt.*;
//
//
//public class FancyExplosion {
//
//    public static void TuffExplosion(ClientWorld world, Vec3d pos, int size) {
//
//        System.out.println("Explosion triggered");
//
//
//        int pow = size;
//
//
//
//        Random random = Random.create();
//        for(int i = 0; i < 10; ++i) {
//
//
//            Vector3f randomMotion = new Vector3f(
//                    (float) (random.nextDouble() / 5.0 * (double) pow) / 20.0F,
//                    pow <= 10 ? random.nextFloat() / 5.0F * (float) pow / 10.0F : 0.0F,
//                    (float) (random.nextDouble() / 5.0 * (double) pow) / 20.0F
//            );
//
//            float randomSpin = random.nextFloat() * Math.signum((float) randomMotion.z) / (float) (4 * pow);
//
//            WorldParticleBuilder.create(ModParticles.EXPLOSION)
//                    .setScaleData(GenericParticleData.create(0.5f, size)
//                            .setCoefficient(20.0f)
//                            .setEasing(Easing.EXPO_OUT)
//                            .build())
//                    .setTransparencyData(GenericParticleData.create(0.1f, 0f).setEasing(Easing.SINE_OUT).build())
//                    .setSpinData(SpinParticleData.create(randomSpin, randomSpin / 100.0F).setEasing(Easing.SINE_IN).build())
//                    .enableNoClip()
//                    .setLifetime(40 * pow + i * pow)
//                    .spawn(world, pos.x, pos.y, pos.z);
//
//            WorldParticleBuilder.create(ModParticles.EXPLOSION)
//                    .setScaleData(GenericParticleData.create(0.5f, size * 2.0F)
//                            .setCoefficient(20.0f)
//                            .setEasing(Easing.EXPO_OUT)
//                            .build())
//                    .setTransparencyData(GenericParticleData.create(0.005f, 0f).setEasing(Easing.SINE_OUT).build())
//                    .setSpinData(SpinParticleData.create(randomSpin, randomSpin / 100.0F).setEasing(Easing.SINE_IN).build())
//                    .setLifetime(40 * pow + i * pow)
//                    .spawn(world, pos.x, pos.y, pos.z);
//
//            WorldParticleBuilder.create(ModParticles.NEWSMOKE)
//                    .setScaleData(GenericParticleData.create(0.5f, size)
//                            .setCoefficient(20.0f)
//                            .setEasing(Easing.EXPO_OUT)
//                            .build())
//                    .setTransparencyData(GenericParticleData.create(1f, 0f).setEasing(Easing.SINE_OUT).build())
//                    .setSpinData(SpinParticleData.create(randomSpin, randomSpin / 100.0F).setEasing(Easing.SINE_IN).build())
//                    .enableNoClip()
//                    .setLifetime(600 * pow + i * pow * 3)
//                    .spawn(world, pos.x, pos.y, pos.z);
//
//            ScreenParticleHolder screenholder = new ScreenParticleHolder();
//            Color owFuckMyEyes = new Color(255, 200, 25);
//            float intensity = size / 5.0F;
//
//            ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.SMOKE, screenholder)
//                    .setTransparencyData(GenericParticleData.create(0.0f, 0.03f).setEasing(Easing.EXPO_OUT, Easing.EXPO_IN_OUT).build())
//                    .setScaleData(GenericParticleData.create(0.5f, size)
//                            .setCoefficient(1000.0F)
//                            .setEasing(Easing.SINE_IN)
//                            .build())
//                    .setColorData(ColorParticleData.create(owFuckMyEyes,owFuckMyEyes).setEasing(Easing.SINE_IN).setCoefficient(1.25f).build())
//                    .setLifetime((int)(8.0F * intensity))
//                    .setRandomOffset(0.05000000074505806)
//                    .spawn(0,0);
//
//        }
//    }
//}
//
