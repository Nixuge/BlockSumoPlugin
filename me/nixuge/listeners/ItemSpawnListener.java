package me.nixuge.listeners;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemSpawnListener implements Listener {

    private static Material[] possibleDrops = {
        Material.FIREBALL, 
        Material.SNOW_BALL,
        Material.WOOD_SWORD,
        Material.TNT,
        Material.NETHER_STAR,
        Material.BLAZE_POWDER ,
        Material.POTION,
        Material.IRON_SWORD ,
        Material.GOLD_HOE
    };

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Material material = event.getEntity().getItemStack().getType();
        boolean contains = Arrays.stream(possibleDrops).anyMatch(
            drop -> drop.equals(material));
        
        if (!contains) {
            event.setCancelled(true);
        }
    }
}
