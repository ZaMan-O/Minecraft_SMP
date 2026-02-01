package zaman.plugin.smp.listener;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import zaman.plugin.smp.utility.ChatPrefixColor;

public class PlayerChatListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerChattingPrefix(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        int playTimeTicks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);

        int playTimeHours = playTimeTicks / 72000;
        int playTimeMinutes = (playTimeTicks % 72000) / 1200;

        String chatColor = ChatPrefixColor.getPrefixColor(playTimeHours);

        event.setFormat(String.format("%s%d.%02dh <%s> §f%s",
                chatColor, playTimeHours, playTimeMinutes, player.getName(), event.getMessage()));
    }
}
