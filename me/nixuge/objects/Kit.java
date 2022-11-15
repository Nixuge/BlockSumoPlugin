package me.nixuge.objects;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Kit {
    // thanks to https://bukkit.org/threads/saving-individual-players-kits.375693/
    // for the inspiration
    private static String folderPath = "plugins" + File.separator + "BlockSumo" + File.separator + "Kits";

    public static Kit loadDefaultKit() {
        ItemStack[] content = new ItemStack[36];
        content[1] = new ItemStack(Material.WOOL);
        content[2] = new ItemStack(Material.SHEARS);
        return new Kit(content);
    }

    @SuppressWarnings("unchecked")
    public static Kit loadKit(String playerName) {
        String filename = playerName + ".yml";
        YamlConfiguration c = YamlConfiguration.loadConfiguration(new File(folderPath, filename));

        if (c.get("kitData") == null)
            return loadDefaultKit();

        ItemStack[] content = ((List<ItemStack>) c.get("kitData")).toArray(new ItemStack[0]);
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
            p.sendMessage("§l§4Critical error happened. Please report.");
            e.printStackTrace();
        }
    }

    public void useKit(Player p) {
        // p.getInventory().setContents(items); unfortunately causes inventory update issues
        Inventory playerInventory = p.getInventory();
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null)
                continue;
            playerInventory.setItem(i, items[i]);
        }
    }

    public ItemStack[] getItems() {
        return items;
    }
}
