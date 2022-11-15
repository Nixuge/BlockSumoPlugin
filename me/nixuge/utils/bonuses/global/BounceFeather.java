package me.nixuge.utils.bonuses.global;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.nixuge.BlockSumo;
import me.nixuge.utils.ItemUtils;

public class BounceFeather {
    public static void run(Player p) {
        ItemUtils.removeSingleItemPlayerHand(p);

        Vector v = p.getEyeLocation().getDirection().multiply(5.0);
        p.setVelocity(v);

        new BukkitRunnable() {
            @Override
            public void run() {
                Vector v = p.getEyeLocation().getDirection().multiply(.1);
                p.setVelocity(v);
            }
        }.runTaskLater(BlockSumo.getInstance(), 10);
    }
}
