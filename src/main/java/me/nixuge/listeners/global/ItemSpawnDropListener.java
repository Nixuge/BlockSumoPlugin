package me.nixuge.listeners.global;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemSpawnDropListener implements Listener {

    private static Material[] possibleDrops = {
        Material.FIREBALL, 
        Material.SNOW_BALL,
        Material.WOOD_SWORD,
        Material.TNT,
        Material.NETHER_STAR,
        Material.BLAZE_POWDER,
        Material.POTION,
        Material.GLASS_BOTTLE,
        Material.IRON_SWORD,
        Material.GOLD_HOE,
        Material.FEATHER
    };

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Material material = event.getItemDrop().getItemStack().getType();
        if (!isPossibleDrop(material)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Material material = event.getEntity().getItemStack().getType();
        if (!isPossibleDrop(material)) {
            event.setCancelled(true);
        }
    }
    
    private boolean isPossibleDrop(Material material) {
        return Arrays.stream(possibleDrops).anyMatch(
            drop -> drop.equals(material));
    }
}
