package io.github.jamalam360.tutorial.lib.stage;

import org.jetbrains.annotations.ApiStatus;

import io.github.jamalam360.tutorial.lib.ToastDuck;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.client.tutorial.TutorialManager;

/**
 * An individual stage in a tutorial. See {@code io.github.jamalam360.tutorial.lib.stage}.
 * If you wish to create a custom stage:
 *
 * <ol>
 *     <li>Create a class extending {@link Stage} that holds any required state (such as a required {@link net.minecraft.item.Item}).</li>
 *     <li>
 *         In a relevant place (such as an event or mixin), call:
 *         <code>
 *              if (YourMod.YOUR_TUTORIAL.getCurrentStage() instanceof YourStage &amp;&amp; /* check logic \*\/) {
                    YourMod.YOUR_TUTORIAL.advanceStage();
                }
 *         </code>
 *     </li>
 * </ol>
 */
public abstract class Stage {
    private final TutorialToast toast;
    private final int toastDisplayTicks;

    public Stage(TutorialToast toast) {
        this(toast, 160);
    }

    public Stage(TutorialToast toast, int toastDisplayTicks) {
        this.toast = toast;
        this.toastDisplayTicks = toastDisplayTicks;
    }

    /**
     * @return the toast shown when this tutorial stage is completed.
     */
    public TutorialToast getToast() {
        return this.toast;
    }

    @ApiStatus.Internal
    public void onStart(TutorialManager manager) {
        ((ToastDuck) this.toast).setToastVisibility(true);
    }

    @ApiStatus.Internal
    public void onFinish(TutorialManager manager) {
        ((ToastDuck) this.toast).setToastVisibility(true);
        manager.add(this.toast, this.toastDisplayTicks);
    }
}
