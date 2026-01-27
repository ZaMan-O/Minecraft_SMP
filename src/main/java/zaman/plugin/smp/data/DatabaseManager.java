package zaman.plugin.smp.data;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private Connection connection;
    private final String dbPath;
    private final JavaPlugin plugin;

    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dbPath = plugin.getDataFolder().getAbsolutePath() + "/database.db";
    }

    public void createTables() {
        try (Statement stmt = connection.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS player_data (
                        uuid TEXT PRIMARY KEY,
                        name TEXT NOT NULL,
                        coin INTEGER DEFAULT 1000,
                        gem INTEGER DEFAULT 0,
                        last_attendance TEXT
                    );
                    """;
            stmt.executeUpdate(sql);
            plugin.getLogger().info("Table Created Successfully");
        } catch (SQLException e) {
            plugin.getLogger().info("Table Creation Failed");
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            plugin.getLogger().info("SQLite Connected Successfully");
        } catch (ClassNotFoundException | SQLException e) {
            plugin.getLogger().info("SQLite Connection Failed");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                plugin.getLogger().info("SQLite Disconnected Successfully");
            }
        } catch (SQLException e) {
            plugin.getLogger().info("SQLite Disconnection Failed");
            e.printStackTrace();
        }
    }
}
