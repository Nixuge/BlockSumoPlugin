package me.nixuge.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.nixuge.config.Lang;
import me.nixuge.objects.Kit;

public class KitEdit {
    // bit different approach to other classes
    // here the list is as a static inside the class itself
    // not in another manager object
    private static List<KitEdit> kitEdits = new ArrayList<>();

    public static void addKitEdit(KitEdit kitEdit) {
        kitEdits.add(kitEdit);
    }

    public static KitEdit getFromPlayer(Player p) {
        for (KitEdit k : kitEdits) {
            if (k.getPlayer() == p) {
                return k;
            }
        }
        return null;
    }

    private final Player p;
    private boolean success;

    public KitEdit(Player p) {
        this.p = p;
        kitEdits.add(this);
    }

    public Player getPlayer() {
        return p;
    }

    public void spawnInventory() {
        Inventory inv = Bukkit.createInventory(p, 0, Lang.get("kit.organiseinventory"));
        p.openInventory(inv);

        Inventory playerInventory = p.getInventory();
        playerInventory.clear();

        Kit currentKit = Kit.loadKit(p);
        currentKit.useKit(p, false);

        playerInventory.setItem(17, ItemUtils.getItemStack(Material.DIAMOND_SWORD, Lang.get("kit.savekit")));
        ItemStack cancelItem = ItemUtils.getItemStack(Material.REDSTONE_BLOCK, Lang.get("kit.cancelkit"));

        playerInventory.setItem(16, cancelItem);

    }

    public void saveKit() {
        Inventory playerInventory = p.getInventory();

        ItemStack[] items = playerInventory.getContents();
        // override the barrier & diamond sword items
        items[16] = null;
        items[17] = null;

        if (Kit.isInventoryValid(items)) {
            success = true;
            closeInventory();
            new Kit(items).saveKit(p);
        } else {
            p.sendMessage(Lang.get("kit.invalidkit"));
        }
    }

    public void closeInventory() {
        p.closeInventory();
    }

    public void onInventoryClose() {
        // note: to be called only on inventory close
        String str = success ? Lang.get("kit.savedkit") : Lang.get("kit.cancelledkit");
        p.sendMessage(str);
        p.getInventory().clear();
        kitEdits.remove(this);
    }

    public void removeKitEdit() {
        kitEdits.remove(this);
    }
}
