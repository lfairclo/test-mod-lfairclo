package com.testmod.custom.packets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;


import java.awt.*;


public class ParticleSpawnPacket {
    private final Vec3d position;
    private final int startingColor;
    private final int endingColor;

    public ParticleSpawnPacket(Vec3d position, int startingColor, int endingColor) {
        this.position = position;
        this.startingColor = startingColor;
        this.endingColor = endingColor;
    }

    public ParticleSpawnPacket(PacketByteBuf buf) {
        this.position = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.startingColor = buf.readInt();
        this.endingColor = buf.readInt();
    }

    public void toBytes(PacketByteBuf buf) {
        buf.writeDouble(position.x);
        buf.writeDouble(position.y);
        buf.writeDouble(position.z);
        buf.writeInt(startingColor);
        buf.writeInt(endingColor);
    }

    public void handle(MinecraftClient client) {
//        client.execute(() -> {
//            World level = client.world;
//            if (level != null) {
//                Color startColor = new Color(startingColor);
//                Color endColor = new Color(endingColor);
//                spawnExampleParticles(level, position, startColor, endColor);
//            }
//        });
        client.execute(() -> {
            // Only spawn particles if the world and player exist
            if (client.world == null || client.player == null) return;

            Color startColor = new Color(startingColor);
            Color endColor = new Color(endingColor);

            ExplosionParticle.spawnExampleParticles(client.world, position, startColor, endColor);
            ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(100).setIntensity(100f));
        });
    }


}