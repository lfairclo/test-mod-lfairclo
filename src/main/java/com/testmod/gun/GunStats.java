package com.testmod.gun;

public class GunStats {
    public final int damage;
    public final int fireRateTicks;
    public final int magazineSize;

    public GunStats( int damage, int fireRateTicks, int magazineSize) {
        this.damage = damage;
        this.fireRateTicks = fireRateTicks;
        this.magazineSize = magazineSize;
    }
}