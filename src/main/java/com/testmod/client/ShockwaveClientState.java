package com.testmod.client;

import net.minecraft.util.math.Vec3d;

public class ShockwaveClientState {
	public static final ShockwaveClientState INSTANCE = new ShockwaveClientState();

	public boolean active = false;
	public Vec3d pos;
	public float ticks = 0;

	public void start(Vec3d pos) {
		this.active = true;
		this.pos = pos;
		this.ticks = 0;
	}

	public void reset() {
		this.active = false;
		this.ticks = 0;
		this.pos = null;
	}
}