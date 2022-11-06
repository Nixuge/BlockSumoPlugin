package me.nixuge.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class RandomChangeListener implements Listener {
    @EventHandler
    public void onPlayerJoin(WeatherChangeEvent weatherChangeEvent) {
        Bukkit.broadcastMessage("cancelling WeatherChangeEvent");
        weatherChangeEvent.setCancelled(true);
    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent foodLevelChangeEvent) {
        Bukkit.broadcastMessage("cancelling FoodLevelChangeEvent");
        foodLevelChangeEvent.setCancelled(true);
    }
}
