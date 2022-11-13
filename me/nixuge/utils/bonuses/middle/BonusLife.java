package me.nixuge.utils.bonuses.middle;

import org.bukkit.entity.Player;

import me.nixuge.utils.game.BsPlayer;
import me.nixuge.utils.specific.ItemUtils;

public class BonusLife {
    public static void run(BsPlayer bsPlayer) {
        Player p = bsPlayer.getBukkitPlayer();
        ItemUtils.removeSingleItemPlayerHand(p);
        
        p.sendMessage("§aYou gained a life !");
        bsPlayer.addLive();
    }
}
