package io.github.jamalam360.tutorial.lib.testmod;

import io.github.jamalam360.tutorial.lib.stage.Stage;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public class ObtainSwordItemStage extends Stage {

    public ObtainSwordItemStage(TutorialToast toast) {
        super(toast);
    }
    
    public boolean matches(Item item) {
        return item instanceof SwordItem;
    }
    
}
