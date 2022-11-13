package me.nixuge.utils.game.items.middle;

import org.bukkit.entity.Player;

import me.nixuge.utils.game.BsPlayer;

public class BonusLife {
    public static void run(BsPlayer bsPlayer) {
        Player p = bsPlayer.getBukkitPlayer();
        p.sendMessage("§aYou gained a life !");
        bsPlayer.addLive();
        int amount = p.getItemInHand().getAmount();
        if (amount > 1) {
            p.getItemInHand().setAmount(amount - 1);
        } else {
            p.setItemInHand(null);
        }
    }
}
