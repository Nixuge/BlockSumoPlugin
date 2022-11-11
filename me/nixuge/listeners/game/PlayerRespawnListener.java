package me.nixuge.listeners.game;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.nixuge.BlockSumo;
import me.nixuge.runnables.particle.PlayerRespawnParticle;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Location spawn = BlockSumo.getInstance().getGameManager().getMcMap().getRandomSpawn();
        event.setRespawnLocation(spawn);

        BlockSumo plugin = BlockSumo.getInstance();
        PlayerRespawnParticle p = new PlayerRespawnParticle(100, event.getPlayer());
        p.runTaskTimer(plugin, 1, 1);
    }
}
