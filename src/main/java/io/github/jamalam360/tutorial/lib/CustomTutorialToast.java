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

import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

/**
 * A copy of {@link TutorialToast} which allows custom textures and dynamically expands to fit its content.
 * Textures must be 256x256.
 */
public class CustomTutorialToast extends TutorialToast implements ToastDuck {

    private static final int TEXT_LEFT_MARGIN = 30;
    private static final int TEXT_RIGHT_MARGIN = 10;

    private final Text title;
    @Nullable
    private final Text description;
    private final boolean hasProgressBar;
    private final Identifier texture;
    private final int u;
    private final int v;
    @Nullable
    private List<OrderedText> wrappedTitle = null;
    @Nullable
    private List<OrderedText> wrappedDescription = null;
    private Toast.Visibility visibility = Toast.Visibility.SHOW;
    private long lastTime;
    private float lastProgress;
    private float progress;

    public CustomTutorialToast(Identifier texture, Text title) {
        this(texture, title, null);
    }

    public CustomTutorialToast(Identifier texture, int u, int v, Text title) {
        this(texture, u, v, title, null);
    }

    public CustomTutorialToast(Identifier texture, Text title, @Nullable Text description) {
        this(texture, title, description, false);
    }

    public CustomTutorialToast(Identifier texture, int u, int v, Text title, @Nullable Text description) {
        this(texture, u, v, title, description, false);
    }

    public CustomTutorialToast(Identifier texture, Text title, @Nullable Text description, boolean hasProgressBar) {
        this(texture, 0, 0, title, description, hasProgressBar);
    }

    public CustomTutorialToast(Identifier texture, int u, int v, Text title, @Nullable Text description, boolean hasProgressBar) {
        // Type.MOUSE is a dummy placeholder.
        super(Type.MOUSE, title, description, hasProgressBar);

        this.title = title;
        this.description = description;
        this.hasProgressBar = hasProgressBar;
        this.texture = texture;
        this.u = u;
        this.v = v;
    }

    @Override
    public Toast.Visibility draw(GuiGraphics graphics, ToastManager manager, long startTime) {
        if (this.wrappedTitle == null) {
            this.wrappedTitle = MinecraftClient.getInstance().textRenderer.wrapLines(this.title, 160 - TEXT_LEFT_MARGIN - TEXT_RIGHT_MARGIN);
        }

        if (this.wrappedDescription == null) {
            this.wrappedDescription = this.description == null ? List.of() : MinecraftClient.getInstance().textRenderer.wrapLines(this.description, 160 - TEXT_LEFT_MARGIN - TEXT_RIGHT_MARGIN);
        }

        if (this.wrappedTitle.size() == 1 && this.wrappedDescription.size() <= 1) {
            graphics.drawTexture(TEXTURE, 0, 0, 0, 32, 160, 32);
        } else {
            int height = this.getHeight();
            int m = Math.min(4, height - 28);
            this.renderBackgroundRow(graphics, manager, 0, 0, 28);

            for (int n = 28; n < height - m; n += 10) {
                this.renderBackgroundRow(graphics, manager, 16, n, Math.min(16, height - n - m));
            }

            this.renderBackgroundRow(graphics, manager, 32 - m, height - m, m);
        }

        for (int i = 0; i < wrappedTitle.size(); i++) {
            graphics.drawText(manager.getGame().textRenderer, wrappedTitle.get(i), TEXT_LEFT_MARGIN, 7 + i * 12, -11534256, false);
        }

        for (int i = 0; i < wrappedDescription.size(); i++) {
            graphics.drawText(manager.getGame().textRenderer, wrappedDescription.get(i), TEXT_LEFT_MARGIN, 8 + wrappedTitle.size() * 12 + i * 12, -16777216, false);
        }

        graphics.drawTexture(this.texture, 6, 6, this.u, this.v, 20, 20);

        if (this.hasProgressBar) {
            graphics.fill(3, 28 + (this.getHeight() - 32), 157, 29 + (this.getHeight() - 32), -1);
            float f = MathHelper.clampedLerp(this.lastProgress, this.progress, (float) (startTime - this.lastTime) / 100.0F);
            int i;
            if (this.progress >= this.lastProgress) {
                i = -16755456;
            } else {
                i = -11206656;
            }

            graphics.fill(3, 28 + (this.getHeight() - 32), (int) (3.0F + 154.0F * f), 29 + (this.getHeight() - 32), i);
            this.lastProgress = f;
            this.lastTime = startTime;
        }

        return this.visibility;
    }

    private void renderBackgroundRow(GuiGraphics graphics, ToastManager manager, int vOffset, int y, int vHeight) {
        int uWidth = vOffset == 0 ? 20 : 5;
        graphics.drawTexture(TEXTURE, 0, y, 0, 32 + vOffset, uWidth, vHeight);

        for (int o = uWidth; o < 160 - 60; o += 64) {
            graphics.drawTexture(TEXTURE, o, y, 32, 32 + vOffset, Math.min(64, 160 - o - 60), vHeight);
        }

        graphics.drawTexture(TEXTURE, 160 - 60, y, 160 - 60, 32 + vOffset, 60, vHeight);
    }

    @Override
    public int getHeight() {
        return 10 + (this.wrappedTitle == null ? 0 : this.wrappedTitle.size() * 12) + (this.wrappedDescription == null ? 0 : this.wrappedDescription.size() * 12);
    }

    public void show() {
        this.visibility = Toast.Visibility.SHOW;
    }

    public void hide() {
        this.visibility = Toast.Visibility.HIDE;
    }

    public boolean hasProgressBar() {
        return this.hasProgressBar;
    }

    public float getProgress() {
        return this.progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    @Override
    public void setToastVisibility(boolean visible) {
        if (visible) {
            this.show();
        } else {
            this.hide();
        }
    }
}
