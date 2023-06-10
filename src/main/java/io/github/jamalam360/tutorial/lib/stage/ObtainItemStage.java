package io.github.jamalam360.tutorial.lib.stage;

import net.minecraft.client.toast.TutorialToast;
import net.minecraft.item.Item;

public class ObtainItemStage extends Stage {
    private final Item item;

    public ObtainItemStage(Item item, TutorialToast toast, int toastDisplayTicks) {
        super(toast, toastDisplayTicks);
        this.item = item;
    }

    public boolean matches(Item other) {
        return this.item == other;
    }
}
