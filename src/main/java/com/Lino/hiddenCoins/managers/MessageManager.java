package com.Lino.hiddenCoins.managers;

import com.Lino.hiddenCoins.HiddenCoins;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageManager {

    private final HiddenCoins plugin;
    private FileConfiguration messagesConfig;
    private final Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");
    private final Pattern gradientPattern = Pattern.compile("<gradient:(#[a-fA-F0-9]{6}):(#[a-fA-F0-9]{6})>(.*?)</gradient>");

    public MessageManager(HiddenCoins plugin) {
        this.plugin = plugin;
        loadMessages();
    }

    private void loadMessages() {
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void reload() {
        loadMessages();
    }

    public String getMessage(String path) {
        return messagesConfig.getString(path, "Messaggio non trovato: " + path);
    }

    public String getMessage(String path, Map<String, String> placeholders) {
        String message = getMessage(path);

        if (placeholders != null) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }
        }

        return colorize(message);
    }

    public void sendMessage(Player player, String path) {
        player.sendMessage(colorize(getMessage(path)));
    }

    public void sendMessage(Player player, String path, Map<String, String> placeholders) {
        player.sendMessage(getMessage(path, placeholders));
    }

    private String colorize(String message) {
        message = replacePlaceholders(message);
        message = applyGradient(message);
        message = applyHexColors(message);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    private String replacePlaceholders(String message) {
        return message;
    }

    private String applyGradient(String message) {
        Matcher matcher = gradientPattern.matcher(message);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String startColor = matcher.group(1);
            String endColor = matcher.group(2);
            String text = matcher.group(3);

            String gradientText = createGradient(text, startColor, endColor);
            matcher.appendReplacement(result, gradientText);
        }

        matcher.appendTail(result);
        return result.toString();
    }

    private String createGradient(String text, String startHex, String endHex) {
        StringBuilder result = new StringBuilder();
        int[] start = hexToRgb(startHex);
        int[] end = hexToRgb(endHex);

        int length = text.length();
        for (int i = 0; i < length; i++) {
            if (text.charAt(i) == ' ') {
                result.append(' ');
                continue;
            }

            double ratio = (double) i / (length - 1);
            int r = (int) (start[0] + ratio * (end[0] - start[0]));
            int g = (int) (start[1] + ratio * (end[1] - start[1]));
            int b = (int) (start[2] + ratio * (end[2] - start[2]));

            String hex = String.format("#%02x%02x%02x", r, g, b);
            result.append(ChatColor.of(hex)).append(text.charAt(i));
        }

        return result.toString();
    }

    private int[] hexToRgb(String hex) {
        hex = hex.replace("#", "");
        return new int[] {
                Integer.parseInt(hex.substring(0, 2), 16),
                Integer.parseInt(hex.substring(2, 4), 16),
                Integer.parseInt(hex.substring(4, 6), 16)
        };
    }

    private String applyHexColors(String message) {
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String hex = matcher.group();
            ChatColor color = ChatColor.of(hex);
            matcher.appendReplacement(result, color.toString());
        }

        matcher.appendTail(result);
        return result.toString();
    }
}