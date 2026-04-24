package com.testmod.custom;

import com.testmod.custom.packets.ModPackets;
import com.testmod.custom.packets.ParticleSpawnPacket;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.awt.*;

public class LodestoneDI {

    public static class LodeStoneDI extends Item {

        public LodeStoneDI(Settings settings) {
            super(settings);
        }

        @Override
        public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

            ItemStack stack = player.getStackInHand(hand);

            if (world.isClient()) {
                return TypedActionResult.pass(stack);
            }

            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

            // =====================================================
            // RAYCAST FROM EYES (works in air, long range)
            // =====================================================
            double range = 120.0;

            Vec3d start = player.getCameraPosVec(1.0f);
            Vec3d direction = player.getRotationVec(1.0f);
            Vec3d end = start.add(direction.multiply(range));

            BlockHitResult hit = world.raycast(new RaycastContext(
                    start,
                    end,
                    RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.NONE,
                    player
            ));

            Vec3d target = (hit.getType() == HitResult.Type.MISS)
                    ? end
                    : hit.getPos();

            // =====================================================
            // SEND PARTICLE PACKET
            // =====================================================
            ParticleSpawnPacket packet = new ParticleSpawnPacket(
                    target,
                    new Color(255, 200, 25).getRGB(),
                    new Color(54, 54, 54).getRGB()
            );

            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            packet.toBytes(buf);

            ServerPlayNetworking.send(serverPlayer, ModPackets.PARTICLE_SPAWN_ID, buf);

            serverPlayer.sendMessage(Text.literal("Explosion targeted at look position"), true);

            return TypedActionResult.success(stack);
        }
    }
}