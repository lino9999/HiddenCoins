package com.Lino.hiddenCoins.managers;

import com.Lino.hiddenCoins.HiddenCoins;
import org.bukkit.entity.Player;
import java.util.UUID;

public class CoinsManager {

    private final HiddenCoins plugin;

    public CoinsManager(HiddenCoins plugin) {
        this.plugin = plugin;
    }

    public int getCoins(Player player) {
        return plugin.getDatabaseManager().getCoins(player.getUniqueId());
    }

    public int getCoins(UUID uuid) {
        return plugin.getDatabaseManager().getCoins(uuid);
    }

    public void setCoins(Player player, int amount) {
        plugin.getDatabaseManager().setCoins(player.getUniqueId(), amount);
    }

    public void addCoins(Player player, int amount) {
        plugin.getDatabaseManager().addCoins(player.getUniqueId(), amount);
    }

    public boolean removeCoins(Player player, int amount) {
        return plugin.getDatabaseManager().removeCoins(player.getUniqueId(), amount);
    }

    public boolean hasCoins(Player player, int amount) {
        return getCoins(player) >= amount;
    }

    public boolean purchaseWithMoney(Player player, double price, int coins) {
        if (plugin.getVaultManager().withdrawMoney(player, price)) {
            addCoins(player, coins);
            return true;
        }
        return false;
    }

    public boolean purchaseWithCoins(Player player, String item, int price) {
        if (removeCoins(player, price)) {
            plugin.getDatabaseManager().logPurchase(player.getUniqueId(), item, price);
            return true;
        }
        return false;
    }
}