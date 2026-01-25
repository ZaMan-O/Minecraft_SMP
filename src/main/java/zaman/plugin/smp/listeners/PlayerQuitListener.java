package zaman.plugin.smp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import zaman.plugin.smp.data.PlayerDataManager;

public class PlayerQuitListener implements Listener {
    private final PlayerDataManager playerDataManager;

    public PlayerQuitListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerDataManager.unloadPlayer(event.getPlayer().getUniqueId());
    }
}
