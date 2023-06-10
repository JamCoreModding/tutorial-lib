package io.github.jamalam360.tutorial.lib;

import java.util.List;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import io.github.jamalam360.tutorial.lib.stage.Stage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.tutorial.TutorialManager;

public class Tutorial {
    private final List<Stage> stages;
    private int stage = -1;

    public Tutorial(List<Stage> stages) {
        this.stages = stages;
    }

    @Nullable
    public Stage getCurrentStage() {
        if (this.stage == -1) {
            return null;
        }

        return this.stages.get(this.stage);
    }

    public int getCurrentStageIndex() {
        return this.stage;
    }

    @ApiStatus.Internal
    public void setCurrentStageIndex(int index) {
        this.stage = index;
    }

    public boolean advanceStage() {
        if (this.getCurrentStageIndex() + 1 >= this.stages.size()) {
            return false;
        }

        if (this.getCurrentStageIndex() != -1) {
            this.getCurrentStage().onFinish(this.getTutorialManager());
        }

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
