package io.wondermine.nbtify.utils;

import com.google.gson.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolTipUtils {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String format(ItemStack stack) {
        if (stack.getTag() == null) return null;

        return gson.toJson(NBTToJsonHelper.nbtToJson(stack.getTag()));
    }

    public static String Colorize(String string) {
        StringBuilder naw = new StringBuilder();

        for (String line: string.lines().toList()) {
            // stage one.
            Pattern p = Pattern.compile("\"(.*)\": \"(.*)\"");
            Matcher m = p.matcher(line);
            boolean flag = false;

            while (m.find()) {
                flag = true;
                naw.append(line.replace(
                        m.group(0),
                        Color("red") + "\"" + m.group(1) + "\"" + Color("white") + ": " + Color("green") + "\"" + m.group(2) + "\"" + Color("white")
                )).append("\n");
            }

            if (flag) {
                continue;
            }

            // stage two.
            p = Pattern.compile("\"(.*)\": \\{");
            m = p.matcher(line);


            while (m.find()) {
                flag = true;

                naw.append(line.replace(
                        m.group(0),
                        Color("red") + "\"" +  m.group(1) + "\"" + Color("white") + ": {"
                )).append("\n");
            }

            if (flag) {
                continue;
            }

            // stage three.
            p = Pattern.compile("\"(.*)\": \\[");
            m = p.matcher(line);

            while (m.find()) {
                flag = true;

                naw.append(line.replace(
                        m.group(0),
                        Color("red") + "\"" +  m.group(1) + "\"" + Color("white") + ": ["
                )).append("\n");
            }

            if (flag) {
                continue;
            }

            // stage four.
            p = Pattern.compile("\"(.*)\": (\\d+|\\w+)(.*)");
            m = p.matcher(line);

            while (m.find()) {
                flag = true;
                naw.append(line.replace(
                        m.group(0),
                        Color("red") + "\"" + m.group(1) + "\"" + Color("white") + ": " + Color("gold") + m.group(2) + Color("white") + m.group(3)
                )).append("\n");
            }

            if (flag) {
                continue;
            }

            naw.append(line).append("\n");
        }

        return naw.toString();
    }

    public static void getGuide(List<Component> lines) {
        Component alt =  new TextComponent("Alt").withStyle(ChatFormatting.UNDERLINE);

        Component control = new TextComponent("Control").withStyle(ChatFormatting.UNDERLINE);

        Component tab =  new TextComponent("Tab").withStyle(ChatFormatting.UNDERLINE);

        lines.add( new TextComponent("Press ").append(alt).append(" to preview NBT Data")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        lines.add(new TextComponent(""));

        lines.add( new TextComponent("Press ").append(control).append(" to copy NBT to clipboard.")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));

        lines.add( new TextComponent(""));
        lines.add( new TextComponent("Press ").append(tab).append(" To open NBT Viewer.")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    public static ChatFormatting Color(String color) {

        return switch (color) {
            case "black" -> ChatFormatting.BLACK;
            case "dark_blue" -> ChatFormatting.DARK_BLUE;
            case "dark_green" -> ChatFormatting.DARK_GREEN;
            case "dark_aqua" -> ChatFormatting.DARK_AQUA;
            case "dark_red" -> ChatFormatting.DARK_RED;
            case "dark_purple" -> ChatFormatting.DARK_PURPLE;
            case "gold" -> ChatFormatting.GOLD;
            case "gray" -> ChatFormatting.GRAY;
            case "dark_gray" -> ChatFormatting.DARK_GRAY;
            case "blue" -> ChatFormatting.BLUE;
            case "green" -> ChatFormatting.GREEN;
            case "aqua" -> ChatFormatting.AQUA;
            case "red" -> ChatFormatting.RED;
            case "light_purple" -> ChatFormatting.LIGHT_PURPLE;
            case "yellow" -> ChatFormatting.YELLOW;
            default -> ChatFormatting.WHITE;
        };
    }
}