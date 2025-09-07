package com.Lino.hiddenCoins.gui;

import com.Lino.hiddenCoins.HiddenCoins;
import com.Lino.hiddenCoins.shop.ShopItem;
import com.Lino.hiddenCoins.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopGUI {

    private final HiddenCoins plugin;
    private final Player player;
    private final Inventory inventory;
    private final Map<Integer, ShopItem> items;

    public ShopGUI(HiddenCoins plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.items = new HashMap<>();

        String title = plugin.getConfigManager().getGuiTitle();
        title = plugin.getMessageManager().colorize(title);

        int size = plugin.getConfigManager().getGuiSize();

        this.inventory = Bukkit.createInventory(null, size, title);

        setupGUI();
    }

    private void setupGUI() {
        fillBackground();
        addCoinPackages();
        addShopItems();
        addInfoItem();
    }

    private void fillBackground() {
        Material material = plugin.getConfigManager().getDecorationMaterial();
        String name = plugin.getConfigManager().getDecorationName();
        name = plugin.getMessageManager().colorize(name);

        ItemStack decoration = new ItemStack(material);
        ItemMeta meta = decoration.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            decoration.setItemMeta(meta);
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, decoration);
            }
        }
    }

    private void addCoinPackages() {
        List<ShopItem> packages = plugin.getConfigManager().getCoinPackages();
        for (ShopItem item : packages) {
            ItemStack display = createCoinPackageItem(item);
            inventory.setItem(item.getSlot(), display);
            items.put(item.getSlot(), item);
        }
    }

    private void addShopItems() {
        List<ShopItem> shopItems = plugin.getConfigManager().getShopItems();
        for (ShopItem item : shopItems) {
            ItemStack display = createShopItem(item);
            inventory.setItem(item.getSlot(), display);
            items.put(item.getSlot(), item);
        }
    }

    private void addInfoItem() {
        ItemStack info = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = info.getItemMeta();

        if (meta != null) {
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("{player}", player.getName());
            placeholders.put("{coins}", String.valueOf(plugin.getCoinsManager().getCoins(player)));
            placeholders.put("{balance}", plugin.getVaultManager().formatMoney(
                    plugin.getVaultManager().getBalance(player)));

            String name = plugin.getMessageManager().getMessage("gui.info.name", placeholders);
            List<String> lore = new ArrayList<>();

            String loreText = plugin.getMessageManager().getMessage("gui.info.lore", placeholders);
            for (String line : loreText.split("\n")) {
                lore.add(line);
            }

            meta.setDisplayName(name);
            meta.setLore(lore);
            info.setItemMeta(meta);
        }

        inventory.setItem(49, info);
    }

    private ItemStack createCoinPackageItem(ShopItem item) {
        ItemStack display = item.createDisplayItem();
        ItemMeta meta = display.getItemMeta();

        if (meta != null) {
            String name = plugin.getMessageManager().colorize(item.getName());
            List<String> lore = new ArrayList<>();

            for (String line : item.getLore()) {
                line = line.replace("{price}", plugin.getVaultManager().formatMoney(item.getMoneyPrice()));
                line = line.replace("{coins}", String.valueOf(item.getCoinAmount()));
                lore.add(plugin.getMessageManager().colorize(line));
            }

            meta.setDisplayName(name);
            meta.setLore(lore);
            display.setItemMeta(meta);
        }

        return display;
    }

    private ItemStack createShopItem(ShopItem item) {
        ItemStack display = item.createDisplayItem();
        ItemMeta meta = display.getItemMeta();

        if (meta != null) {
            String name = plugin.getMessageManager().colorize(item.getName());
            List<String> lore = new ArrayList<>();

            for (String line : item.getLore()) {
                line = line.replace("{price}", String.valueOf(item.getCoinAmount()));
                lore.add(plugin.getMessageManager().colorize(line));
            }

            meta.setDisplayName(name);
            meta.setLore(lore);
            display.setItemMeta(meta);
        }

        return display;
    }

    public void open() {
        player.openInventory(inventory);
        playSound("open");
    }

    public void handleClick(int slot) {
        if (!items.containsKey(slot)) {
            playSound("error");
            return;
        }

        ShopItem item = items.get(slot);

        if (item.getPurchaseType() == ShopItem.PurchaseType.MONEY_TO_COINS) {
            handleCoinPurchase(item);
        } else {
            handleItemPurchase(item);
        }
    }

    private void handleCoinPurchase(ShopItem item) {
        if (plugin.getCoinsManager().purchaseWithMoney(player, item.getMoneyPrice(), item.getCoinAmount())) {
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("{amount}", String.valueOf(item.getCoinAmount()));
            placeholders.put("{price}", plugin.getVaultManager().formatMoney(item.getMoneyPrice()));

            plugin.getMessageManager().sendMessage(player, "purchase.coins.success", placeholders);
            playSound("success");

            player.closeInventory();
            new ShopGUI(plugin, player).open();
        } else {
            plugin.getMessageManager().sendMessage(player, "purchase.coins.insufficient-money");
            playSound("error");
        }
    }

    private void handleItemPurchase(ShopItem item) {
        if (plugin.getCoinsManager().purchaseWithCoins(player, item.getId(), item.getCoinAmount())) {
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("{item}", MessageManager.stripColors(item.getName()));
            placeholders.put("{price}", String.valueOf(item.getCoinAmount()));

            if (item.hasCommand()) {
                String command = item.getCommand().replace("{player}", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }

            if (item.hasPermission()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "lp user " + player.getName() + " permission set " + item.getPermission() + " true");
            }

            plugin.getMessageManager().sendMessage(player, "purchase.item.success", placeholders);
            playSound("success");

            player.closeInventory();
            new ShopGUI(plugin, player).open();
        } else {
            plugin.getMessageManager().sendMessage(player, "purchase.item.insufficient-coins");
            playSound("error");
        }
    }

    private void playSound(String type) {
        if (plugin.getConfigManager().playSound(type)) {
            try {
                Sound sound = Sound.valueOf(plugin.getConfigManager().getSound(type));
                float volume = plugin.getConfigManager().getSoundVolume(type);
                float pitch = plugin.getConfigManager().getSoundPitch(type);
                player.playSound(player.getLocation(), sound, volume, pitch);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}