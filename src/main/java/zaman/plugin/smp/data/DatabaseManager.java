package zaman.plugin.smp.data;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private Connection connection;
    private final String dbPath;

    public DatabaseManager(JavaPlugin plugin) {
        this.dbPath = plugin.getDataFolder().getAbsolutePath() + "/database.db";
    }

    public void createTables() {
        try (Statement stmt = connection.createStatement()) {
            String sql = """
                    CREATE TABLE IF NOT EXISTS player_data (
                        uuid TEXT PRIMARY KEY,
                        name TEXT NOT NULL,
                        cash INTEGER DEFAULT 0,
                        coin INTEGER DEFAULT 0,
                        orb INTEGER DEFAULT 0,
                        last_attendance TEXT
                    );
                    """;
            System.out.println("테이블 생성 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            System.out.println("SQLite 연결 성공");
        } catch (ClassNotFoundException | SQLException e) {
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
                System.out.println("SQLite 연결 종료");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
