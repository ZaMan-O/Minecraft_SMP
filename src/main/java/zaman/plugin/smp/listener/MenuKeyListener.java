package zaman.plugin.smp.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import zaman.plugin.smp.utility.SendMessageUtil;
import zaman.plugin.smp.utility.InventoryMenu;

import java.util.HashMap;
import java.util.UUID;

public class MenuKeyListener implements Listener {
    private final JavaPlugin plugin;
    private final HashMap<UUID, BukkitRunnable> activeTeleports = new HashMap<>();

    public MenuKeyListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        if (player.isSneaking()) {
            event.setCancelled(true);

            Inventory menu = InventoryMenu.createMenu();
            player.openInventory(menu);
        }
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("§a메인 메뉴")) {
            return;
        }
        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        int slot = event.getRawSlot();
        switch (slot) {
            case 10 -> {
                player.closeInventory();
                startTeleport(player, Bukkit.getWorld("world"));
            }
            case 12 -> {
                player.closeInventory();
                startTeleport(player, Bukkit.getWorld("spawn"));
            }
            case 14 -> {
                player.closeInventory();
                startLocationTeleport(player, new Location(Bukkit.getWorld("spawn"), 621, 60, 635, 180, 0));
            }
            case 16 -> {
                player.closeInventory();
            }
            default -> {
            }
        }
    }

    private void startTeleport(Player player, World world) {
        UUID uuid = player.getUniqueId();

        if (activeTeleports.containsKey(uuid)) {
            activeTeleports.get(uuid).cancel();
            activeTeleports.remove(uuid);
            SendMessageUtil.sendError(player, "§c이전 텔레포트가 취소되었습니다.");
        }

        Location startLoc = player.getLocation().clone();

        BukkitRunnable task = new BukkitRunnable() {
            int countdown = 5;

            @Override
            public void run() {
                Location currentLoc = player.getLocation();
                if (currentLoc.getX() != startLoc.getX() ||
                        currentLoc.getY() != startLoc.getY() ||
                        currentLoc.getZ() != startLoc.getZ()) {
                    player.sendTitle("§c텔레포트 취소됨", "§c움직여서 텔레포트가 취소되었습니다!", 0, 30, 10);
                    activeTeleports.remove(uuid);
                    this.cancel();
                    return;
                }

                if (countdown > 0) {
                    player.sendTitle("§b" + countdown + "초 후 이동합니다.", "§c움직이면 취소됩니다!", 0, 30, 10);
                    player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);
                    countdown--;
                } else {
                    player.teleport(world.getSpawnLocation());
                    player.sendTitle("§a성공적으로 이동하였습니다!", "", 0, 30, 10);
                    player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                    activeTeleports.remove(uuid);
                    this.cancel();
                }
            }
        };

        activeTeleports.put(uuid, task);
        task.runTaskTimer(plugin, 0L, 20L);
        player.sendTitle("§b5초 후 이동합니다.", "§c움직이면 취소됩니다!", 0, 30, 10);
    }

    private void startLocationTeleport(Player player, Location location) {
        UUID uuid = player.getUniqueId();

        if (activeTeleports.containsKey(uuid)) {
            activeTeleports.get(uuid).cancel();
            activeTeleports.remove(uuid);
            SendMessageUtil.sendError(player, "§c이전 텔레포트가 취소되었습니다.");
        }

        Location startLoc = player.getLocation().clone();

        BukkitRunnable task = new BukkitRunnable() {
            int countdown = 5;

            @Override
            public void run() {
                Location currentLoc = player.getLocation();
                if (currentLoc.getX() != startLoc.getX() ||
                        currentLoc.getY() != startLoc.getY() ||
                        currentLoc.getZ() != startLoc.getZ()) {

                    player.sendTitle("§c텔레포트 취소됨", "§c움직여서 텔레포트가 취소되었습니다!", 0, 30, 10);
                    activeTeleports.remove(uuid);
                    this.cancel();
                    return;
                }

                if (countdown > 0) {
                    player.sendTitle("§b" + countdown + "초 후 이동합니다.", "§c움직이면 취소됩니다!", 0, 30, 10);
                    player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);
                    countdown--;
                } else {
                    player.teleport(location);
                    player.sendTitle("§a성공적으로 이동하였습니다!", "", 0, 30, 10);
                    player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                    activeTeleports.remove(uuid);
                    this.cancel();
                }
            }
        };

        activeTeleports.put(uuid, task);
        task.runTaskTimer(plugin, 0L, 20L);
        player.sendTitle("§b5초 후 이동합니다.", "§c움직이면 취소됩니다!", 0, 30, 10);
    }
}
