package com.seriouscreeper.tfctweaks.mixin;

import com.seriouscreeper.tfctweaks.config.TFCTweaksConfig;
import net.dries007.tfc.common.capabilities.VesselLike;
import net.dries007.tfc.common.items.VesselItem;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = VesselItem.class, remap = false)
public class MixinVesselItem {
    @Inject(method = "overrideOtherStackedOnMe", at = @At("HEAD"), cancellable = true)
    public void overrideOtherStackedOnMe(ItemStack stack, ItemStack carried, Slot slot, ClickAction action, Player player, SlotAccess carriedSlot, CallbackInfoReturnable<Boolean> cir)
    {
        if(!TFCTweaksConfig.SERVER.reverseExtractSmallVesselOrder.get()) {
            return;
        }

        final VesselLike vessel = VesselLike.get(stack);
        if (vessel != null && TFCConfig.SERVER.enableSmallVesselInventoryInteraction.get() && vessel.mode() == VesselLike.Mode.INVENTORY && vessel.getTemperature() == 0f && !player.isCreative() && action == ClickAction.SECONDARY)
        {
            for (int i = VesselItem.SLOTS - 1; i >= 0; i--)
            {
                final ItemStack current = vessel.getStackInSlot(i);
                if (carried.isEmpty() && !current.isEmpty())
                {
                    carriedSlot.set(vessel.extractItem(i, 64, false));
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }
}
