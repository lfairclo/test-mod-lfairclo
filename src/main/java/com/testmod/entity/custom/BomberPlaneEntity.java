package com.testmod.entity.custom;

import com.testmod.entity.ModEntities;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import dev.onyxstudios.cca.api.v3.component.sync.ComponentPacketWriter;
import dev.onyxstudios.cca.api.v3.component.sync.PlayerSyncPredicate;
import io.github.fabricators_of_create.porting_lib.entity.ITeleporter;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.EnumSet;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class BomberPlaneEntity extends FlyingEntity {
    
    public BomberPlaneEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);

        this.moveControl = new PlaneMoveControl(this);
    }

    public BomberPlaneEntity createChild(ServerWorld world, PassiveEntity entity){
        return ModEntities.BOMBER_PLANE.create(world);
    }

    private static final TrackedData<Boolean> SHOOTING = DataTracker.registerData(BomberPlaneEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int fireballStrength = 1;

    @Override
    protected void initGoals() {
        this.goalSelector.add(5, new BomberPlaneEntity.FlyRandomlyGoal(this));
        this.goalSelector.add(7, new BomberPlaneEntity.LookAtTargetGoal(this));
        this.goalSelector.add(7, new BomberPlaneEntity.ShootFireballGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, entity -> Math.abs(entity.getY() - this.getY()) <= 4.0));
    }


    public boolean isShooting() {
        return this.dataTracker.get(SHOOTING);
    }

    public void setShooting(boolean shooting) {
        this.dataTracker.set(SHOOTING, shooting);
    }

    public int getFireballStrength() {
        return this.fireballStrength;
    }

    /**
     * {@return whether {@code damageSource} is caused by a player's fireball}
     *
     * <p>This returns {@code true} for ghast fireballs reflected by a player,
     * since the attacker is set as the player in that case.
     */
    private static boolean isFireballFromPlayer(DamageSource damageSource) {
        return damageSource.getSource() instanceof FireballEntity && damageSource.getAttacker() instanceof PlayerEntity;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return !isFireballFromPlayer(damageSource) && super.isInvulnerableTo(damageSource);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SHOOTING, false);
    }

    public static DefaultAttributeContainer.Builder CreateBomberPlaneAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BOMBER_PLANE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BOMBER_PLANE_KILL;
    }

    @Override
    protected float getSoundVolume() {
        return 5.0F;
    }

    public static boolean canSpawn(EntityType<BomberPlaneEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return false;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("ExplosionPower", (byte)this.fireballStrength);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("ExplosionPower", NbtElement.NUMBER_TYPE)) {
            this.fireballStrength = nbt.getByte("ExplosionPower");
        }
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 2.6F;
    }

    static class FlyRandomlyGoal extends Goal {
        private final BomberPlaneEntity bomberplane;

        public FlyRandomlyGoal(BomberPlaneEntity bomberplane) {
            this.bomberplane = bomberplane;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            MoveControl moveControl = this.bomberplane.getMoveControl();
            if (!moveControl.isMoving()) {
                return true;
            } else {
                double d = moveControl.getTargetX() - this.bomberplane.getX();
                double e = moveControl.getTargetY() - this.bomberplane.getY();
                double f = moveControl.getTargetZ() - this.bomberplane.getZ();
                double g = d * d + e * e + f * f;
                return g < 1.0 || g > 3600.0;
            }
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void start() {
            Random random = this.bomberplane.getRandom();
            double d = this.bomberplane.getX() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
            double e = this.bomberplane.getY() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
            double f = this.bomberplane.getZ() + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
            this.bomberplane.getMoveControl().moveTo(d, e, f, 1.0);
        }
    }

    static class PlaneMoveControl extends MoveControl {
        private final BomberPlaneEntity bomberplane;
        private int collisionCheckCooldown;

        public PlaneMoveControl(BomberPlaneEntity bomberplane) {
            super(bomberplane);
            this.bomberplane = bomberplane;
        }

        @Override
        public void tick() {
            if (this.state == MoveControl.State.MOVE_TO) {
                if (this.collisionCheckCooldown-- <= 0) {
                    this.collisionCheckCooldown = this.collisionCheckCooldown + this.bomberplane.getRandom().nextInt(5) + 2;
                    Vec3d vec3d = new Vec3d(this.targetX - this.bomberplane.getX(), this.targetY - this.bomberplane.getY(), this.targetZ - this.bomberplane.getZ());
                    double d = vec3d.length();
                    vec3d = vec3d.normalize();
                    if (this.willCollide(vec3d, MathHelper.ceil(d))) {
                        this.bomberplane.setVelocity(this.bomberplane.getVelocity().add(vec3d.multiply(0.1)));
                    } else {
                        this.state = MoveControl.State.WAIT;
                    }
                }
            }
        }

        private boolean willCollide(Vec3d direction, int steps) {
            Box box = this.bomberplane.getBoundingBox();

            for (int i = 1; i < steps; i++) {
                box = box.offset(direction);
                if (!this.bomberplane.getWorld().isSpaceEmpty(this.bomberplane, box)) {
                    return false;
                }
            }

            return true;
        }
    }

    static class LookAtTargetGoal extends Goal {
        private final BomberPlaneEntity bomberplane;

        public LookAtTargetGoal(BomberPlaneEntity bomberplane) {
            this.bomberplane = bomberplane;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.bomberplane.getTarget() == null) {
                Vec3d vec3d = this.bomberplane.getVelocity();
                this.bomberplane.setYaw(-((float)MathHelper.atan2(vec3d.x, vec3d.z)) * (180.0F / (float)Math.PI));
                this.bomberplane.bodyYaw = this.bomberplane.getYaw();
            } else {
                LivingEntity livingEntity = this.bomberplane.getTarget();
                double d = 64.0;
                if (livingEntity.squaredDistanceTo(this.bomberplane) < 4096.0) {
                    double e = livingEntity.getX() - this.bomberplane.getX();
                    double f = livingEntity.getZ() - this.bomberplane.getZ();
                    this.bomberplane.setYaw(-((float)MathHelper.atan2(e, f)) * (180.0F / (float)Math.PI));
                    this.bomberplane.bodyYaw = this.bomberplane.getYaw();
                }
            }
        }
    }

    static class ShootFireballGoal extends Goal {
        private final BomberPlaneEntity bomberplane;
        public int cooldown;

        public ShootFireballGoal(BomberPlaneEntity bomberplane) {
            this.bomberplane = bomberplane;
        }

        @Override
        public boolean canStart() {
            return this.bomberplane.getTarget() != null;
        }

        @Override
        public void start() {
            this.cooldown = 0;
        }

        @Override
        public void stop() {
            this.bomberplane.setShooting(false);
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.bomberplane.getTarget();
            if (livingEntity != null) {
                double d = 64.0;
                if (livingEntity.squaredDistanceTo(this.bomberplane) < 4096.0 && this.bomberplane.canSee(livingEntity)) {
                    World world = this.bomberplane.getWorld();
                    this.cooldown++;
                    if (this.cooldown == 10 && !this.bomberplane.isSilent()) {
                        world.syncWorldEvent(null, WorldEvents.GHAST_WARNS, this.bomberplane.getBlockPos(), 0);
                    }

                    if (this.cooldown == 20) {
                        double e = 4.0;
                        Vec3d vec3d = this.bomberplane.getRotationVec(1.0F);
                        double f = livingEntity.getX() - (this.bomberplane.getX() + vec3d.x * 4.0);
                        double g = livingEntity.getBodyY(0.5) - (0.5 + this.bomberplane.getBodyY(0.5));
                        double h = livingEntity.getZ() - (this.bomberplane.getZ() + vec3d.z * 4.0);
                        if (!this.bomberplane.isSilent()) {
                            world.syncWorldEvent(null, WorldEvents.GHAST_SHOOTS, this.bomberplane.getBlockPos(), 0);
                        }

                        FireballEntity fireballEntity = new FireballEntity(world, this.bomberplane, f, g, h, this.bomberplane.getFireballStrength());
                        fireballEntity.setPosition(this.bomberplane.getX() + vec3d.x * 4.0, this.bomberplane.getBodyY(0.5) + 0.5, fireballEntity.getZ() + vec3d.z * 4.0);
                        world.spawnEntity(fireballEntity);
                        this.cooldown = -40;
                    }
                } else if (this.cooldown > 0) {
                    this.cooldown--;
                }

                this.bomberplane.setShooting(this.cooldown > 10);
            }
        }
    }
}
