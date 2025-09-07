package com.Lino.hiddenCoins.database;

import com.Lino.hiddenCoins.HiddenCoins;
import java.io.File;
import java.sql.*;
import java.util.UUID;

public class DatabaseManager {

    private final HiddenCoins plugin;
    private Connection connection;
    private final String dbPath;

    public DatabaseManager(HiddenCoins plugin) {
        this.plugin = plugin;
        this.dbPath = plugin.getDataFolder() + File.separator + "data.db";
    }

    public void initialize() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        String coinsTable = "CREATE TABLE IF NOT EXISTS player_coins (" +
                "uuid TEXT PRIMARY KEY," +
                "coins INTEGER DEFAULT 0" +
                ");";

        String purchasesTable = "CREATE TABLE IF NOT EXISTS purchases (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "uuid TEXT," +
                "item TEXT," +
                "price INTEGER," +
                "timestamp BIGINT" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(coinsTable);
            stmt.execute(purchasesTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCoins(UUID uuid) {
        String query = "SELECT coins FROM player_coins WHERE uuid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, uuid.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("coins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setCoins(UUID uuid, int coins) {
        String query = "INSERT OR REPLACE INTO player_coins (uuid, coins) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, uuid.toString());
            pstmt.setInt(2, coins);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCoins(UUID uuid, int amount) {
        int current = getCoins(uuid);
        setCoins(uuid, current + amount);
    }

    public boolean removeCoins(UUID uuid, int amount) {
        int current = getCoins(uuid);
        if (current >= amount) {
            setCoins(uuid, current - amount);
            return true;
        }
        return false;
    }

    public void logPurchase(UUID uuid, String item, int price) {
        String query = "INSERT INTO purchases (uuid, item, price, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, item);
            pstmt.setInt(3, price);
            pstmt.setLong(4, System.currentTimeMillis());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}