package io.github.jamalam360.tutorial.lib.stage;

import net.minecraft.client.toast.TutorialToast;
import net.minecraft.client.tutorial.TutorialManager;
import org.jetbrains.annotations.ApiStatus;

/**
 * A stage that is advanced after a certain amount of ticks elapse.
 */
public class DelayedStage extends Stage {

    private final int delay;
    private long startTime;

    public DelayedStage(TutorialToast toast, int delay) {
        super(toast);
        this.delay = delay;
    }

    public DelayedStage(TutorialToast toast, int delay, int toastDisplayTicks) {
        super(toast, toastDisplayTicks);
        this.delay = delay;
    }

    @Override
    public void onStart(TutorialManager manager) {
        super.onStart(manager);
        this.startTime = manager.getClient().world.getTime();
    }

    public long getEndTime() {
        return this.startTime + this.delay;
    }

    public long getStartTime() {
        return this.startTime;
    }

    @ApiStatus.Internal
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
