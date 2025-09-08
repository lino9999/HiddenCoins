package com.Lino.hiddenCoins.commands;

import com.Lino.hiddenCoins.HiddenCoins;
import com.Lino.hiddenCoins.listeners.GUIListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NegozioCommand implements CommandExecutor {

    private final HiddenCoins plugin;
    private GUIListener guiListener;

    public NegozioCommand(HiddenCoins plugin) {
        this.plugin = plugin;
    }

    public void setupListener() {
        this.guiListener = new GUIListener(plugin);
        plugin.getServer().getPluginManager().registerEvents(guiListener, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Questo comando può essere eseguito solo da un giocatore!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("hiddencoins.shop")) {
            plugin.getMessageManager().sendMessage(player, "no-permission");
            return true;
        }

        guiListener.openGUI(player);
        return true;
    }
}