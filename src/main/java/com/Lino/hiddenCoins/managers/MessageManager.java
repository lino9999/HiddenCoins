package com.Lino.hiddenCoins.managers;

import com.Lino.hiddenCoins.HiddenCoins;
import com.Lino.hiddenCoins.utils.GradientUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;

public class MessageManager {

    private final HiddenCoins plugin;
    private FileConfiguration messagesConfig;

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
        String message = messagesConfig.getString(path, "Messaggio non trovato: " + path);
        return colorize(message);
    }

    public String getMessage(String path, Map<String, String> placeholders) {
        String message = messagesConfig.getString(path, "Messaggio non trovato: " + path);

        if (placeholders != null) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }
        }

        return colorize(message);
    }

    public void sendMessage(Player player, String path) {
        player.sendMessage(getMessage(path));
    }

    public void sendMessage(Player player, String path, Map<String, String> placeholders) {
        player.sendMessage(getMessage(path, placeholders));
    }

    public String colorize(String message) {
        if (message == null) return "";
        return GradientUtils.translateColorCodes(message);
    }

    public static String stripColors(String text) {
        return GradientUtils.stripColors(text);
    }
}