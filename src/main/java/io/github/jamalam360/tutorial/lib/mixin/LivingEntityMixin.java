package io.github.jamalam360.tutorial.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.jamalam360.tutorial.lib.Tutorial;
import io.github.jamalam360.tutorial.lib.TutorialLib;
import io.github.jamalam360.tutorial.lib.stage.EquipItemStage;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "method_6116", at = @At("HEAD"))
    private void tutoriallib$triggerEquipItemStages(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack, CallbackInfo ci) {
        for (Tutorial tutorial : TutorialLib.getTutorials()) {
            if (tutorial.getCurrentStage() instanceof EquipItemStage equipItemStage
                    && equipItemStage.matches(newStack.getItem())) {
                tutorial.advanceStage();
            }
        }
    }
}
