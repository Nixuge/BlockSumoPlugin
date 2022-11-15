package me.nixuge.listeners.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.nixuge.BlockSumo;
import me.nixuge.enums.GameState;

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
        Bukkit.broadcastMessage(event.getCurrentItem() + "");
        if (BlockSumo.getInstance().getGameMgr().getGameState().equals(GameState.PLAYING)) {
            // boolean contains = Arrays.stream(unmoveableMaterials).anyMatch(
                // drop -> drop.equals(material));
        }
    }
}
