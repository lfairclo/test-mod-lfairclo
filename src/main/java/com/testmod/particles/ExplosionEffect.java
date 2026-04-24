package com.testmod.particles;


import com.testmod.client.ShockwaveClientState;
import com.testmod.client.ShockwaveRenderer;
import com.testmod.shaders.ModShaders;
import com.testmod.util.TaskScheduler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.math.random.Random;

import net.minecraft.client.network.ClientPlayerEntity;

import java.awt.*;

import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

import static com.testmod.particles.FancyExplosion.spawnFlash;

public class ExplosionEffect {

    public static void CreateNewExplosion(ClientWorld world, Vec3d pos2, ClientPlayerEntity player){

        Vec3d pos = new Vec3d(pos2.x,  pos2.y+10, pos2.z);


//        ShockwaveClientState.INSTANCE.start(pos);
//        ModShaders.SHOCKWAVE.setUniformValue("Thickness", 10.0f);

        FancyExplosion.tuffExplosion(world,pos, 50);
        spawnFlash();
        ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(600).setIntensity(100f));
//        ExplosionParticle.spawnExplosion(world,pos, new Color(255, 200, 80), new Color(120, 120, 120));
//        ExplosionParticle.spawnExplosionBackround(world,pos, new Color(255, 200, 80), new Color(120, 120, 120));
//        TaskScheduler.schedule(() -> ExplosionParticle.spawnImplosion(world, pos, new Color(90, 90, 90), new Color(40, 40, 40)), 60);
//        //TaskScheduler.schedule(() -> ExplosionParticle.spawnBurst(world, pos, new Color(90, 90, 90), new Color(40, 40, 40)), 120);
    }
}