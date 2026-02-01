package zaman.plugin.smp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import zaman.plugin.smp.data.DatabaseManager;
import zaman.plugin.smp.data.PlayerDataManager;
import zaman.plugin.smp.listener.*;
import zaman.plugin.smp.utility.ScoreboardUtil;

public final class Smp extends JavaPlugin {
    private DatabaseManager databaseManager;
    private PlayerDataManager playerDataManager;
    private ScoreboardUtil scoreboardUtil;

    @Override
    public void onEnable() {
        // 데이터베이스 관리
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        databaseManager = new DatabaseManager(this);
        databaseManager.connect();
        databaseManager.createTables();

        playerDataManager = new PlayerDataManager(databaseManager, this);

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            playerDataManager.saveAllPlayers();
        }, 6000L, 6000L);

        scoreboardUtil = new ScoreboardUtil(playerDataManager);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerDataManager, scoreboardUtil), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new NoExplosionDamageListener(), this);
        getServer().getPluginManager().registerEvents(new BanItemListener(), this);
        getServer().getPluginManager().registerEvents(new MenuKeyListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new NPCListener(), this);
        getServer().getPluginManager().registerEvents(new ShopClickListener(playerDataManager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(playerDataManager), this);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    scoreboardUtil.updateStatsScoreboard(player);
                }
            }
        }.runTaskTimer(this, 0, 20L);
    }

    @Override
    public void onDisable() {
        playerDataManager.saveAllPlayers();

        databaseManager.disconnect();
    }
}
