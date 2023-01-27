package me.nixuge.objects.bonuses.global;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

import me.nixuge.utils.item.ItemUtils;

public class MovingFireball {
    public static void run(Player p) {
        ItemUtils.removeSingleItemPlayerHand(p);

        Fireball fireball = p.launchProjectile(Fireball.class);
        fireball.setShooter(p);
        fireball.setIsIncendiary(false);
        fireball.setBounce(false);
        fireball.setYield(3f);
        fireball.setVelocity(fireball.getDirection().multiply(20.0));
    }
}
