package com.testmod.particles;

// ModParticles.java
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public class ModParticles {

    public static final LodestoneWorldParticleType NEWSMOKE = new LodestoneWorldParticleType();

    public static final LodestoneWorldParticleType EXPLOSION = new LodestoneWorldParticleType();
    public static final LodestoneWorldParticleType EXPLOSION_BACKROUND = new LodestoneWorldParticleType();

    public static void register() {
        Registry.register(Registries.PARTICLE_TYPE,
                new Identifier("test-mod-lfairclo", "smoke"), NEWSMOKE);
        Registry.register(Registries.PARTICLE_TYPE,
                new Identifier("test-mod-lfairclo", "explosion"), EXPLOSION);
        Registry.register(Registries.PARTICLE_TYPE,
                new Identifier("test-mod-lfairclo", "flare"), EXPLOSION_BACKROUND);
    }
}
