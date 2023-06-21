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

import io.github.jamalam360.tutorial.lib.stage.DelayedStage;
import java.util.List;
import java.util.stream.Collectors;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see Tutorial
 */
@Environment(EnvType.CLIENT)
public class TutorialLib implements ClientModInitializer {

    public static final String MOD_ID = "tutorial-lib";
    public static final Registry<Tutorial> TUTORIAL_REGISTRY = FabricRegistryBuilder
          .createSimple(Tutorial.class, idOf("tutorial")).buildAndRegister();
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @ApiStatus.Internal
    public static List<Tutorial> getTutorials() {
        return TUTORIAL_REGISTRY.stream().collect(Collectors.toList());
    }

    public static Identifier idOf(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_WORLD_TICK.register(world -> {
            for (Tutorial tutorial : getTutorials()) {
                if (tutorial.getCurrentStage() instanceof DelayedStage delayedStage
                    && MinecraftClient.getInstance().world.getTime() >= delayedStage.getEndTime()) {
                    tutorial.advanceStage();
                }
            }
        });

        LOGGER.info("Initialized Tutorial Lib");
    }
}
