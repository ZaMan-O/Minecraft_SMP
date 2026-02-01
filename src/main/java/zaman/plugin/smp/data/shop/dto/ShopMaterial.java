package zaman.plugin.smp.data.shop.dto;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import zaman.plugin.smp.utility.NumberFormatter;

import java.util.Arrays;

public class ShopMaterial extends ShopItemType{
    private final Material material;
    private final int sellPrice;
    private final int buyPrice;

    public ShopMaterial(Material material, int sellPrice, int buyPrice) {
        this.material = material;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.setMaterialType(true);
    }

    public Material getMaterial() { return material; }
    public int getSellPrice() { return sellPrice; }
    public int getBuyPrice() { return buyPrice; }

    public ItemStack getDisplayItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(
                "§a판매: §f" + NumberFormatter.format(sellPrice) + "\uE001",
                "§c구매: §f" + NumberFormatter.format(buyPrice) + "\uE001"
        ));
        item.setItemMeta(meta);
        return item;
    }
}
