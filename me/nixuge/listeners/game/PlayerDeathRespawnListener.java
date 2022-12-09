package me.nixuge.listeners.game;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
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
import me.nixuge.objects.McMap;
import me.nixuge.runnables.GameRunnable;
import me.nixuge.runnables.TargetterRunnable;
import me.nixuge.runnables.particle.PlayerRespawnParticle;
import me.nixuge.utils.TextUtils;

public class PlayerDeathRespawnListener implements Listener {

    BlockSumo plugin;
    GameManager gameMgr;
    PlayerManager playerMgr;
    GameRunnable gameRunnable;
    TargetterRunnable targetterRunnable;
    McMap mcMap;

    public PlayerDeathRespawnListener() {
        plugin = BlockSumo.getInstance();
        gameMgr = plugin.getGameMgr();
        playerMgr = gameMgr.getPlayerMgr();
        gameRunnable = gameMgr.getGameRunnable();
        targetterRunnable = gameMgr.getTargetterRunnable();
        mcMap = gameMgr.getMcMap();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        BsPlayer player = playerMgr.getBsPlayer(p);

        baseDeathLogic(player);

        if (player.isDead()) {
            player.getBukkitPlayer().getInventory().clear();
            plugin.getGameMgr().checkGameEnd();
        }

        // auto respawn the player w a delay of .5s
        new BukkitRunnable() {
            @Override
            public void run() {
                p.spigot().respawn();
            }
        }.runTaskLater(plugin, 10);
    }

    // Handle offline players deaths
    @EventHandler
    public void onZombieDeath(EntityDeathEvent event) {
        if (!event.getEntityType().equals(EntityType.ZOMBIE))
            return;

        BsPlayer player = playerMgr.getBsPlayer((Zombie) event.getEntity());
        if (player == null)
            return;

        baseDeathLogic(player);

        if (player.isDead()) {
            plugin.getGameMgr().checkGameEnd();
        }

        // respawn the now dead entity
        player.spawnOfflineEntity(mcMap.getRandomSpawn());
    }

    public void baseDeathLogic(BsPlayer player) {
        player.removeLive();

        Player p = player.getBukkitPlayer();

        String target = gameMgr.getTargetterRunnable().getTarget();
        Hit lastHit = player.getLastHit();

        if (lastHit != null && lastHit.getHitTime() + Config.game.getCountAsKillDelay() > gameRunnable.getTime()) {
            BsPlayer killer = playerMgr.getBsPlayer(lastHit.getHitter());
            killer.addKill();
            TextUtils.broadcastGame(
                    Lang.get("deathmessages.fromkiller", player.getColoredName(), killer.getColoredName()));

            if (target != null && target == p.getName()) {
                TextUtils.broadcastGame(
                        Lang.get("targetter.targetkilled", killer.getColoredName(), player.getColoredName()));
                killer.addLive();
                targetterRunnable.resetTarget();
            }

        } else {
            TextUtils.broadcastGame(Lang.get("deathmessages.alone", player.getColoredName()));

            if (target != null && target == p.getName()) {
                TextUtils.broadcastGame(Lang.get("targetter.targetdied", player.getColoredName()));
                targetterRunnable.resetTarget();
            }
        }

        // Always reset the player average Y anyways
        targetterRunnable.resetPlayer(player.getName());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player p = event.getPlayer();
        BsPlayer bsPlayer = playerMgr.getBsPlayer(p);

        if (bsPlayer.isDead()) {
            p.kickPlayer(Lang.get("general.eliminated"));
            return;
        }

        Location spawn = mcMap.getRandomSpawn();
        event.setRespawnLocation(spawn);

        if (!bsPlayer.isDead()) {
            PlayerRespawnParticle packet = new PlayerRespawnParticle(60, p);
            packet.runTaskTimer(plugin, 1, 1);
        }
    }
}
