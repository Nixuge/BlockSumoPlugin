package me.nixuge.utils.game.items.global;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

public class MovingFireball {
    public static void run(Player p) {
        Fireball fireball = (Fireball) p.getWorld().spawnEntity(p.getLocation() , EntityType.FIREBALL);
        // fireball.setVelocity(null);
    }
}
