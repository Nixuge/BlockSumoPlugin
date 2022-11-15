package me.nixuge.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class PlayerDamageListener implements Listener {
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;

        if (e.getCause().equals(DamageCause.VOID)) {
            e.setDamage(200);
        } else {
            e.setDamage(0);
        }
        // e.setCancelled(true); <- cancels kb too
    }
}
