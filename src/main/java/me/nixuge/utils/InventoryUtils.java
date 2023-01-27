package me.nixuge.utils;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.Kit;
import me.nixuge.utils.item.ItemBuilder;

public class InventoryUtils {
    public static void setupInventories(List<BsPlayer> players) {
        players.forEach(player -> setupInventory(player));
    }

    public static void setupInventory(BsPlayer bsPlayer) {
        Player p = bsPlayer.getBukkitPlayer();
        p.getInventory().clear();

        bsPlayer.setKit(Kit.loadKit(p));
        bsPlayer.getKit().useKit(p, true);

        Material[] materials = {
                Material.LEATHER_BOOTS,
                Material.LEATHER_LEGGINGS,
                Material.LEATHER_CHESTPLATE,
                Material.LEATHER_HELMET,
        };
        ItemStack[] armor = new ItemStack[4];

        for (int i = 0; i < 4; i++) {
            Material currentPart = materials[i];
            ItemStack item = new ItemBuilder(currentPart).unbreakable(true).build();

            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(bsPlayer.getColor().getDyeColor().getColor());
            item.setItemMeta(meta);

            armor[i] = item;
        }

        p.getInventory().setArmorContents(armor);
    }
}
