package com.Lino.hiddenCoins.listeners;

import com.Lino.hiddenCoins.HiddenCoins;
import com.Lino.hiddenCoins.gui.ShopGUI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIListener implements Listener {

    private final HiddenCoins plugin;
    private final Map<UUID, ShopGUI> openGUIs;

    public GUIListener(HiddenCoins plugin) {
        this.plugin = plugin;
        this.openGUIs = new HashMap<>();
    }

    public void openGUI(Player player) {
        ShopGUI gui = new ShopGUI(plugin, player);
        openGUIs.put(player.getUniqueId(), gui);
        gui.open();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();

        if (!openGUIs.containsKey(uuid)) return;

        event.setCancelled(true);

        ShopGUI gui = openGUIs.get(uuid);
        if (event.getInventory().equals(gui.getInventory())) {
            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                gui.handleClick(event.getSlot());
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Player player = (Player) event.getPlayer();
        openGUIs.remove(player.getUniqueId());
    }
}