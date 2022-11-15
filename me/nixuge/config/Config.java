package me.nixuge.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Config {
    public static void saveKit(Player p, String kitName) throws IOException {
        YamlConfiguration c = new YamlConfiguration();
        c.set("kits." + kitName + ".armor", p.getInventory().getArmorContents());
        c.set("kits." + kitName + ".content", p.getInventory().getContents());
        c.save(new File("plugins/" + File.separator + "PluginName" + File.separator + "Kits",
                p.getUniqueId() + ".yml"));
    }

    @SuppressWarnings("unchecked")
    public static void useKit(Player p, String kitName) throws IOException {
        YamlConfiguration c = YamlConfiguration.loadConfiguration(new File(
                "plugins/" + File.separator + "PluginName" + File.separator + "Kits", p.getUniqueId() + ".yml"));
        ItemStack[] content = ((List<ItemStack>) c.get("kits." + kitName + ".armor")).toArray(new ItemStack[0]);
        p.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) c.get("kits." + kitName + ".content")).toArray(new ItemStack[0]);
        p.getInventory().setContents(content);

    }
}
