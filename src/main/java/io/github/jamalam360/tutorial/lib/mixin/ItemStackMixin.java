package io.github.jamalam360.tutorial.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.jamalam360.tutorial.lib.Tutorial;
import io.github.jamalam360.tutorial.lib.TutorialLib;
import io.github.jamalam360.tutorial.lib.stage.UseItemStage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void tutoriallib$triggerUseItemStages(CallbackInfoReturnable<ItemStack> cir) {
        for (Tutorial tutorial : TutorialLib.getTutorials()) {
            if (tutorial.getCurrentStage() instanceof UseItemStage useItemStage
                    && useItemStage.matches(this.getItem())) {
                tutorial.advanceStage();
            }
        }
    }
}
