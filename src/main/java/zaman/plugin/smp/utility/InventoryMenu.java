package zaman.plugin.smp.utility;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import zaman.plugin.smp.data.shop.dto.ShopItemStack;
import zaman.plugin.smp.data.shop.dto.ShopMaterial;
import zaman.plugin.smp.listener.PlayerDeathListener;

import java.util.Arrays;

public class InventoryMenu {
    public static Inventory createMenu() {
        Inventory inv = Bukkit.createInventory(null, 54, "§a메인 메뉴");
        ItemStack yasang = createItem(Material.MYCELIUM, "\uE004 §c야생으로 이동",
                "§f클릭하여 야생으로 이동하세요!",
                "§c주의 : 약탈 가능성이 있으며, 인벤토리가 유지되지 않습니다.");
        ItemStack spawn = createItem(Material.MOSSY_STONE_BRICKS, "\uE006 §a스폰으로 이동",
                "§f클릭하여 스폰으로 이동하세요!",
                "§c주의 : 5초동안 이동하지 않으면 이동됩니다.");
        ItemStack armsijang = createItem(Material.POLISHED_BLACKSTONE_BRICKS, "\uE005 §7암시장으로 이동",
                "§f클릭하여 암시장으로 이동하세요!",
                "§aPVP가 막혀있어 안전한 재화 거래가 가능합니다.");

        ItemStack hiddenList = createItem(Material.WRITABLE_BOOK, "§d히든 요소 보기",
                "§f클릭하여 서버 내 히든 요소를 확인하세요!",
                "§e최초로 히든 요소를 발견 시 상금을 얻을 수 있습니다.");

        String killer = PlayerDeathListener.getLastKiller();
        String victim = PlayerDeathListener.getLastVictim();
        ItemStack killerHead = createPlayerHead(killer, "§f" + killer,
                "§c가장 최근에 죽인 사람");
        ItemStack victimHead = createPlayerHead(victim, "§f" + victim,
                "§c가장 최근에 죽은 사람");

        inv.setItem(10, yasang);
        inv.setItem(12, spawn);
        inv.setItem(14, armsijang);
        inv.setItem(16, hiddenList);

        inv.setItem(28,killerHead);
        inv.setItem(29,new ItemStack(Material.DIAMOND_SPEAR));

        inv.setItem(42,victimHead);

        inv.setItem(36,new ItemStack(Material.GRASS_BLOCK));
        inv.setItem(37,new ItemStack(Material.GRASS_BLOCK));
        inv.setItem(38,new ItemStack(Material.GRASS_BLOCK));
        inv.setItem(39,new ItemStack(Material.GRASS_BLOCK));
        inv.setItem(40,new ItemStack(Material.GRASS_BLOCK));
        inv.setItem(45,new ItemStack(Material.DIRT));
        inv.setItem(46,new ItemStack(Material.DIRT));
        inv.setItem(47,new ItemStack(Material.DIRT));
        inv.setItem(48,new ItemStack(Material.DIRT));
        inv.setItem(49,new ItemStack(Material.DIRT));
        inv.setItem(50,new ItemStack(Material.GRASS_BLOCK));
        inv.setItem(51,new ItemStack(Material.GRASS_BLOCK));
        inv.setItem(52,new ItemStack(Material.GRASS_BLOCK));
        inv.setItem(53,new ItemStack(Material.GRASS_BLOCK));
        inv.setItem(43,new ItemStack(Material.CHEST));

        return inv;
    }

    public static Inventory createShopMenu(ShopMaterial[] items, String title) {
        Inventory inv = Bukkit.createInventory(null, 54, title);
        for (int i=0;i<items.length;i++) {
            inv.setItem(i, items[i].getDisplayItem());
        }
        return inv;
    }

    public static Inventory createItemStackShopMenu(ShopItemStack[] items, String title) {
        Inventory inv = Bukkit.createInventory(null, 54, title);
        for (int i=0;i<items.length;i++) {
            inv.setItem(i, items[i].getDisplayItem());
        }
        return inv;
    }

    private static ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            if (lore.length > 0) {
                meta.setLore(Arrays.asList(lore));
            }
            item.setItemMeta(meta);
        }

        return item;
    }

    private static ItemStack createPlayerHead(String playerName, String displayName, String... lore) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        if (meta != null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
            meta.setOwningPlayer(offlinePlayer);
            meta.setDisplayName(displayName);
            if (lore.length > 0) {
                meta.setLore(Arrays.asList(lore));
            }
            skull.setItemMeta(meta);
        }

        return skull;
    }
}
