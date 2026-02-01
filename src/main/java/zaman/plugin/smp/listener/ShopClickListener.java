package zaman.plugin.smp.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import zaman.plugin.smp.data.PlayerDataManager;
import zaman.plugin.smp.data.shop.ShopData;
import zaman.plugin.smp.data.shop.ShopTitle;
import zaman.plugin.smp.data.shop.dto.ShopItemStack;
import zaman.plugin.smp.data.shop.dto.ShopItemType;
import zaman.plugin.smp.data.shop.dto.ShopMaterial;
import zaman.plugin.smp.utility.SendMessageUtil;

public class ShopClickListener implements Listener {
    private final PlayerDataManager playerDataManager;

    public ShopClickListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onClickShopInventory(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ShopTitle.FARM.getName())) {
            event.setCancelled(true);
            if (!(event.getWhoClicked() instanceof Player player)) {
                return;
            }
            int slot = event.getRawSlot();
            if (event.isLeftClick()) {
                purchaseShopItem(ShopData.FARM_ITEMS, slot, player, true);

            } else if (event.isRightClick()) {
                purchaseShopItem(ShopData.FARM_ITEMS, slot, player, false);
            }
        }
    }

    public void purchaseShopItem(ShopItemType[] items, int slot, Player player, boolean sell) {
        ShopItemType item = items[slot];
        if (item.isMaterialType()) {
            ShopMaterial materialItem = (ShopMaterial) item;
            Material material = materialItem.getMaterial();
            int sellPrice = materialItem.getSellPrice();
            int buyPrice = materialItem.getBuyPrice();
            if (sell) {
                if (sellPrice == 0) {
                    SendMessageUtil.sendError(player, "§c판매가 불가능한 상품입니다.");
                    player.closeInventory();
                } else {
                    if (player.getInventory().contains(material)) {
                        player.getInventory().removeItem(new ItemStack(material, 1));

                        playerDataManager.addCoin(player.getUniqueId(), sellPrice);
                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    }
                }
            } else {
                if (buyPrice == 0) {
                    SendMessageUtil.sendError(player, "§c구매가 불가능한 상품입니다.");
                    player.closeInventory();
                } else {
                    if(!isInventoryFull(player)) {
                        if (playerDataManager.subtractCoin(player.getUniqueId(), buyPrice)) {
                            player.getInventory().addItem(new ItemStack(material, 1));
                        } else {
                            SendMessageUtil.sendError(player, "§c돈이 부족합니다.");
                            player.closeInventory();
                        }
                    } else {
                        SendMessageUtil.sendError(player, "§c인벤토리가 부족합니다.");
                        player.closeInventory();
                    }
                }
            }
        } else {
            ShopItemStack itemStackItem = (ShopItemStack) item;
            ItemStack itemStack = itemStackItem.getItemStack();
            int sellPrice = itemStackItem.getSellPrice();
            int buyPrice = itemStackItem.getBuyPrice();
            if (sell) {
                if (sellPrice == 0) {
                    SendMessageUtil.sendError(player, "§c판매가 불가능한 상품입니다.");
                    player.closeInventory();
                } else {
                    if (player.getInventory().contains(itemStack)) {
                        player.getInventory().removeItem(itemStack);

                        playerDataManager.addCoin(player.getUniqueId(), sellPrice);
                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    }
                }
            } else {
                if (buyPrice == 0) {
                    SendMessageUtil.sendError(player, "§c구매가 불가능한 상품입니다.");
                    player.closeInventory();
                } else {
                    if(!isInventoryFull(player)) {
                        if (playerDataManager.subtractCoin(player.getUniqueId(), buyPrice)) {
                            player.getInventory().addItem(itemStack);
                        } else {
                            SendMessageUtil.sendError(player, "§c돈이 부족합니다.");
                            player.closeInventory();
                        }
                    } else {
                        SendMessageUtil.sendError(player, "§c인벤토리가 부족합니다.");
                        player.closeInventory();
                    }
                }
            }
        }
    }

    public boolean isInventoryFull(Player player) {
        for (ItemStack item : player.getInventory().getStorageContents()) {
            if (item == null || item.getType() == Material.AIR) {
                return false;
            }
        }
        return true;
    }
}
