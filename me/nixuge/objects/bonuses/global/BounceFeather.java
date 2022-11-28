package me.nixuge.objects.bonuses.global;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.nixuge.utils.ItemUtils;

public class BounceFeather {
    public static void run(Player p) {
        ItemUtils.removeSingleItemPlayerHand(p);

        Vector v = p.getEyeLocation().getDirection().multiply(3.0);
        p.setVelocity(v); 
    }
}
