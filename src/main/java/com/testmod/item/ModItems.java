package com.testmod.item;

import com.testmod.TestModLfairclo;
import com.testmod.custom.LodestoneDI;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    //public static final Item GOON_AXE = registerItem("goon_axe", new Item(new Item.Settings()));
    //public static final Item LDI = new LodestoneDI.LodeStoneDI(new Item.Settings());
    public static final Item GOON_AXE = new LodestoneDI.LodeStoneDI(new Item.Settings());



    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(TestModLfairclo.MOD_ID, name), item);
    }

    public static void registerModItems(){
        TestModLfairclo.LOGGER.info("Registering Mod Items for lfairclo test mod");

        ItemGroupEvents.modifyEntriesEvent((ItemGroups.INGREDIENTS)).register(entries -> {
           entries.add(GOON_AXE);
        });
    }

}
