package io.github.jamalam360.tutorial.lib.stage;

import net.minecraft.client.toast.TutorialToast;
import net.minecraft.util.Identifier;

public class ObtainAdvancementStage extends Stage {
    private final Identifier advancement;

    public ObtainAdvancementStage(Identifier advancement, TutorialToast toast, int toastDisplayTicks) {
        super(toast, toastDisplayTicks);
        this.advancement = advancement;
    }

    public boolean matches(Identifier other) {
        return this.advancement.equals(other);
    }
}
