package me.nixuge.listeners.global;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;

public class PreventiveListener implements Listener {
    BlockSumo plugin;
    GameManager gameMgr;

    public PreventiveListener() {
        this.plugin = BlockSumo.getInstance();
        this.gameMgr = plugin.getGameMgr();
    }

    // =====EXPLOSION EVENTS=====
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

    // =====BLOCK PLACE/BREAK=====
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    // =====PLAYER DAMAGE=====
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        EntityType type = event.getEntityType();
        if (!(type.equals(EntityType.ZOMBIE) || type.equals(EntityType.PLAYER)))
            return;

        // TP player back to surface or if zombie just remove it
        if (event.getCause() == DamageCause.VOID) {
            switch (type) {
                case PLAYER:
                    event.getEntity().teleport(gameMgr.getMcMap().getCenter());
                    break;
                default:
                    event.getEntity().remove();
                    break;
            }
        }
        // Then still cancel it to avoid taking damage

        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        event.setCancelled(true);
    }
}
