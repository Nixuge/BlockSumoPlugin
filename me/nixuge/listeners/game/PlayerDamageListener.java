package me.nixuge.listeners.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.PlayerManager;
import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.Hit;
import me.nixuge.runnables.GameRunnable;

public class PlayerDamageListener implements Listener {

    BlockSumo plugin;
    GameManager gameMgr;
    PlayerManager playerMgr;
    GameRunnable gameRunnable;

    public PlayerDamageListener() {
        plugin = BlockSumo.getInstance();
        gameMgr = plugin.getGameMgr();
        playerMgr = gameMgr.getPlayerMgr();
        gameRunnable = gameMgr.getGameRunnable();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        if (event.getCause().equals(DamageCause.VOID)) {
            event.setDamage(200);
        } else {
            event.setDamage(0);
        }
        // event.setCancelled(true); <- cancels kb too
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player && event.getDamager() instanceof Player))
            return;
        
        Player hitPlayer = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        BsPlayer bsp = playerMgr.getExistingBsPlayerFromBukkit(hitPlayer);
        bsp.setLastHit(new Hit(gameRunnable.getTime(), damager));
    }
}
