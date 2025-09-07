package com.Lino.hiddenCoins.managers;

import com.Lino.hiddenCoins.HiddenCoins;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultManager {

    private final HiddenCoins plugin;
    private Economy economy;

    public VaultManager(HiddenCoins plugin) {
        this.plugin = plugin;
    }

    public boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public double getBalance(Player player) {
        return economy.getBalance(player);
    }

    public boolean withdrawMoney(Player player, double amount) {
        if (economy.has(player, amount)) {
            economy.withdrawPlayer(player, amount);
            return true;
        }
        return false;
    }

    public void depositMoney(Player player, double amount) {
        economy.depositPlayer(player, amount);
    }

    public String formatMoney(double amount) {
        return economy.format(amount);
    }
}