package me.nixuge.objects;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.nixuge.BlockSumo;
import me.nixuge.config.Lang;
import me.nixuge.utils.ItemUtils;

public class Kit {
    private static BlockSumo plugin = BlockSumo.getInstance();

    public static boolean isInventoryValid(ItemStack[] items) {
        //possible materials hardcoded
        //note: this inventory allows inventories of more than 8 size,
        //however if anything not null is inside the slots at index 9+
        //it'll return false
        Map<Material, Integer> possibleMaterials = new HashMap<>();
        possibleMaterials.put(Material.WOOL, 1);
        possibleMaterials.put(Material.SHEARS, 1);

        Map<Material, Integer> inventoryMaterials = new HashMap<>();

        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];
            if (item == null)
                continue;

            int amount = item.getAmount();
            Material mat = item.getType();

            if (i > 8) {
                return false;
            }
            int currentMatQuantity = inventoryMaterials.containsKey(mat) ? inventoryMaterials.get(mat) + amount
                    : amount;

            inventoryMaterials.put(mat, currentMatQuantity);

        }
        return (possibleMaterials.equals(inventoryMaterials));
    }

    // thanks to https://bukkit.org/threads/saving-individual-players-kits.375693/
    // for the inspiration
    private static String folderPath = plugin.getDataFolder() + File.separator + "BlockSumo" + File.separator + "Kits";

    public static Kit loadDefaultKit() {
        ItemStack[] content = new ItemStack[36];
        content[1] = new ItemStack(Material.WOOL);
        content[2] = new ItemStack(Material.SHEARS);
        return new Kit(content);
    }

    public static Kit loadKit(Player player) {
        return loadKit(player.getName());
    }
    @SuppressWarnings("unchecked")
    public static Kit loadKit(String playerName) {
        String filename = playerName + ".yml";
        YamlConfiguration c = YamlConfiguration.loadConfiguration(new File(folderPath, filename));

        if (c.get("kitData") == null)
            return loadDefaultKit();

        ItemStack[] content = ((List<ItemStack>) c.get("kitData")).toArray(new ItemStack[0]);

        // in case the loaded kit is invalid/corrupted
        if (!isInventoryValid(content))
            return loadDefaultKit();

        return new Kit(content);
    }

    private ItemStack[] items;

    public Kit(ItemStack items[]) {
        this.items = Arrays.copyOfRange(items, 0, 9);
    }

    public void saveKit(Player p) {
        YamlConfiguration c = new YamlConfiguration();
        c.set("kitData", items);
        try {
            c.save(new File(folderPath, p.getName() + ".yml"));
        } catch (IOException e) {
            p.sendMessage(Lang.get("errors.kit.critical"));
            e.printStackTrace();
        }
    }

    public void useKit(Player p, boolean inGame) {
        // p.getInventory().setContents(items); unfortunately causes inventory update
        // issues & can't set 64 wool
        Inventory playerInventory = p.getInventory();
        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];
            if (item == null)
                continue;
            
            //"dirty" fixes on the fly but not really a choice here ngl
            if (inGame) {
                switch (item.getType()) {
                    case WOOL:
                        playerInventory.setItem(i, new ItemStack(item.getType(), 64));
                        break;
                    case SHEARS:
                        playerInventory.setItem(i, ItemUtils.setUnbreakable(item));
                        break;
                    default:
                        break;
                }
            } else {
                playerInventory.setItem(i, items[i]);
            }
        }
    }

    public ItemStack[] getItems() {
        return items;
    }
}
