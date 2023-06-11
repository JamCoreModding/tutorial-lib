/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2023 Jamalam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
