/*
 * The MIT License (MIT)
 *
 * Copyright (c) [YEAR] Jamalam
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

import java.util.List;

import io.github.jamalam360.tutorial.lib.stage.EquipItemStage;
import io.github.jamalam360.tutorial.lib.stage.ManualStage;
import io.github.jamalam360.tutorial.lib.stage.ObtainAdvancementStage;
import io.github.jamalam360.tutorial.lib.stage.ObtainItemStage;
import io.github.jamalam360.tutorial.lib.stage.UseItemStage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TutorialLibTestMod implements ClientModInitializer {
        public static final String MOD_ID = "tutorial-lib-test-mod";

        @Override
        public void onInitializeClient() {
                Identifier tex = idOf("textures/gui/stages.png");

                Tutorial testTutorial = new Tutorial(
                                List.of(
                                                new ObtainItemStage(Items.WOODEN_SWORD, new CustomTutorialToast(tex, 20,
                                                                0, Text.literal("You'll need a sword!"), null, false),
                                                                160),
                                                new ObtainItemStage(Items.LEATHER_HELMET,
                                                                new TutorialToast(TutorialToast.Type.MOVEMENT_KEYS,
                                                                                Text.literal("A helmet would be good!"),
                                                                                null, false),
                                                                160),
                                                new EquipItemStage(Items.LEATHER_HELMET,
                                                                new TutorialToast(TutorialToast.Type.MOUSE,
                                                                                Text.literal("Nice helmet!"),
                                                                                Text.literal("Put it on."), false),
                                                                160),
                                                new ObtainItemStage(Items.POTION, new TutorialToast(
                                                                TutorialToast.Type.RECIPE_BOOK,
                                                                Text.literal("You'll need to drink a potion to finish off this mob."),
                                                                null, false), 160),
                                                new UseItemStage(Items.POTION,
                                                                new TutorialToast(TutorialToast.Type.RECIPE_BOOK,
                                                                                Text.literal("Drink up!"), null, false),
                                                                160),
                                                new ObtainAdvancementStage(new Identifier("adventure/kill_a_mob"),
                                                                new TutorialToast(TutorialToast.Type.TREE,
                                                                                Text.literal("Nice work!"),
                                                                                Text.literal("Now kill him..."), false),
                                                                160),
                                                new ManualStage(new TutorialToast(TutorialToast.Type.RECIPE_BOOK,
                                                                Text.literal("Great!"), null, false), 160)));

                Registry.register(TutorialLib.TUTORIAL_REGISTRY, TutorialLib.idOf("test_tutorial"), testTutorial);

                ClientCommandRegistrationCallback.EVENT.register((dispatcher, r) -> {
                        dispatcher.register(ClientCommandManager.literal("start_test_tutorial").executes(context -> {
                                testTutorial.advanceStage();
                                return 0;
                        }));

                        dispatcher.register(ClientCommandManager.literal("reset_test_tutorial").executes(context -> {
                                testTutorial.setCurrentStageIndex(-1);
                                return 0;
                        }));
                });
        }

        public static Identifier idOf(String path) {
                return new Identifier(MOD_ID, path);
        }
}
