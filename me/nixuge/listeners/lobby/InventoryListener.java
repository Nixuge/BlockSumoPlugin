package me.nixuge.listeners.lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.nixuge.objects.KitEdit;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        KitEdit kitEdit = KitEdit.getFromPlayer((Player)event.getPlayer());
        if (kitEdit == null) return;
        kitEdit.onInventoryClose();
    }

    @EventHandler
    public void onInventoryChange(InventoryClickEvent event) {
        KitEdit kitEdit = KitEdit.getFromPlayer((Player)event.getWhoClicked());
        if (kitEdit == null) return;

        Inventory inv = event.getClickedInventory();
        if (inv == null) return;
        
        ItemStack item = inv.getItem(event.getSlot());
        if (item == null) return;

        switch (item.getType()) {
            case DIAMOND_SWORD:
                kitEdit.saveKit();
                event.setCancelled(true);
                break;
            case BARRIER:
                kitEdit.closeInventory();
                event.setCancelled(true);
                break;
            default:
                break;
        }
        
    }
}
