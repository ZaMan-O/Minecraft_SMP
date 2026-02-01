package zaman.plugin.smp.utility;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import zaman.plugin.smp.data.PlayerDataManager;

public class ScoreboardUtil {
    private final PlayerDataManager playerDataManager;

    public ScoreboardUtil(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    public void createStatsScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(
                "stats",
                "dummy",
                "\uE004 §4하드코어 §c반야생 서버 §f\uE004"
        );
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
    }

    public void updateStatsScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("stats");

        if(objective == null) {
            createStatsScoreboard(player);
            return;
        }

        for(String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }

        String name = player.getName();
        int coin = playerDataManager.getPlayerData(player.getUniqueId()).getCoin();
        int gem = playerDataManager.getPlayerData(player.getUniqueId()).getGem();

        objective.getScore("\uE003 " + name).setScore(14);
        objective.getScore(" ").setScore(13);
        objective.getScore("\uE001 " + NumberFormatter.format(coin)).setScore(12);
        objective.getScore("\uE002 " + NumberFormatter.format(gem)).setScore(11);
    }
}
