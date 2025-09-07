package com.Lino.hiddenCoins.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ShopItem {

    public enum PurchaseType {
        MONEY_TO_COINS,
        COINS_TO_ITEM
    }

    private final String id;
    private final String name;
    private final List<String> lore;
    private final Material material;
    private final int slot;
    private final double moneyPrice;
    private final int coinAmount;
    private final PurchaseType purchaseType;
    private String command;
    private String permission;

    public ShopItem(String id, String name, List<String> lore, Material material,
                    int slot, double moneyPrice, int coinAmount, PurchaseType purchaseType) {
        this.id = id;
        this.name = name;
        this.lore = lore;
        this.material = material;
        this.slot = slot;
        this.moneyPrice = moneyPrice;
        this.coinAmount = coinAmount;
        this.purchaseType = purchaseType;
    }

    public ItemStack createDisplayItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public Material getMaterial() {
        return material;
    }

    public int getSlot() {
        return slot;
    }

    public double getMoneyPrice() {
        return moneyPrice;
    }

    public int getCoinAmount() {
        return coinAmount;
    }

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean hasCommand() {
        return command != null && !command.isEmpty();
    }

    public boolean hasPermission() {
        return permission != null && !permission.isEmpty();
    }
}