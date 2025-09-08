package com.Lino.hiddenCoins;

import com.Lino.hiddenCoins.commands.NegozioCommand;
import com.Lino.hiddenCoins.database.DatabaseManager;
import com.Lino.hiddenCoins.managers.CoinsManager;
import com.Lino.hiddenCoins.managers.ConfigManager;
import com.Lino.hiddenCoins.managers.MessageManager;
import com.Lino.hiddenCoins.managers.VaultManager;
import com.Lino.hiddenCoins.utils.ResourceManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class HiddenCoins extends JavaPlugin {

    private static HiddenCoins instance;
    private DatabaseManager databaseManager;
    private CoinsManager coinsManager;
    private ConfigManager configManager;
    private MessageManager messageManager;
    private VaultManager vaultManager;

    @Override
    public void onEnable() {
        instance = this;

        ResourceManager resourceManager = new ResourceManager(this);
        resourceManager.createDefaultFiles();

        configManager = new ConfigManager(this);
        messageManager = new MessageManager(this);

        if (!setupVault()) {
            getLogger().severe("Vault non trovato! Disabilitando il plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        databaseManager = new DatabaseManager(this);
        databaseManager.initialize();

        coinsManager = new CoinsManager(this);

        NegozioCommand negozioCommand = new NegozioCommand(this);
        negozioCommand.setupListener();
        getCommand("negozio").setExecutor(negozioCommand);

        getLogger().info("HiddenCoins abilitato con successo!");
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.close();
        }
        getLogger().info("HiddenCoins disabilitato!");
    }

    private boolean setupVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        vaultManager = new VaultManager(this);
        return vaultManager.setupEconomy();
    }

    public static HiddenCoins getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public CoinsManager getCoinsManager() {
        return coinsManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public VaultManager getVaultManager() {
        return vaultManager;
    }
}