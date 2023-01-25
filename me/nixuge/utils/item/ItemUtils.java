package me.nixuge.utils.item;

import org.bukkit.entity.Player;

public class ItemUtils {

    public static void removeSingleItemPlayerHand(Player p) {
        int amount = p.getItemInHand().getAmount();
        if (amount > 1) {
            p.getItemInHand().setAmount(amount - 1);
        } else {
            /*
             * weird note:
             * for some reason on 1.12 (& especially 1.11+ apparently) this causes a
             * java.util.concurrent.ExecutionException: java.lang.AssertionError: TRAP
             * when setting an empty item in hand (sometimes it does, sometimes no)
             * not fatal & runs just fine but still, to fix if possible
             * see
             * https://www.spigotmc.org/threads/error-executing-task-java-util-concurrent-
             * executionexception-trap.282031/
             * and
             * https://hub.spigotmc.org/jira/browse/SPIGOT-2977
             */
            p.setItemInHand(null);
        }
    }
}
