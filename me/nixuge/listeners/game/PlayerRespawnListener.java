package me.nixuge.listeners.game;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.objects.BsPlayer;
import me.nixuge.runnables.particle.PlayerRespawnParticle;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        GameManager gameMgr = BlockSumo.getInstance().getGameMgr();
        Player p = event.getEntity();
        BsPlayer player = gameMgr.getPlayerMgr().getExistingBsPlayerFromBukkit(p);

        player.removeLive();

        if (player.isDead()) {
            gameMgr.checkGameEnd();
        }

        //note: need to call the respawn event manually
        //since spigot().respawn() doesn't
        new BukkitRunnable() {
            @Override
            public void run() {
                p.spigot().respawn();
            }
        }.runTaskLater(BlockSumo.getInstance(), 20);
        onRespawn(new PlayerRespawnEvent(p, null, false));
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        GameManager gameMgr = BlockSumo.getInstance().getGameMgr();
        Player p = event.getPlayer();

        if (gameMgr.getPlayerMgr().getExistingBsPlayerFromBukkit(p).isDead()) {
            p.setGameMode(GameMode.SPECTATOR);
            Location spawn = gameMgr.getMcMap().getCenter();
            event.setRespawnLocation(spawn);
            p.sendMessage("You're now dead.");
            return;
        }

        Location spawn = gameMgr.getMcMap().getRandomSpawn();
        event.setRespawnLocation(spawn);

        BlockSumo plugin = BlockSumo.getInstance();
        PlayerRespawnParticle packet = new PlayerRespawnParticle(100, p);
        packet.runTaskTimer(plugin, 1, 1);
    }
}
