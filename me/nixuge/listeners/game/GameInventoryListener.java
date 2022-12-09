package me.nixuge.listeners.game;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.nixuge.utils.PlayerUtils;

public class GameInventoryListener implements Listener {

    private static Material[] unmoveableMaterials = {
            Material.WOOL,
            Material.SHEARS,
            Material.LEATHER_BOOTS,
            Material.LEATHER_LEGGINGS,
            Material.LEATHER_CHESTPLATE,
            Material.LEATHER_HELMET
    };

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (PlayerUtils.isHidden((Player) event.getWhoClicked())) {
            event.setCancelled(true);
            return;
        }

        ItemStack item = event.getCurrentItem();
        if (item == null)
            return;
        Material mat = item.getType();

        boolean contains = Arrays.stream(unmoveableMaterials).anyMatch(
                drop -> drop.equals(mat));
        if (contains)
            event.setCancelled(true);
    }
}