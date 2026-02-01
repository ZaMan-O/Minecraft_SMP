package zaman.plugin.smp.utility;

public class ChatPrefixColor {
    public static String getPrefixColor(int playTimeHours) {
        String chatColor = "§f";
        if (playTimeHours >= 600) {
            chatColor = "§e§l";
        } else if (playTimeHours >= 480) {
            chatColor = "§c§l";
        } else if (playTimeHours >= 440) {
            chatColor = "§b§l";
        } else if (playTimeHours >= 400) {
            chatColor = "§d§l";
        } else if (playTimeHours >= 360) {
            chatColor = "§6§l";
        } else if (playTimeHours >= 320) {
            chatColor = "§5§l";
        } else if (playTimeHours >= 280) {
            chatColor = "§9§l";
        } else if (playTimeHours >= 240) {
            chatColor = "§a§l";
        } else if (playTimeHours >= 200) {
            chatColor = "§f§l";
        } else if (playTimeHours >= 160) {
            chatColor = "§7§l";
        } else if (playTimeHours >= 130) {
            chatColor = "§e";
        } else if (playTimeHours >= 100) {
            chatColor = "§c";
        } else if (playTimeHours >= 70) {
            chatColor = "§b";
        } else if (playTimeHours >= 50) {
            chatColor = "§d";
        } else if (playTimeHours >= 35) {
            chatColor = "§6";
        } else if (playTimeHours >= 24) {
            chatColor = "§5";
        } else if (playTimeHours >= 15) {
            chatColor = "§9";
        } else if (playTimeHours >= 10) {
            chatColor = "§a";
        } else if (playTimeHours >= 5) {
            chatColor = "§f";
        } else if (playTimeHours >= 2) {
            chatColor = "§7";
        }
        return chatColor;
    }
}
