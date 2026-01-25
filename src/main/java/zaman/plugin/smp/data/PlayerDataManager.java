package zaman.plugin.smp.data;

import org.bukkit.entity.Player;
import zaman.plugin.smp.data.dto.PlayerData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {
    private final DatabaseManager db;
    private final HashMap<UUID, PlayerData> cache;

    public PlayerDataManager(DatabaseManager db) {
        this.db = db;
        this.cache = new HashMap<>();
    }

    public void loadPlayer(UUID uuid, String name) {
        String sql = "SELECT * FROM player_data WHERE uuid = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, uuid.toString());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                PlayerData data = new PlayerData(
                        rs.getString("name"),
                        rs.getInt("cash"),
                        rs.getInt("coin"),
                        rs.getInt("orb"),
                        rs.getString("last_attendance")
                );
                cache.put(uuid, data);
                System.out.println(name + " 데이터 로드 완료");
            } else {
                createNewPlayer(uuid, name);

                PlayerData data = new PlayerData(name, 0, 0, 0, null);
                cache.put(uuid, data);
                System.out.println(name + " 신규 플레이어 생성");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createNewPlayer(UUID uuid, String name) {
        String sql = "INSERT INTO player_data (uuid, name) VALUES (?, ?)";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerData getPlayerData(UUID uuid) {
        return cache.get(uuid);
    }

    public void savePlayer(UUID uuid) {
        PlayerData data = cache.get(uuid);
        if (data == null) return;

        String sql = "UPDATE player_data SET name = ?, cash = ?, coin = ?, orb = ?, last_attendance = ? WHERE uuid = ?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, data.getName());
            pstmt.setInt(2, data.getCash());
            pstmt.setInt(3, data.getCoin());
            pstmt.setInt(4, data.getOrb());
            pstmt.setString(5, data.getLastAttendance());
            pstmt.setString(6, uuid.toString());
            pstmt.executeUpdate();

            System.out.println(data.getName() + " 데이터 저장 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveAllPlayers() {
        System.out.println("전체 플레이어 데이터 저장 시작");
        for (UUID uuid : cache.keySet()) {
            savePlayer(uuid);
        }
        System.out.println("전체 플레이어 데이터 저장 완료");
    }

    public void unloadPlayer(UUID uuid) {
        savePlayer(uuid);
        cache.remove(uuid);
    }

    public void addCash(UUID uuid, int amount) {
        PlayerData data = cache.get(uuid);
        if (data != null) {
            data.setCash(data.getCash() + amount);
        }
    }

    public boolean subtractCash(UUID uuid, int amount) {
        PlayerData data = cache.get(uuid);
        if (data != null && data.getCash() >= amount) {
            data.setCash(data.getCash() - amount);
            return true;
        }
        return false;
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

    public void addOrb(UUID uuid, int amount) {
        PlayerData data = cache.get(uuid);
        if (data != null) {
            data.setOrb(data.getOrb() + amount);
        }
    }

    public boolean subtractOrb(UUID uuid, int amount) {
        PlayerData data = cache.get(uuid);
        if (data != null && data.getOrb() >= amount) {
            data.setOrb(data.getOrb() - amount);
            return true;
        }
        return false;
    }

    public boolean checkAttendance(UUID uuid, Player player) {
        PlayerData data = cache.get(uuid);
        if (data == null) return false;

        String lastAttendance = data.getLastAttendance();
        LocalDate today = LocalDate.now();

        if (lastAttendance != null) {
            LocalDate lastDate = LocalDate.parse(lastAttendance);
            if (today.equals(lastDate)) {
                return false;
            }
        }
        return true;
    }
}
