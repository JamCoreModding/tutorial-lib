package io.github.jamalam360.tutorial.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.jamalam360.tutorial.lib.Tutorial;
import io.github.jamalam360.tutorial.lib.TutorialLib;
import io.github.jamalam360.tutorial.lib.stage.EquipItemStage;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(method = "equipStack", at = @At("HEAD"))
    private void tutoriallib$triggerEquipItemStages(EquipmentSlot slot, ItemStack stack, CallbackInfo ci) {
        for (Tutorial tutorial : TutorialLib.getTutorials()) {
            if (tutorial.getCurrentStage() instanceof EquipItemStage equipItemStage
                    && equipItemStage.matches(stack.getItem())) {
                tutorial.advanceStage();
            }
        }
    }
}
