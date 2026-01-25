package zaman.plugin.smp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import zaman.plugin.smp.data.DatabaseManager;
import zaman.plugin.smp.data.PlayerDataManager;

public final class Smp extends JavaPlugin {
    private DatabaseManager databaseManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        // 데이터베이스 관리
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        databaseManager = new DatabaseManager(this);
        databaseManager.connect();
        databaseManager.createTables();

        playerDataManager = new PlayerDataManager(databaseManager);

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            playerDataManager.saveAllPlayers();
        }, 6000L, 6000L);
    }

    @Override
    public void onDisable() {
        playerDataManager.saveAllPlayers();
    }
}
