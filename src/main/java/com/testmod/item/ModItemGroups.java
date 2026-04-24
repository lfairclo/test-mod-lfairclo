package com.testmod.item;

import com.testmod.TestModLfairclo;
import com.testmod.block.ModBlocks;
import com.testmod.registry.ModGuns;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup TESTMODLFAIRCLOTAB = Registry.register(Registries.ITEM_GROUP, new Identifier(TestModLfairclo.MOD_ID, "testmodtab"), FabricItemGroup.builder()
            .displayName(Text.translatable("itemgroup.testmodlfairclo"))
            .icon(() -> new ItemStack(ModItems.GOON_AXE)).entries((displayContext, entries) -> {
                entries.add(ModBlocks.TESTBLOCK);
                entries.add(ModItems.GOON_AXE);
                entries.add(ModGuns.PISTOL);
            }).build());
    public static void registerItemGroups(){
        TestModLfairclo.LOGGER.info("Item Groups Registered");
    }
}
