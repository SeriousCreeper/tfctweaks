package com.seriouscreeper.tfctweaks.events;

import com.seriouscreeper.tfctweaks.TFCTweaksTags;
import com.seriouscreeper.tfctweaks.config.TFCTweaksConfig;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;

public final class TFCTweaksEvents {
    public static void init() {
        final IEventBus bus = MinecraftForge.EVENT_BUS;

        if(TFCTweaksConfig.SERVER.preventMobsOnCertainBlocks.get()) {
            bus.addListener(TFCTweaksEvents::onLivingSpawnCheck);
        }
    }


    public static void onLivingSpawnCheck(LivingSpawnEvent.CheckSpawn event) {
        final LivingEntity entity = event.getEntityLiving();
        final LevelAccessor level = event.getWorld();
        final MobSpawnType spawn = event.getSpawnReason();

        // we only care about "natural" spawns
        if (spawn == MobSpawnType.NATURAL || spawn == MobSpawnType.CHUNK_GENERATION || spawn == MobSpawnType.REINFORCEMENT)
        {
            if (Helpers.isEntity(entity, TFCTags.Entities.VANILLA_MONSTERS))
            {
                if (TFCConfig.SERVER.enableVanillaMonsters.get())
                {
                    if (!TFCConfig.SERVER.enableVanillaMonstersOnSurface.get())
                    {
                        final BlockPos pos = entity.blockPosition();

                        if (level.getRawBrightness(pos, 0) != 0 || !level.getBlockState(pos.below()).is(TFCTweaksTags.Blocks.MOB_CAN_SPAWN)) {
                            event.setResult(Event.Result.DENY);
                        }
                    }
                }
                else
                {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
