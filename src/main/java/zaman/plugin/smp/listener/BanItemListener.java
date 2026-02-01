package zaman.plugin.smp.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import zaman.plugin.smp.utility.SendMessageUtil;

import java.util.Set;

public class BanItemListener implements Listener {
    private final Set<Material> bannedItems = Set.of(
            Material.ENCHANTING_TABLE,
            Material.ANVIL,
            Material.CHIPPED_ANVIL,
            Material.DAMAGED_ANVIL
    );

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        ItemStack result = event.getRecipe().getResult();

        if (bannedItems.contains(result.getType())) {
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player player) {
                SendMessageUtil.sendError(player, "§c이 아이템은 제작할 수 없습니다.");
            }
        }
    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        ItemStack item = event.getItem().getItemStack();

        if (bannedItems.contains(item.getType())) {
            event.setCancelled(true);
            SendMessageUtil.sendError(player, "§c이 아이템은 주울 수 없습니다.");
            event.getItem().remove();
        }
    }

    @EventHandler
    public void onPlaceItem(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();

        if (bannedItems.contains(item.getType())) {
            event.setCancelled(true);
            SendMessageUtil.sendError(event.getPlayer(), "§c이 아이템은 설치할 수 없습니다.");
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.ANVIL || event.getInventory().getType() == InventoryType.ENCHANTING) {
            event.setCancelled(true);
            SendMessageUtil.sendError((Player) event.getPlayer(), "§c사용할 수 없습니다.");
        }
    }
}
