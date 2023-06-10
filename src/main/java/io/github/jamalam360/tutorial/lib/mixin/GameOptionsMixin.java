package io.github.jamalam360.tutorial.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.jamalam360.tutorial.lib.Tutorial;
import io.github.jamalam360.tutorial.lib.TutorialLib;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.registry.RegistryKey;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Inject(method = "accept", at = @At("HEAD"))
    private void tutoriallib$insertStages(GameOptions.Visitor visitor, CallbackInfo ci) {
        for (var entry : TutorialLib.TUTORIAL_REGISTRY.getEntries()) {
            RegistryKey<Tutorial> key = entry.getKey();
            Tutorial tutorial = entry.getValue();
            tutorial.setCurrentStageIndex(visitor.visitInt(
                    "tutoriallib$" + key.getValue().toUnderscoreSeparatedString(), tutorial.getCurrentStageIndex()));
        }
    }
}
