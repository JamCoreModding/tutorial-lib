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
