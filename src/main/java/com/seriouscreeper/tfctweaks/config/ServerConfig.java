package com.seriouscreeper.tfctweaks.config;

import com.seriouscreeper.tfctweaks.TFCTweaks;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Function;

public class ServerConfig {
    public final ForgeConfigSpec.BooleanValue mountsBypassSlowdown;
    public final ForgeConfigSpec.BooleanValue reverseExtractSmallVesselOrder;

    ServerConfig(ForgeConfigSpec.Builder innerBuilder) {
        Function<String, ForgeConfigSpec.Builder> builder = name -> innerBuilder.translation(TFCTweaks.MOD_ID + ".config.server." + name);

        innerBuilder.push("general");

        mountsBypassSlowdown = builder.apply("mountsBypassSlowdown").comment("Riding a mount prevents slowdown from grass").define("mountsBypassSlowdown", true);
        reverseExtractSmallVesselOrder = builder.apply("reverseExtractSmallVesselOrder").comment("If enabled, right clicking a small vessel in your inventory will take out the latest item, not the first item you placed into it").define("reverseExtractSmallVesselOrder", true);
    }
}
