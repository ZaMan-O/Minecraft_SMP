package zaman.plugin.smp.data.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import zaman.plugin.smp.data.shop.dto.ShopItemStack;
import zaman.plugin.smp.data.shop.dto.ShopMaterial;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ShopData {
    public static final ShopMaterial[] FARM_ITEMS = {
            new ShopMaterial(Material.WHEAT, 15, 200),
            new ShopMaterial(Material.CARROT, 4, 100),
            new ShopMaterial(Material.POTATO, 4, 100)
    };

    public static final ShopItemStack[] FARM_ITEMS2 = {
            new ShopItemStack(createItem("§dTest1", List.of("ddd", "§adddd")), 15, 1),
            new ShopItemStack(createItem("§dTest2", List.of("dd", "§adwgd")), 15, 200),
    };

    private static ItemStack createItem(String displayName, List<String> lore) {
        ItemStack item = new ItemStack(Material.ACACIA_BOAT);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }
}
