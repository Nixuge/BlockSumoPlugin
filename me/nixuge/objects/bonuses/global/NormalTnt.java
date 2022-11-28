package me.nixuge.objects.bonuses.global;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;

public class NormalTnt {
    public static void run(Block block) {
        block.setType(Material.AIR);
        TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(block.getLocation().add(.5, 0, .5) , EntityType.PRIMED_TNT);
        tnt.setYield(3f); //reduce tnt size
        tnt.setFuseTicks(60);
    }
}
