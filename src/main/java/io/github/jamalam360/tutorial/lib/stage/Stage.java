package io.github.jamalam360.tutorial.lib.stage;

import org.jetbrains.annotations.ApiStatus;

import io.github.jamalam360.tutorial.lib.ToastDuck;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.client.tutorial.TutorialManager;

public abstract class Stage {
    private final TutorialToast toast;
    private final int toastDisplayTicks;

    public Stage(TutorialToast toast, int toastDisplayTicks) {
        this.toast = toast;
        this.toastDisplayTicks = toastDisplayTicks;
    }

    public TutorialToast getToast() {
        return this.toast;
    }

    @ApiStatus.Internal
    public void onStart(TutorialManager manager) {
        ((ToastDuck) this.toast).setToastVisibility(true);
        manager.add(this.toast, this.toastDisplayTicks);
    }

    @ApiStatus.Internal
    public void onFinish(TutorialManager manager) {
        manager.remove(this.toast);
    }
}
