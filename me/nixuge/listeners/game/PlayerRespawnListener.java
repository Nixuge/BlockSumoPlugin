package me.nixuge.listeners.game;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.runnables.particle.PlayerRespawnParticle;
import me.nixuge.utils.BsPlayer;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        GameManager gameMgr = BlockSumo.getInstance().getGameMgr();
        Player p = event.getEntity();
        BsPlayer player = gameMgr.getPlayerMgr().getExistingBsPlayerFromBukkit(p);

        player.removeLive();
        p.sendMessage("You're now dead.");
        p.setGameMode(GameMode.SPECTATOR);

        gameMgr.checkGameEnd();
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        GameManager gameMgr = BlockSumo.getInstance().getGameMgr();
        Player p = event.getPlayer();

        if (gameMgr.getPlayerMgr().getExistingBsPlayerFromBukkit(p).isDead()) {
            Location spawn = gameMgr.getMcMap().getCenter();
            event.setRespawnLocation(spawn);
            return;
        }

        Location spawn = gameMgr.getMcMap().getRandomSpawn();
        event.setRespawnLocation(spawn);

        BlockSumo plugin = BlockSumo.getInstance();
        PlayerRespawnParticle packet = new PlayerRespawnParticle(100, p);
        packet.runTaskTimer(plugin, 1, 1);
    }
}
