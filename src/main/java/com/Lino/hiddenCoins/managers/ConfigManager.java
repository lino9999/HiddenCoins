package com.Lino.hiddenCoins.managers;

import com.Lino.hiddenCoins.HiddenCoins;
import com.Lino.hiddenCoins.shop.ShopItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final HiddenCoins plugin;
    private FileConfiguration config;

    public ConfigManager(HiddenCoins plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public String getGuiTitle() {
        return config.getString("gui.title", "&6&lNegozio HiddenCoins");
    }

    public int getGuiSize() {
        return config.getInt("gui.size", 54);
    }

    public List<ShopItem> getCoinPackages() {
        List<ShopItem> packages = new ArrayList<>();
        ConfigurationSection section = config.getConfigurationSection("coin-packages");

        if (section != null) {
            for (String key : section.getKeys(false)) {
                String path = "coin-packages." + key + ".";
                ShopItem item = new ShopItem(
                        key,
                        config.getString(path + "name"),
                        config.getStringList(path + "lore"),
                        Material.valueOf(config.getString(path + "material", "GOLD_INGOT")),
                        config.getInt(path + "slot"),
                        config.getDouble(path + "price"),
                        config.getInt(path + "coins"),
                        ShopItem.PurchaseType.MONEY_TO_COINS
                );
                packages.add(item);
            }
        }

        return packages;
    }

    public List<ShopItem> getShopItems() {
        List<ShopItem> items = new ArrayList<>();
        ConfigurationSection section = config.getConfigurationSection("shop-items");

        if (section != null) {
            for (String key : section.getKeys(false)) {
                String path = "shop-items." + key + ".";
                ShopItem item = new ShopItem(
                        key,
                        config.getString(path + "name"),
                        config.getStringList(path + "lore"),
                        Material.valueOf(config.getString(path + "material", "DIAMOND")),
                        config.getInt(path + "slot"),
                        0,
                        config.getInt(path + "coin-price"),
                        ShopItem.PurchaseType.COINS_TO_ITEM
                );

                if (config.contains(path + "command")) {
                    item.setCommand(config.getString(path + "command"));
                }

                if (config.contains(path + "permission")) {
                    item.setPermission(config.getString(path + "permission"));
                }

                items.add(item);
            }
        }

        return items;
    }

    public Material getDecorationMaterial() {
        return Material.valueOf(config.getString("gui.decoration.material", "GRAY_STAINED_GLASS_PANE"));
    }

    public String getDecorationName() {
        return config.getString("gui.decoration.name", " ");
    }

    public boolean playSound(String soundType) {
        return config.getBoolean("sounds." + soundType + ".enabled", true);
    }

    public String getSound(String soundType) {
        return config.getString("sounds." + soundType + ".sound", "UI_BUTTON_CLICK");
    }

    public float getSoundVolume(String soundType) {
        return (float) config.getDouble("sounds." + soundType + ".volume", 1.0);
    }

    public float getSoundPitch(String soundType) {
        return (float) config.getDouble("sounds." + soundType + ".pitch", 1.0);
    }
}