package com.Lino.hiddenCoins.utils;

import com.Lino.hiddenCoins.HiddenCoins;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResourceManager {

    private final HiddenCoins plugin;

    public ResourceManager(HiddenCoins plugin) {
        this.plugin = plugin;
    }

    public void createDefaultFiles() {
        createConfigFile();
        createMessagesFile();
    }

    private void createConfigFile() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.getDataFolder().mkdirs();
            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write(getDefaultConfig());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createMessagesFile() {
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.getDataFolder().mkdirs();
            try (FileWriter writer = new FileWriter(messagesFile)) {
                writer.write(getDefaultMessages());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getDefaultConfig() {
        return "gui:\n" +
                "  title: \"<gradient:#FFD700:#FFA500>✨ Negozio HiddenCoins ✨</gradient>\"\n" +
                "  size: 54\n" +
                "  decoration:\n" +
                "    material: BLACK_STAINED_GLASS_PANE\n" +
                "    name: \" \"\n\n" +
                "coin-packages:\n" +
                "  small:\n" +
                "    name: \"<gradient:#90EE90:#32CD32>Pacchetto Piccolo</gradient>\"\n" +
                "    lore:\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#FFFFFF:#E0E0E0>Acquista</gradient> &e100 HiddenCoins\"\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#FFD700:#FFA500>► Prezzo:</gradient> &a{price}\"\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#00FF00:#00CC00>✦ Clicca per acquistare! ✦</gradient>\"\n" +
                "    material: GOLD_NUGGET\n" +
                "    slot: 20\n" +
                "    price: 1000.0\n" +
                "    coins: 100\n\n" +
                "  medium:\n" +
                "    name: \"<gradient:#87CEEB:#4682B4>Pacchetto Medio</gradient>\"\n" +
                "    lore:\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#FFFFFF:#E0E0E0>Acquista</gradient> &e500 HiddenCoins\"\n" +
                "      - \"<gradient:#FFD700:#FFA500>✨ Risparmia il 10% ✨</gradient>\"\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#FFD700:#FFA500>► Prezzo:</gradient> &a{price}\"\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#00FF00:#00CC00>✦ Clicca per acquistare! ✦</gradient>\"\n" +
                "    material: GOLD_INGOT\n" +
                "    slot: 22\n" +
                "    price: 4500.0\n" +
                "    coins: 500\n\n" +
                "  large:\n" +
                "    name: \"<gradient:#DA70D6:#8B008B>Pacchetto Grande</gradient>\"\n" +
                "    lore:\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#FFFFFF:#E0E0E0>Acquista</gradient> &e1000 HiddenCoins\"\n" +
                "      - \"<gradient:#FFD700:#FFA500>⭐ Risparmia il 20% ⭐</gradient>\"\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#FFD700:#FFA500>► Prezzo:</gradient> &a{price}\"\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#00FF00:#00CC00>✦ Clicca per acquistare! ✦</gradient>\"\n" +
                "    material: GOLD_BLOCK\n" +
                "    slot: 24\n" +
                "    price: 8000.0\n" +
                "    coins: 1000\n\n" +
                "shop-items:\n" +
                "  elite:\n" +
                "    name: \"<gradient:#FFD700:#FF6347>⚔ Ruolo Elite ⚔</gradient>\"\n" +
                "    lore:\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#FFFFFF:#E0E0E0>Ottieni il ruolo</gradient> &6Elite\"\n" +
                "      - \"<gradient:#87CEEB:#4682B4>nel server!</gradient>\"\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#00FF00:#00CC00>▸ Vantaggi:</gradient>\"\n" +
                "      - \"<gradient:#FFD700:#FFA500>•</gradient> &e/fly <gradient:#808080:#606060>nel mondo principale</gradient>\"\n" +
                "      - \"<gradient:#FFD700:#FFA500>•</gradient> &eKit Elite <gradient:#808080:#606060>settimanale</gradient>\"\n" +
                "      - \"<gradient:#FFD700:#FFA500>•</gradient> &eTag speciale <gradient:#808080:#606060>in chat</gradient>\"\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#00BFFF:#0080FF>► Prezzo:</gradient> &b{price} HiddenCoins\"\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#00FF00:#00CC00>✦ Clicca per acquistare! ✦</gradient>\"\n" +
                "    material: DIAMOND\n" +
                "    slot: 30\n" +
                "    coin-price: 500\n" +
                "    permission: group.elite\n" +
                "    command: \"lp user {player} parent add elite\"\n\n" +
                "  elite-plus:\n" +
                "    name: \"<gradient:#FF1493:#4B0082>⚔ Ruolo Elite+ ⚔</gradient>\"\n" +
                "    lore:\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#FFFFFF:#E0E0E0>Ottieni il ruolo</gradient> &dElite+\"\n" +
                "      - \"<gradient:#FF69B4:#FF1493>nel server!</gradient>\"\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#00FF00:#00CC00>▸ Vantaggi:</gradient>\"\n" +
                "      - \"<gradient:#FF1493:#FF69B4>•</gradient> &eTutti i vantaggi Elite\"\n" +
                "      - \"<gradient:#FF1493:#FF69B4>•</gradient> &e/god <gradient:#808080:#606060>modalità dio</gradient>\"\n" +
                "      - \"<gradient:#FF1493:#FF69B4>•</gradient> &eKit Elite+ <gradient:#808080:#606060>giornaliero</gradient>\"\n" +
                "      - \"<gradient:#FF1493:#FF69B4>•</gradient> &eAccesso a &d/warp vip\"\n" +
                "      - \"<gradient:#FF1493:#FF69B4>•</gradient> &eTag animato <gradient:#808080:#606060>in chat</gradient>\"\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#00BFFF:#0080FF>► Prezzo:</gradient> &b{price} HiddenCoins\"\n" +
                "      - \"\"\n" +
                "      - \"<gradient:#00FF00:#00CC00>✦ Clicca per acquistare! ✦</gradient>\"\n" +
                "    material: NETHERITE_INGOT\n" +
                "    slot: 32\n" +
                "    coin-price: 1500\n" +
                "    permission: group.eliteplus\n" +
                "    command: \"lp user {player} parent add eliteplus\"\n\n" +
                "sounds:\n" +
                "  open:\n" +
                "    enabled: true\n" +
                "    sound: BLOCK_CHEST_OPEN\n" +
                "    volume: 0.5\n" +
                "    pitch: 1.2\n\n" +
                "  success:\n" +
                "    enabled: true\n" +
                "    sound: ENTITY_PLAYER_LEVELUP\n" +
                "    volume: 0.7\n" +
                "    pitch: 1.5\n\n" +
                "  error:\n" +
                "    enabled: true\n" +
                "    sound: ENTITY_VILLAGER_NO\n" +
                "    volume: 0.5\n" +
                "    pitch: 0.8\n\n" +
                "  click:\n" +
                "    enabled: true\n" +
                "    sound: UI_BUTTON_CLICK\n" +
                "    volume: 0.3\n" +
                "    pitch: 1.0\n";
    }

    private String getDefaultMessages() {
        return "no-permission: \"<gradient:#FF0000:#CC0000>✖ Non hai il permesso per eseguire questo comando!</gradient>\"\n\n" +
                "gui:\n" +
                "  info:\n" +
                "    name: \"<gradient:#00FF00:#00AA00>✦ Le tue Informazioni ✦</gradient>\"\n" +
                "    lore: |\n" +
                "      \n" +
                "      <gradient:#FFD700:#FFA500>▸ Giocatore:</gradient> &e{player}\n" +
                "      \n" +
                "      <gradient:#00FF00:#00CC00>▸ Saldo:</gradient> &a{balance}\n" +
                "      <gradient:#00BFFF:#0080FF>▸ HiddenCoins:</gradient> &b{coins}\n" +
                "      \n" +
                "      &7Usa le tue coins per\n" +
                "      &7acquistare oggetti speciali!\n\n" +
                "purchase:\n" +
                "  coins:\n" +
                "    success: \"<gradient:#00FF00:#00CC00>✔ Hai acquistato con successo</gradient> &e{amount} HiddenCoins <gradient:#00FF00:#00CC00>per</gradient> &2{price}<gradient:#00FF00:#00CC00>!</gradient>\"\n" +
                "    insufficient-money: \"<gradient:#FF0000:#CC0000>✖ Non hai abbastanza soldi per questo acquisto!</gradient>\"\n\n" +
                "  item:\n" +
                "    success: \"<gradient:#00FF00:#00CC00>✔ Hai acquistato con successo</gradient> &e{item} <gradient:#00FF00:#00CC00>per</gradient> &b{price} HiddenCoins<gradient:#00FF00:#00CC00>!</gradient>\"\n" +
                "    insufficient-coins: \"<gradient:#FF0000:#CC0000>✖ Non hai abbastanza HiddenCoins per questo acquisto!</gradient>\"\n" +
                "    already-owned: \"<gradient:#FF0000:#CC0000>✖ Possiedi già questo oggetto!</gradient>\"\n\n" +
                "errors:\n" +
                "  player-only: \"<gradient:#FF0000:#CC0000>✖ Questo comando può essere eseguito solo da un giocatore!</gradient>\"\n" +
                "  invalid-amount: \"<gradient:#FF0000:#CC0000>✖ Importo non valido! Usa un numero positivo.</gradient>\"\n" +
                "  player-not-found: \"<gradient:#FF0000:#CC0000>✖ Giocatore non trovato!</gradient>\"\n" +
                "  invalid-syntax: \"<gradient:#FF0000:#CC0000>✖ Sintassi non valida! Usa: {syntax}</gradient>\"\n\n" +
                "admin:\n" +
                "  reload:\n" +
                "    success: \"<gradient:#00FF00:#00CC00>✔ Configurazione ricaricata con successo!</gradient>\"\n\n" +
                "  coins:\n" +
                "    set: \"<gradient:#00FF00:#00CC00>✔ Hai impostato le HiddenCoins di</gradient> &e{player} <gradient:#00FF00:#00CC00>a</gradient> &b{amount}<gradient:#00FF00:#00CC00>!</gradient>\"\n" +
                "    add: \"<gradient:#00FF00:#00CC00>✔ Hai aggiunto</gradient> &b{amount} HiddenCoins <gradient:#00FF00:#00CC00>a</gradient> &e{player}<gradient:#00FF00:#00CC00>!</gradient>\"\n" +
                "    remove: \"<gradient:#00FF00:#00CC00>✔ Hai rimosso</gradient> &b{amount} HiddenCoins <gradient:#00FF00:#00CC00>da</gradient> &e{player}<gradient:#00FF00:#00CC00>!</gradient>\"\n" +
                "    check: \"<gradient:#FFD700:#FFA500>▸</gradient> &e{player} <gradient:#FFD700:#FFA500>ha</gradient> &b{amount} HiddenCoins<gradient:#FFD700:#FFA500>!</gradient>\"\n\n" +
                "  reset:\n" +
                "    success: \"<gradient:#00FF00:#00CC00>✔ Hai resettato le HiddenCoins di</gradient> &e{player}<gradient:#00FF00:#00CC00>!</gradient>\"\n" +
                "    all: \"<gradient:#00FF00:#00CC00>✔ Hai resettato le HiddenCoins di tutti i giocatori!</gradient>\"\n\n" +
                "notifications:\n" +
                "  coins-received: \"<gradient:#00FF00:#00CC00>✔ Hai ricevuto</gradient> &b{amount} HiddenCoins<gradient:#00FF00:#00CC00>!</gradient>\"\n" +
                "  coins-removed: \"<gradient:#FF0000:#CC0000>✖ Ti sono state rimosse</gradient> &b{amount} HiddenCoins<gradient:#FF0000:#CC0000>!</gradient>\"\n\n" +
                "confirmations:\n" +
                "  purchase: \"<gradient:#FFD700:#FFA500>⚠ Sei sicuro di voler acquistare</gradient> &e{item}<gradient:#FFD700:#FFA500>?</gradient> &a[CONFERMA] &c[ANNULLA]\"\n" +
                "  reset: \"<gradient:#FF0000:#CC0000>⚠ Sei sicuro di voler resettare le tue HiddenCoins? Questa azione è irreversibile!</gradient>\"\n";
    }
}