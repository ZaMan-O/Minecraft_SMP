package zaman.plugin.smp.data.shop.dto;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import zaman.plugin.smp.utility.NumberFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopItemStack extends ShopItemType{
    private final ItemStack itemStack;
    private final int sellPrice;
    private final int buyPrice;

    public ShopItemStack(ItemStack itemStack, int sellPrice, int buyPrice) {
        this.itemStack = itemStack;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.setMaterialType(false);
    }

    public ItemStack getItemStack() { return itemStack; }
    public int getSellPrice() { return sellPrice; }
    public int getBuyPrice() { return buyPrice; }

    public ItemStack getDisplayItem() {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        lore.add("§a판매: §f" + NumberFormatter.format(sellPrice) + "\uE001");
        lore.add("§c구매: §f" + NumberFormatter.format(buyPrice) + "\uE001");
        meta.setLore(lore);
        ItemStack cloned = itemStack.clone();
        cloned.setItemMeta(meta);
        return cloned;
    }
}
