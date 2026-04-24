package com.testmod.client;

import com.testmod.gun.GunItem;
import com.testmod.gun.GunStats;
import com.testmod.item.ModItems;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

public class ReloadKeybind {

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (Keybinds.RELOAD.wasPressed()) {
                reload(client);
            }
        });
    }

    private static void reload(MinecraftClient client) {
        var player = client.player;
        if (player == null) return;

        ItemStack stack = player.getMainHandStack();

        if (!(stack.getItem() instanceof GunItem gun)) return;

        int missing = gun.getStats().magazineSize - gun.getAmmo(stack);
        if (missing <= 0) return;

        int ammoFound = 0;

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack inv = player.getInventory().getStack(i);

            if (inv.getItem() == ModItems.BULLET_AMMO) {
                int take = Math.min(missing - ammoFound, inv.getCount());
                inv.decrement(take);
                ammoFound += take;

                if (ammoFound >= missing) break;
            }
        }

        gun.setAmmo(stack, gun.getAmmo(stack) + ammoFound);
    }
}
