package zaman.plugin.smp.utility;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SendMessageUtil {
    public static void sendError(Player player, String message) {
        player.sendMessage(message);
        player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
    }

    public static void sendSuccess(Player player, String message) {
        player.sendMessage(message);
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
    }
}
