package com.seriouscreeper.tfctweaks.mixin;

import com.seriouscreeper.tfctweaks.TFCTweaksTags;
import com.seriouscreeper.tfctweaks.config.TFCTweaksConfig;
import net.dries007.tfc.common.entities.livestock.horse.TFCDonkey;
import net.dries007.tfc.common.entities.livestock.horse.TFCHorse;
import net.dries007.tfc.common.entities.livestock.horse.TFCMule;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = Entity.class, remap = false)
public class MixinEntity {
    private Entity self() {
        return (Entity) (Object) this;
    }

    @Inject(method = "getBlockSpeedFactor", at = @At("RETURN"), cancellable = true)
    private void getBlockSpeedFactor(CallbackInfoReturnable<Float> callbackInfo) {
        if(callbackInfo.getReturnValue() < 1f) {
            if(TFCTweaksConfig.SERVER.mountsBypassSlowdown.get() && (self() instanceof TFCHorse || self() instanceof TFCDonkey || self() instanceof TFCMule)) {
                callbackInfo.setReturnValue(1f);
            } else if(self() instanceof Player player) {
                ItemStack handMain = player.getMainHandItem();
                ItemStack handOff = player.getOffhandItem();

                if(handMain.is(TFCTweaksTags.Items.WALKING_STICK_MAIN) || handOff.is(TFCTweaksTags.Items.WALKING_STICK_OFF)) {
                    float difference = 1f - callbackInfo.getReturnValueF();
                    callbackInfo.setReturnValue(1f - difference / 4);
                }
            }
        }
    }
}
