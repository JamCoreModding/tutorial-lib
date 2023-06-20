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

    public DelayedStage(int delay, TutorialToast toast) {
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
