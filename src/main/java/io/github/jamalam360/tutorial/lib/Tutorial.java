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

package io.github.jamalam360.tutorial.lib;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import io.github.jamalam360.tutorial.lib.stage.Stage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.tutorial.TutorialManager;

/**
 * Holds the stages and state of an in-game tutorial.
 *
 * @see Stage
 */
public class Tutorial {
    private final Stage[] stages;
    private int stage = 0;

    public Tutorial(Stage... stages) {
        this.stages = stages;
    }

    @Nullable
    public Stage getCurrentStage() {
        if (this.stage < 0 || this.stage >= this.stages.length) {
            return null;
        }

        return this.stages[this.stage];
    }

    public int getCurrentStageIndex() {
        return this.stage;
    }

    @ApiStatus.Internal
    public void setCurrentStageIndex(int index) {
        this.stage = index;
    }

    public boolean advanceStage() {
        if (this.getCurrentStageIndex() + 1 >= this.stages.length) {
            this.getCurrentStage().onFinish(this.getTutorialManager());
            this.setCurrentStageIndex(this.getCurrentStageIndex() + 1);
            return false;
        }

        this.getCurrentStage().onFinish(this.getTutorialManager());
        this.setCurrentStageIndex(this.getCurrentStageIndex() + 1);
        this.saveGameOptions();
        this.getCurrentStage().onStart(this.getTutorialManager());
        return true;
    }

    public void setStageProgress(float progress) {
        this.getCurrentStage().getToast().setProgress(progress);
    }

    private TutorialManager getTutorialManager() {
        return MinecraftClient.getInstance().getTutorialManager();
    }

    private void saveGameOptions() {
        MinecraftClient.getInstance().options.write();
    }
}
