package me.nixuge.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
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

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
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
        if (!(event.getEntity() instanceof Player))
            return;

        // TP player back to surface
        if (event.getCause() == DamageCause.VOID)
            event.getEntity().teleport(gameMgr.getMcMap().getCenter());

        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        event.setCancelled(true);
    }
}
