package io.github.jamalam360.tutorial.lib.stage;

import net.minecraft.client.toast.TutorialToast;

/**
 * A stage that can be advanced manually with no condition.
 */
public class ManualStage extends Stage {
    public ManualStage(TutorialToast toast) {
        this(toast, 160);
    }

    public ManualStage(TutorialToast toast, int toastDisplayTicks) {
        super(toast, toastDisplayTicks);
    }
}
