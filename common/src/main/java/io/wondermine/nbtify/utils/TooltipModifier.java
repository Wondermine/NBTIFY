package io.wondermine.nbtify.utils;

import com.mojang.blaze3d.platform.InputConstants;
import io.wondermine.nbtify.screen.NBTScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Objects;

import static io.wondermine.nbtify.utils.ToolTipUtils.getGuide;

public class TooltipModifier {

    private static boolean flag = false;

    public static void getTooltip(ItemStack stack, TooltipFlag context, List<Component> lines) {
        if (!context.isAdvanced()) return;

        if (stack.getTag() == null) return;

        if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 258)) {
            lines.add(new TextComponent(""));
            getGuide(lines);

            String string = ToolTipUtils.format(stack);
            Minecraft.getInstance().setScreen(new NBTScreen(
                    Minecraft.getInstance().screen, string
            ));
            return;
        }

        if(Screen.hasAltDown()) {
            lines.remove(lines.size() - 1);

            String string = ToolTipUtils.Colorize(Objects.requireNonNull(ToolTipUtils.format(stack)));

            for (String line: string.lines().toList()) {
                lines.add(new TextComponent(line));
            }
            return;
        }

        if (Screen.hasControlDown()) {
            if (!flag) {
                String data = ToolTipUtils.format(stack);

                if (data == null) {
                    return;
                }

                Minecraft.getInstance().keyboardHandler.setClipboard(data);

                SystemToast.add(
                        Minecraft.getInstance().getToasts(),
                        SystemToast.SystemToastIds.TUTORIAL_HINT,
                        new TextComponent("NBTIFY"),
                        new TextComponent("Copied NBT data.")
                );

                lines.add(new TextComponent(""));
                getGuide(lines);

                flag = true;
                return;
            }
            lines.add(new TextComponent(""));
            getGuide(lines);
            return;
        }

        flag = false;

        lines.add(new TextComponent(""));
        getGuide(lines);
    }
}