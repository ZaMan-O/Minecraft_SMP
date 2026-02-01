package zaman.plugin.smp.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import zaman.plugin.smp.data.dto.PlayerData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {
    private final DatabaseManager db;
    private final JavaPlugin plugin;
    private final HashMap<UUID, PlayerData> cache;

    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");

    public PlayerDataManager(DatabaseManager db, JavaPlugin plugin) {
        this.db = db;
        this.plugin = plugin;
        this.cache = new HashMap<>();

        for (Player p : Bukkit.getOnlinePlayers()) {
            loadPlayer(p.getUniqueId(), p.getName());
        }
    }

    public void loadPlayer(UUID uuid, String name) {
        String sql = "SELECT * FROM player_data WHERE uuid = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, uuid.toString());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                PlayerData data = new PlayerData(
                        rs.getString("name"),
                        rs.getInt("coin"),
                        rs.getInt("gem"),
                        rs.getString("last_attendance")
                );
                cache.put(uuid, data);
                plugin.getLogger().info(name + " Data Load Complete");
            } else {
                createNewPlayer(uuid, name);

                PlayerData data = new PlayerData(name, 1000, 0, null);
                cache.put(uuid, data);
                plugin.getLogger().info(name + " New Player Data Created");
            }
        } catch (SQLException e) {
            plugin.getLogger().severe(name + " Data Load Failed");
            e.printStackTrace();

            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                player.kickPlayer("§c§l데이터 로드 실패!\n§c잠시 후 다시 접속해주세요.");
            }
        }
    }

    private void createNewPlayer(UUID uuid, String name) {
        String sql = "INSERT INTO player_data (uuid, name) VALUES (?, ?)";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().info(name + " Data Create Failed!");
            e.printStackTrace();
        }
    }

    public PlayerData getPlayerData(UUID uuid) {
        return cache.get(uuid);
    }

    public void savePlayer(UUID uuid) {
        PlayerData data = cache.get(uuid);
        if (data == null) return;

        String sql = "UPDATE player_data SET name = ?, coin = ?, gem = ?, last_attendance = ? WHERE uuid = ?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, data.getName());
            pstmt.setInt(2, data.getCoin());
            pstmt.setInt(3, data.getGem());
            pstmt.setString(4, data.getLastAttendance());
            pstmt.setString(5, uuid.toString());
            pstmt.executeUpdate();

            plugin.getLogger().info(data.getName() + " Data Save Complete");
        } catch (SQLException e) {
            plugin.getLogger().info(data.getName() + " Data Save Failed");
            e.printStackTrace();
        }
    }

    public void saveAllPlayers() {
        plugin.getLogger().info("All Player Data Saving...");
        for (UUID uuid : cache.keySet()) {
            savePlayer(uuid);
        }
        plugin.getLogger().info("All Player Data Save Complete");
    }

    public void unloadPlayer(UUID uuid) {
        savePlayer(uuid);
        cache.remove(uuid);
    }

    public void addCoin(UUID uuid, int amount) {
        PlayerData data = cache.get(uuid);
        if (data != null) {
            data.setCoin(data.getCoin() + amount);
        }
    }

    public boolean subtractCoin(UUID uuid, int amount) {
        PlayerData data = cache.get(uuid);
        if (data != null && data.getCoin() >= amount) {
            data.setCoin(data.getCoin() - amount);
            return true;
        }
        return false;
    }

    public void addGem(UUID uuid, int amount) {
        PlayerData data = cache.get(uuid);
        if (data != null) {
            data.setGem(data.getGem() + amount);
        }
    }

    public boolean subtractGem(UUID uuid, int amount) {
        PlayerData data = cache.get(uuid);
        if (data != null && data.getGem() >= amount) {
            data.setGem(data.getGem() - amount);
            return true;
        }
        return false;
    }

    public boolean checkAttendance(UUID uuid, Player player) {
        PlayerData data = cache.get(uuid);
        if (data == null) return false;

        String lastAttendance = data.getLastAttendance();
        LocalDate today = LocalDate.now(KOREA_ZONE);

        if (lastAttendance != null) {
            LocalDate lastDate = LocalDate.parse(lastAttendance);
            if (today.equals(lastDate)) {
                return false;
            }
        }
        data.setLastAttendance(LocalDate.now(KOREA_ZONE).toString());
        return true;
    }
}
