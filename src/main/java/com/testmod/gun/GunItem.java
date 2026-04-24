package com.testmod.gun;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class GunItem extends Item {

    private final GunStats stats;
    private static final String AMMO_TAG = "ammo";
    public int getAmmo(ItemStack stack) {
        return stack.getOrCreateNbt().getInt(AMMO_TAG);
    }

    public void setAmmo(ItemStack stack, int value) {
        stack.getOrCreateNbt().putInt(AMMO_TAG, value);
    }
    public GunStats getStats(){
        return stats;
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, net.minecraft.entity.Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player)) return;
        if (world.isClient) return;
        if (!selected) return;

        GunStats stats = this.stats;

        if (!player.isUsingItem()) return;

        // cooldown check
        if (player.getItemCooldownManager().isCoolingDown(this)) return;

        int ammo = getAmmo(stack);

        if (ammo <= 0) return;

        shootHitscan(world, player);

        setAmmo(stack, ammo - 1);

        player.getItemCooldownManager().set(this, stats.fireRateTicks);
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    public GunItem(Settings settings, GunStats stats) {
        super(settings);
        this.stats = stats;
    }

    private void shootHitscan(World world, PlayerEntity user) {

        Vec3d eyePos = user.getCameraPosVec(1.0f);
        Vec3d lookVec = user.getRotationVec(1.0f);
        Vec3d reachVec = eyePos.add(lookVec.multiply(200)); // range = 200 blocks

        RaycastContext context = new RaycastContext(
                eyePos,
                reachVec,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                user
        );

        HitResult hit = world.raycast(context);

        if (hit.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) hit;

            if (entityHit.getEntity() instanceof LivingEntity living) {
                living.damage(
                        world.getDamageSources().playerAttack(user),
                        stats.damage
                );
            }
        }
    }

}