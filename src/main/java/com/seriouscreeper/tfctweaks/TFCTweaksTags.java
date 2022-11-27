package com.seriouscreeper.tfctweaks;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TFCTweaksTags {
    public static class Items
    {
        public static final TagKey<Item> WALKING_STICK_MAIN = create("walking_stick_main_hand");
        public static final TagKey<Item> WALKING_STICK_OFF = create("walking_stick_off_hand");

        private static TagKey<Item> create(String id) {
            return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(TFCTweaks.MOD_ID, id));
        }
    }

    public static class Blocks
    {
        public static final TagKey<Block> MOB_CAN_SPAWN = create("mob_can_spawn");

        private static TagKey<Block> create(String id) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(TFCTweaks.MOD_ID, id));
        }
    }
}
