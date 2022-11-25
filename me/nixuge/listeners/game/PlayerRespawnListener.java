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
import me.nixuge.config.Config;
import me.nixuge.config.Lang;
import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.Hit;
import me.nixuge.runnables.GameRunnable;
import me.nixuge.runnables.particle.PlayerRespawnParticle;
import me.nixuge.utils.TextUtils;

public class PlayerRespawnListener implements Listener {

    BlockSumo plugin;
    GameManager gameMgr;
    PlayerManager playerMgr;
    GameRunnable gameRunnable;

    public PlayerRespawnListener() {
        plugin = BlockSumo.getInstance();
        gameMgr = plugin.getGameMgr();
        playerMgr = gameMgr.getPlayerMgr();
        gameRunnable = gameMgr.getGameRunnable();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        BsPlayer player = playerMgr.getExistingBsPlayerFromBukkit(p);

        player.removeLive();

        String target = gameMgr.getTargetterRunnable().getTarget();
        Hit lastHit = player.getLastHit();

        if (lastHit != null && lastHit.getHitTime() + Config.game.getCountAsKillDelay() > gameRunnable.getTime()) {
            BsPlayer killer = playerMgr.getExistingBsPlayerFromBukkit(lastHit.getHitter());
            killer.addKill();
            event.setDeathMessage(
                    Lang.get("deathmessages.fromkiller", player.getColoredName(), killer.getColoredName()));

            if (target != null && target == p.getName()) {
                TextUtils.broadcastGame(
                        Lang.get("targetter.targetkilled", killer.getColoredName(), player.getColoredName()));
                killer.addLive();
            }

        } else {
            event.setDeathMessage(Lang.get("deathmessages.alone", player.getColoredName()));
            if (target != null && target == p.getName())
                TextUtils.broadcastGame(Lang.get("targetter.targetdied", player.getColoredName()));
        }

        if (player.isDead()) {
            plugin.getGameMgr().checkGameEnd();
        }

        // auto respawn the player w a delay of .5s
        new BukkitRunnable() {
            @Override
            public void run() {
                p.spigot().respawn();
            }
        }.runTaskLater(plugin, 10);

        // note: need to call the respawn event manually
        // since spigot().respawn() doesn't
        onRespawn(new PlayerRespawnEvent(p, null, false));
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player p = event.getPlayer();

        if (gameMgr.getPlayerMgr().getExistingBsPlayerFromBukkit(p).isDead()) {
            p.setGameMode(GameMode.SPECTATOR);
            Location spawn = gameMgr.getMcMap().getCenter();
            event.setRespawnLocation(spawn);
            p.sendMessage(Lang.get("general.eliminated"));
            return;
        }

        Location spawn = gameMgr.getMcMap().getRandomSpawn();
        event.setRespawnLocation(spawn);

        if (!p.isDead()) {
            PlayerRespawnParticle packet = new PlayerRespawnParticle(60, p);
            packet.runTaskTimer(plugin, 1, 1);
        }

    }
}
