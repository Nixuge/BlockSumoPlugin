package me.nixuge.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.nixuge.objects.Kit;

public class KitEdit {
    //bit different approach to other classes
    //here the list is as a static inside the class itself
    //not in another manager object
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
        Inventory inv = Bukkit.createInventory(p, 0, "Please organize your"); 
        p.openInventory(inv);

        Inventory playerInventory = p.getInventory();
        playerInventory.clear();

        Kit currentKit = Kit.loadKit(p.getName());
        if (!isInventoryValid(currentKit.getItems())) { //in case the kit is invalid/corrupted
            Kit.loadDefaultKit().saveKit(p);
            currentKit = Kit.loadKit(p.getName());
        }

        currentKit.useKit(p);
        
        playerInventory.setItem(17, ItemUtils.getItemStack(Material.DIAMOND_SWORD, "§aSave kit"));
        playerInventory.setItem(16, ItemUtils.getItemStack(Material.BARRIER, "§cCancel kit edit"));
    }

    private boolean isInventoryValid(ItemStack[] items) {
        Map<Material, Integer> possibleMaterials = new HashMap<>();
        possibleMaterials.put(Material.WOOL, 1);
        possibleMaterials.put(Material.SHEARS, 1);

        Map<Material, Integer> inventoryMaterials = new HashMap<>();
        
        for (int i=0; i < items.length; i++) {
            ItemStack item = items[i];
            if (item == null) continue;

            int amount = item.getAmount();
            Material mat = item.getType();
            
            if (i > 8) {
                return false;
            }
            int currentMatQuantity = inventoryMaterials.containsKey(mat) ?
                inventoryMaterials.get(mat) + amount : amount;

            inventoryMaterials.put(mat, currentMatQuantity);


        }
        return (possibleMaterials.equals(inventoryMaterials));
    }

    public void saveKit() {
        Inventory playerInventory = p.getInventory();

        ItemStack[] items = playerInventory.getContents();
        //override the barrier & diamond sword items
        items[16] = null; 
        items[17] = null;

        if (isInventoryValid(items)) {
            success = true;
            closeInventory();
            new Kit(items).saveKit(p);
        } else {
            p.sendMessage("§cInvalid kit !");
        }
    }
    
    public void closeInventory() {
        p.closeInventory();
    }

    public void onInventoryClose() {
        //note: to be called only on inventory close
        String str = success ?
            "§aSaved kit !" :
            "§cKit edit cancelled !";
        p.sendMessage(str);
        p.getInventory().clear();
        kitEdits.remove(this);
    }
}
