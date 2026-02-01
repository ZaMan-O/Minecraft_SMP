package zaman.plugin.smp.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private static String lastKiller;
    private static String lastVictim;

    public PlayerDeathListener() {
        lastKiller = "zZaMan";
        lastVictim = "zZaMan";
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer != null) {
            String killerName = killer.getName();
            String victimName = victim.getName();

            lastKiller = killerName;
            lastVictim = victimName;

            Bukkit.broadcastMessage("§c사람이 죽었다.");
        }
    }

    public static String getLastKiller() {
        return lastKiller;
    }

    public static String getLastVictim() {
        return lastVictim;
    }
}
