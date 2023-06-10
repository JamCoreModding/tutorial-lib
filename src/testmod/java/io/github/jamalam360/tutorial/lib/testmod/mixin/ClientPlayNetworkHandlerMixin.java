package io.github.jamalam360.tutorial.lib.testmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.jamalam360.tutorial.lib.testmod.ObtainSwordItemStage;
import io.github.jamalam360.tutorial.lib.testmod.TutorialLibTestMod;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onScreenHandlerSlotUpdate", at = @At("HEAD"))
    private void tutoriallib$triggerObtainItemStages(ScreenHandlerSlotUpdateS2CPacket packet, CallbackInfo ci) {
        if (TutorialLibTestMod.TUTORIAL.getCurrentStage() instanceof ObtainSwordItemStage obtainSwordItemStage
                && obtainSwordItemStage.matches(packet.getItemStack().getItem())) {
            TutorialLibTestMod.TUTORIAL.advanceStage();
        }
    }
}