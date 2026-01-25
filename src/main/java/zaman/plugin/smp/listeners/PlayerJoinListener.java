package zaman.plugin.smp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import zaman.plugin.smp.data.PlayerDataManager;

public class PlayerJoinListener implements Listener {
    private final PlayerDataManager playerDataManager;

    public PlayerJoinListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        playerDataManager.loadPlayer(
                event.getPlayer().getUniqueId(),
                event.getPlayer().getName()
        );
    }
}
