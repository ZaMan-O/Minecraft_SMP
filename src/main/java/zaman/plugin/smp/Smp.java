package zaman.plugin.smp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import zaman.plugin.smp.data.DatabaseManager;
import zaman.plugin.smp.data.PlayerDataManager;
import zaman.plugin.smp.listeners.PlayerJoinListener;
import zaman.plugin.smp.listeners.PlayerQuitListener;

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

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerDataManager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(playerDataManager), this);
    }

    @Override
    public void onDisable() {
        playerDataManager.saveAllPlayers();

        databaseManager.disconnect();
    }
}
