package me.nixuge.listeners.game;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.PlayerManager;
import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.Hit;
import me.nixuge.runnables.GameRunnable;
import me.nixuge.utils.PlayerUtils;

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

    @SuppressWarnings("deprecation") // event.setDamage()
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        EntityType type = event.getEntityType();

        if (type.equals(EntityType.PLAYER) && PlayerUtils.isHidden((Player) event.getEntity())) {
            event.setCancelled(true);
            return;
        }

        if (!(type.equals(EntityType.PLAYER) || type.equals(EntityType.ZOMBIE)))
            return;

        switch (event.getCause()) {
            case VOID: // Kill instantly
                event.setDamage(200);
                break;
            case FIRE_TICK:
            case FIRE:
            case FALL: // Cancels the fall damage & fire damage animations
                event.setCancelled(true);
                break;
            default: // Cancel the damage without cancelling the animation
                event.setDamage(0);
                break;
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            event.setCancelled(true);
            return;
        }
        
        Player damager = (Player) event.getDamager();

        if (PlayerUtils.isHidden(damager)) {
            event.setCancelled(true);
            return;
        }

        EntityType type = event.getEntityType();

        BsPlayer bsp;
        if (type.equals(EntityType.PLAYER)) {
            bsp = playerMgr.getBsPlayer((Player) event.getEntity());
        } else if (type.equals(EntityType.ZOMBIE)) {
            bsp = playerMgr.getBsPlayer((Zombie) event.getEntity());
        } else {
            return;
        }

        bsp.setLastHit(new Hit(gameRunnable.getTime(), damager));
    }
}
