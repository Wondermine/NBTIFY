package io.wondermine.nbtify.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import io.wondermine.nbtify.utils.ToolTipUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;

import java.util.Timer;
import java.util.TimerTask;

public class NBTScreen extends Screen {
    private final Screen lastScreen;
    private final String jsonNBT;
    private final String formattedJsonNBT;
    private int yPos;

    public NBTScreen(Screen lastScreen, String jsonNBT) {
        super(new TextComponent(""));

        this.lastScreen = lastScreen;
        this.jsonNBT = jsonNBT;
        this.formattedJsonNBT = ToolTipUtils.Colorize(jsonNBT);
    }

    @Override
    public void init() {

        this.addButton(
                new Button(
                        (this.width / 2) - 90,
                        this.height - 60,
                        200,
                        20,
                        new TextComponent("Copy to clipboard"),
                        (button) -> {
                            assert this.minecraft != null;
                            this.minecraft.keyboardHandler.setClipboard(this.jsonNBT);

                            SystemToast.add(
                                    Minecraft.getInstance().getToasts(),
                                    SystemToast.SystemToastIds.TUTORIAL_HINT,
                                    new TextComponent("NBTIFY"),
                                    new TextComponent("Copied NBT data.")
                            );
                        })
        );

        this.addButton(
                new Button(
                        (this.width / 2) - 90,
                        this.height - 40,
                        200, 20,
                        CommonComponents.GUI_BACK ,
                        (button) -> {
                            assert this.minecraft != null;
                            this.minecraft.setScreen(this.lastScreen);
                        })
        );
    }

    @Override
    public void render(PoseStack guiGraphics, int i, int j, float f) {
        this.fillGradient(guiGraphics, 5, 20, this.width - 5, this.height - 10, -16777216, -804253680);
        renderBackground(guiGraphics);

        super.render(guiGraphics, i, j, f);

        int k=0;
        for (String line: this.formattedJsonNBT.lines().toList()) {

            int y = 25 + (10 * k) - yPos;

            if (y <= 18 || y >= this.height - 18) {
                k++;
                continue;
            }

            drawString(guiGraphics, this.font, line, 10, y, -1);
            k++;
        }
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f) {
        yPos = Mth.clamp((int) (yPos - (f * 10)), 0, 9999);

        Timer time = new Timer();
        final int[] runCount = {0};
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                yPos = Mth.clamp((int) (yPos - (f * 5)), 0, 9999);
                runCount[0]++;

                if (runCount[0] > 5) this.cancel();
            }
        };

        time.schedule(task, 0, 50);

        return true;
    }

    @Override
    public boolean mouseDragged(double d, double e, int f, double g, double h) {
        yPos = Mth.clamp((int) (yPos - (h * 2)), 0, 9999);
        return true;
    }
}