package me.nixuge.utils.bonuses.middle;

import org.bukkit.entity.Player;

import me.nixuge.objects.BsPlayer;
import me.nixuge.utils.ItemUtils;

public class BonusLife {
    public static void run(BsPlayer bsPlayer) {
        Player p = bsPlayer.getBukkitPlayer();
        ItemUtils.removeSingleItemPlayerHand(p);
        
        p.sendMessage("§aYou gained a life !");
        bsPlayer.addLive();
    }
}
