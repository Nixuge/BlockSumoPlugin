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
import me.nixuge.PlayerManager;
import me.nixuge.objects.BsPlayer;
import me.nixuge.runnables.particle.PlayerRespawnParticle;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        BlockSumo plugin = BlockSumo.getInstance();
        PlayerManager playerMgr = plugin.getGameMgr().getPlayerMgr();
        Player p = event.getEntity();
        BsPlayer player = playerMgr.getExistingBsPlayerFromBukkit(p);

        player.removeLive();
        playerMgr.getExistingBsPlayerFromBukkit(event.getEntity().getKiller()).addKill();

        if (player.isDead()) {
            plugin.getGameMgr().checkGameEnd();
        }

        //note: need to call the respawn event manually
        //since spigot().respawn() doesn't
        new BukkitRunnable() {
            @Override
            public void run() {
                p.spigot().respawn();
            }
        }.runTaskLater(plugin, 10);
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

        if (!p.isDead()) {
            BlockSumo plugin = BlockSumo.getInstance();
            PlayerRespawnParticle packet = new PlayerRespawnParticle(60, p);
            packet.runTaskTimer(plugin, 1, 1);
        }

    }
}
