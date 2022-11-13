package me.nixuge.utils.bonuses.middle;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import me.nixuge.utils.game.BsPlayer;
import me.nixuge.utils.specific.ItemUtils;

public class TntRain {
    public static void run(BsPlayer bsPlayer, List<BsPlayer> bsPlayers) {
        Player p = bsPlayer.getBukkitPlayer();
        World world = p.getWorld();

        ItemUtils.removeSingleItemPlayerHand(p);

        p.sendMessage("§6TNT rain for everyone !");
        bsPlayer.addLive();
        
        for (BsPlayer innerBsPlayer : bsPlayers) {
            if (innerBsPlayer == bsPlayer) continue;
            Location innerLoc = innerBsPlayer.getBukkitPlayer().getLocation();

            TNTPrimed tnt = (TNTPrimed) world.spawnEntity(new Location(world, innerLoc.getX(), 100, innerLoc.getZ()) , EntityType.PRIMED_TNT);
            tnt.setYield(3f); //reduce tnt size
            tnt.setFuseTicks(80);
        }
    }
}
