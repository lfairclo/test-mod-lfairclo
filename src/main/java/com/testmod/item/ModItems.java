package com.testmod.item;

import com.testmod.TestModLfairclo;
import com.testmod.custom.LodestoneDI;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static Item GOON_AXE; // DO NOT initialize here

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(TestModLfairclo.MOD_ID, name), item);
    }

    public static void registerModItems(){
        TestModLfairclo.LOGGER.info("Registering Mod Items for lfairclo test mod");

        // Initialize the item here
        GOON_AXE = registerItem("goon_axe", new LodestoneDI.LodeStoneDI(new Item.Settings()));

//        // Add to creative tab
//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
//            entries.add(GOON_AXE);
//        });
    }
}