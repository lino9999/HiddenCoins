package com.Lino.hiddenCoins.utils;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradientUtils {

    private static final Pattern GRADIENT_PATTERN = Pattern.compile("<gradient:(#[A-Fa-f0-9]{6}):(#[A-Fa-f0-9]{6})>(.*?)</gradient>");
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final Pattern BUKKIT_HEX_PATTERN = Pattern.compile("#([A-Fa-f0-9]{6})");

    public static String applyGradient(String text) {
        if (text == null) return "";

        Matcher matcher = GRADIENT_PATTERN.matcher(text);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String startHex = matcher.group(1);
            String endHex = matcher.group(2);
            String content = matcher.group(3);

            String gradient = createGradient(content, startHex, endHex);
            matcher.appendReplacement(result, Matcher.quoteReplacement(gradient));
        }

        matcher.appendTail(result);
        return result.toString();
    }

    private static String createGradient(String text, String startHex, String endHex) {
        if (text == null || text.isEmpty()) return text;

        int[] start = hexToRgb(startHex);
        int[] end = hexToRgb(endHex);

        StringBuilder builder = new StringBuilder();
        char[] chars = text.toCharArray();

        int visibleChars = 0;
        for (char c : chars) {
            if (c != ' ' && c != '§' && !Character.isISOControl(c)) {
                visibleChars++;
            }
        }

        if (visibleChars <= 1) {
            return applyHexColor(text, startHex);
        }

        int currentVisible = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (c == ' ') {
                builder.append(c);
                continue;
            }

            if (c == '§' && i + 1 < chars.length) {
                builder.append(c).append(chars[++i]);
                continue;
            }

            if (Character.isISOControl(c)) {
                builder.append(c);
                continue;
            }

            float ratio = (float) currentVisible / (float) (visibleChars - 1);
            int[] interpolated = interpolateColor(start, end, ratio);
            String hex = rgbToHex(interpolated);

            builder.append(applyHexColor(String.valueOf(c), hex));
            currentVisible++;
        }

        return builder.toString();
    }

    private static int[] hexToRgb(String hex) {
        hex = hex.replace("#", "");
        return new int[] {
                Integer.parseInt(hex.substring(0, 2), 16),
                Integer.parseInt(hex.substring(2, 4), 16),
                Integer.parseInt(hex.substring(4, 6), 16)
        };
    }

    private static String rgbToHex(int[] rgb) {
        return String.format("#%02x%02x%02x", rgb[0], rgb[1], rgb[2]);
    }

    private static int[] interpolateColor(int[] start, int[] end, float ratio) {
        int red = (int) (start[0] + (end[0] - start[0]) * ratio);
        int green = (int) (start[1] + (end[1] - start[1]) * ratio);
        int blue = (int) (start[2] + (end[2] - start[2]) * ratio);

        return new int[] {
                Math.max(0, Math.min(255, red)),
                Math.max(0, Math.min(255, green)),
                Math.max(0, Math.min(255, blue))
        };
    }

    public static String applyHexColors(String text) {
        if (text == null) return "";

        text = HEX_PATTERN.matcher(text).replaceAll("#$1");

        Matcher matcher = BUKKIT_HEX_PATTERN.matcher(text);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String hex = "#" + matcher.group(1);
            String replacement = applyHexColor("", hex);
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(result);
        return result.toString();
    }

    private static String applyHexColor(String text, String hex) {
        try {
            ChatColor color = ChatColor.of(hex);
            return color + text;
        } catch (Exception e) {
            return ChatColor.WHITE + text;
        }
    }

    public static String translateColorCodes(String text) {
        if (text == null) return "";

        text = applyGradient(text);
        text = applyHexColors(text);
        text = ChatColor.translateAlternateColorCodes('&', text);

        return text;
    }

    public static String stripColors(String text) {
        if (text == null) return null;

        text = GRADIENT_PATTERN.matcher(text).replaceAll("$3");
        text = HEX_PATTERN.matcher(text).replaceAll("");
        text = BUKKIT_HEX_PATTERN.matcher(text).replaceAll("");
        text = ChatColor.stripColor(text);

        return text;
    }
}