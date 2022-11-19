package me.nixuge.listeners.game;

import org.bukkit.Bukkit;
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
import me.nixuge.config.Lang;
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

        Player killer = event.getEntity().getKiller();
        if (killer != null)
            playerMgr.getExistingBsPlayerFromBukkit(killer).addKill();

        if (player.isDead()) {
            plugin.getGameMgr().checkGameEnd();
        }

        // Bukkit.broadcastMessage("hello there " + event.getDeathMessage());

        new BukkitRunnable() {
            @Override
            public void run() {
                p.spigot().respawn();
            }
        }.runTaskLater(plugin, 10);

        //note: need to call the respawn event manually
        //since spigot().respawn() doesn't
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
            p.sendMessage(Lang.get("general.dead"));
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
