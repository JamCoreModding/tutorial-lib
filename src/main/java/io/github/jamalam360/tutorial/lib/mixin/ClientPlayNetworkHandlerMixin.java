package io.github.jamalam360.tutorial.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.jamalam360.tutorial.lib.Tutorial;
import io.github.jamalam360.tutorial.lib.TutorialLib;
import io.github.jamalam360.tutorial.lib.stage.ObtainAdvancementStage;
import io.github.jamalam360.tutorial.lib.stage.ObtainItemStage;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.AdvancementUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.util.Identifier;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onScreenHandlerSlotUpdate", at = @At("HEAD"))
    private void tutoriallib$triggerObtainItemStages(ScreenHandlerSlotUpdateS2CPacket packet, CallbackInfo ci) {
        for (Tutorial tutorial : TutorialLib.getTutorials()) {
            if (tutorial.getCurrentStage() instanceof ObtainItemStage obtainItemStage
                    && obtainItemStage.matches(packet.getItemStack().getItem())) {
                tutorial.advanceStage();
            }
        }
    }

    @Inject(method = "onAdvancements", at = @At("HEAD"))
    private void tutoriallib$triggerAdvancementStages(AdvancementUpdateS2CPacket packet, CallbackInfo ci) {
        for (Identifier advancement : packet.getAdvancementsToEarn().keySet()) {
            for (Tutorial tutorial : TutorialLib.getTutorials()) {
                if (tutorial.getCurrentStage() instanceof ObtainAdvancementStage advancementStage
                        && advancementStage.matches(advancement)) {
                    tutorial.advanceStage();
                }
            }
        }
    }
}
