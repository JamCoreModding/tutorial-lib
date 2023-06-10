package io.github.jamalam360.tutorial.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import io.github.jamalam360.tutorial.lib.ToastDuck;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.TutorialToast;

@Mixin(TutorialToast.class)
public abstract class TutorialToastMixin implements ToastDuck {
    @Shadow
    private Toast.Visibility visibility;

    @Override
    public void setToastVisibility(boolean visible) {
        this.visibility = visible ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
    }
}
