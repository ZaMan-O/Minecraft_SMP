package zaman.plugin.smp.listener;

import de.oliver.fancynpcs.api.events.NpcInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import zaman.plugin.smp.data.shop.ShopData;
import zaman.plugin.smp.data.shop.ShopTitle;
import zaman.plugin.smp.utility.InventoryMenu;

public class NPCListener implements Listener {

    // 농부
    @EventHandler
    public void onNPCRightClick(NpcInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getNpc().getData().getName().equals("farmer")) {
            Inventory menu = InventoryMenu.createShopMenu(ShopData.FARM_ITEMS, ShopTitle.FARM.getName());
            player.openInventory(menu);
        } else if (event.getNpc().getData().getName().equals("blacksmith")) {
        }
    }
}