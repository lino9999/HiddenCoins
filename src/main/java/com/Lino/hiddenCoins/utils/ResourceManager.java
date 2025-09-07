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
                "  title: \"<gradient:#FFD700:#FFA500>Negozio HiddenCoins</gradient>\"\n" +
                "  size: 54\n" +
                "  decoration:\n" +
                "    material: BLACK_STAINED_GLASS_PANE\n" +
                "    name: \" \"\n\n" +
                "coin-packages:\n" +
                "  small:\n" +
                "    name: \"<gradient:#90EE90:#32CD32>Pacchetto Piccolo</gradient>\"\n" +
                "    lore:\n" +
                "      - \"\"\n" +
                "      - \"&7Acquista &e100 HiddenCoins\"\n" +
                "      - \"\"\n" +
                "      - \"&8► &7Prezzo: &a{price}\"\n" +
                "      - \"\"\n" +
                "      - \"&eClicca per acquistare!\"\n" +
                "    material: GOLD_NUGGET\n" +
                "    slot: 20\n" +
                "    price: 1000.0\n" +
                "    coins: 100\n\n" +
                "  medium:\n" +
                "    name: \"<gradient:#87CEEB:#4682B4>Pacchetto Medio</gradient>\"\n" +
                "    lore:\n" +
                "      - \"\"\n" +
                "      - \"&7Acquista &e500 HiddenCoins\"\n" +
                "      - \"&7&o(Risparmia il 10%)\"\n" +
                "      - \"\"\n" +
                "      - \"&8► &7Prezzo: &a{price}\"\n" +
                "      - \"\"\n" +
                "      - \"&eClicca per acquistare!\"\n" +
                "    material: GOLD_INGOT\n" +
                "    slot: 22\n" +
                "    price: 4500.0\n" +
                "    coins: 500\n\n" +
                "  large:\n" +
                "    name: \"<gradient:#DA70D6:#8B008B>Pacchetto Grande</gradient>\"\n" +
                "    lore:\n" +
                "      - \"\"\n" +
                "      - \"&7Acquista &e1000 HiddenCoins\"\n" +
                "      - \"&7&o(Risparmia il 20%)\"\n" +
                "      - \"\"\n" +
                "      - \"&8► &7Prezzo: &a{price}\"\n" +
                "      - \"\"\n" +
                "      - \"&eClicca per acquistare!\"\n" +
                "    material: GOLD_BLOCK\n" +
                "    slot: 24\n" +
                "    price: 8000.0\n" +
                "    coins: 1000\n\n" +
                "shop-items:\n" +
                "  elite:\n" +
                "    name: \"<gradient:#FFD700:#FF6347>Ruolo Elite</gradient>\"\n" +
                "    lore:\n" +
                "      - \"\"\n" +
                "      - \"&7Ottieni il ruolo &6Elite\"\n" +
                "      - \"&7nel server!\"\n" +
                "      - \"\"\n" +
                "      - \"&8► &7Vantaggi:\"\n" +
                "      - \"&8• &e/fly &7nel mondo principale\"\n" +
                "      - \"&8• &eKit Elite &7settimanale\"\n" +
                "      - \"&8• &eTag speciale &7in chat\"\n" +
                "      - \"\"\n" +
                "      - \"&8► &7Prezzo: &b{price} HiddenCoins\"\n" +
                "      - \"\"\n" +
                "      - \"&eClicca per acquistare!\"\n" +
                "    material: DIAMOND\n" +
                "    slot: 30\n" +
                "    coin-price: 500\n" +
                "    permission: group.elite\n" +
                "    command: \"lp user {player} parent add elite\"\n\n" +
                "  elite-plus:\n" +
                "    name: \"<gradient:#FF1493:#4B0082>Ruolo Elite+</gradient>\"\n" +
                "    lore:\n" +
                "      - \"\"\n" +
                "      - \"&7Ottieni il ruolo &dElite+\"\n" +
                "      - \"&7nel server!\"\n" +
                "      - \"\"\n" +
                "      - \"&8► &7Vantaggi:\"\n" +
                "      - \"&8• &eTutti i vantaggi Elite\"\n" +
                "      - \"&8• &e/god &7modalità dio\"\n" +
                "      - \"&8• &eKit Elite+ &7giornaliero\"\n" +
                "      - \"&8• &eAccesso a &d/warp vip\"\n" +
                "      - \"&8• &eTag animato &7in chat\"\n" +
                "      - \"\"\n" +
                "      - \"&8► &7Prezzo: &b{price} HiddenCoins\"\n" +
                "      - \"\"\n" +
                "      - \"&eClicca per acquistare!\"\n" +
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
        return "prefix: \"<gradient:#FFD700:#FFA500>[HiddenCoins]</gradient> \"\n\n" +
                "no-permission: \"{prefix}&cNon hai il permesso per eseguire questo comando!\"\n\n" +
                "gui:\n" +
                "  info:\n" +
                "    name: \"<gradient:#00FF00:#00AA00>Le tue Informazioni</gradient>\"\n" +
                "    lore: |\n" +
                "      \n" +
                "      &8► &7Giocatore: &e{player}\n" +
                "      \n" +
                "      &8► &7Saldo: &a{balance}\n" +
                "      &8► &7HiddenCoins: &b{coins}\n" +
                "      \n" +
                "      &7Usa le tue coins per\n" +
                "      &7acquistare oggetti speciali!\n\n" +
                "purchase:\n" +
                "  coins:\n" +
                "    success: \"{prefix}&aHai acquistato con successo &e{amount} HiddenCoins &aper &2{price}&a!\"\n" +
                "    insufficient-money: \"{prefix}&cNon hai abbastanza soldi per questo acquisto!\"\n\n" +
                "  item:\n" +
                "    success: \"{prefix}&aHai acquistato con successo &e{item} &aper &b{price} HiddenCoins&a!\"\n" +
                "    insufficient-coins: \"{prefix}&cNon hai abbastanza HiddenCoins per questo acquisto!\"\n" +
                "    already-owned: \"{prefix}&cPossiedi già questo oggetto!\"\n\n" +
                "errors:\n" +
                "  player-only: \"&cQuesto comando può essere eseguito solo da un giocatore!\"\n" +
                "  invalid-amount: \"{prefix}&cImporto non valido! Usa un numero positivo.\"\n" +
                "  player-not-found: \"{prefix}&cGiocatore non trovato!\"\n" +
                "  invalid-syntax: \"{prefix}&cSintassi non valida! Usa: {syntax}\"\n\n" +
                "admin:\n" +
                "  reload:\n" +
                "    success: \"{prefix}&aConfigurazione ricaricata con successo!\"\n\n" +
                "  coins:\n" +
                "    set: \"{prefix}&aHai impostato le HiddenCoins di &e{player} &aa &b{amount}&a!\"\n" +
                "    add: \"{prefix}&aHai aggiunto &b{amount} HiddenCoins &aa &e{player}&a!\"\n" +
                "    remove: \"{prefix}&aHai rimosso &b{amount} HiddenCoins &ada &e{player}&a!\"\n" +
                "    check: \"{prefix}&e{player} &7ha &b{amount} HiddenCoins&7!\"\n\n" +
                "  reset:\n" +
                "    success: \"{prefix}&aHai resettato le HiddenCoins di &e{player}&a!\"\n" +
                "    all: \"{prefix}&aHai resettato le HiddenCoins di tutti i giocatori!\"\n\n" +
                "notifications:\n" +
                "  coins-received: \"{prefix}&aHai ricevuto &b{amount} HiddenCoins&a!\"\n" +
                "  coins-removed: \"{prefix}&cTi sono state rimosse &b{amount} HiddenCoins&c!\"\n\n" +
                "confirmations:\n" +
                "  purchase: \"&7Sei sicuro di voler acquistare &e{item}&7? &a[CONFERMA] &c[ANNULLA]\"\n" +
                "  reset: \"&cSei sicuro di voler resettare le tue HiddenCoins? Questa azione è irreversibile!\"\n";
    }
}