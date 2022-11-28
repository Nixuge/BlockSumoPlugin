package me.nixuge.listeners.global;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class RandomChangeListener implements Listener {
    @EventHandler
    public void onPlayerJoin(WeatherChangeEvent weatherChangeEvent) {
        weatherChangeEvent.setCancelled(true);
    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent foodLevelChangeEvent) {
        foodLevelChangeEvent.setCancelled(true);
    }

    // Should prevent grass from turning into dirt, STILL TO TEST
    @EventHandler
    public void onBlockPhysics(BlockFadeEvent event) {
        if (event.getBlock().getType() == Material.GRASS) {
            event.setCancelled(true);
        }
    }
}
