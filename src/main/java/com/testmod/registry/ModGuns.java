package com.testmod.registry;

import com.testmod.TestModLfairclo;
import com.testmod.gun.GunItem;
import com.testmod.gun.GunStats;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModGuns {

    // Example gun definition
    public static final GunItem PISTOL = new GunItem(
            new Item.Settings().maxCount(1),
            new GunStats(
                    6,   // damage
                    5,   // fire rate ticks
                    12
            )
    );


    public static void register() {
        Registry.register(Registries.ITEM,
                new Identifier(TestModLfairclo.MOD_ID, "pistol"),
                PISTOL
        );
    }
}