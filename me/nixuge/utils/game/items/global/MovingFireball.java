package me.nixuge.utils.game.items.global;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

public class MovingFireball {
    public static void run(Player p) {
        int amount = p.getItemInHand().getAmount();
        if (amount > 1) {
            p.getItemInHand().setAmount(amount - 1);
        } else {
            p.setItemInHand(null);
        }
        Fireball fireball = p.launchProjectile(Fireball.class);
        fireball.setShooter(p);
        fireball.setIsIncendiary(false);
        fireball.setBounce(false);
        fireball.setYield(3f);
        fireball.setVelocity(fireball.getDirection().multiply(20.0));
    }
}
