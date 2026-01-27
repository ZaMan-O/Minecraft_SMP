package zaman.plugin.smp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import zaman.plugin.smp.data.PlayerDataManager;
import zaman.plugin.smp.utility.ScoreboardUtil;

public class PlayerJoinListener implements Listener {
    private final PlayerDataManager playerDataManager;
    private final ScoreboardUtil scoreboardUtil;

    public PlayerJoinListener(PlayerDataManager playerDataManager, ScoreboardUtil scoreboardUtil) {
        this.playerDataManager = playerDataManager;
        this.scoreboardUtil = scoreboardUtil;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        playerDataManager.loadPlayer(
                event.getPlayer().getUniqueId(),
                event.getPlayer().getName()
        );
        scoreboardUtil.createStatsScoreboard(event.getPlayer());
    }
}
