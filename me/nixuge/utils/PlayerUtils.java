package me.nixuge.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.BlockSumo;
import me.nixuge.PlayerManager;
import me.nixuge.objects.BsPlayer;

public class PlayerUtils {
    private static BlockSumo plugin = BlockSumo.getInstance();
    private static PlayerManager playerMgr = plugin.getGameMgr().getPlayerMgr();
    private static List<Player> hiddenPlayers = new ArrayList<>();


    public static void hidePlayer(Player p) {
        hiddenPlayers.add(p);
        
        new BukkitRunnable() {
            @Override
            public void run() {
                p.getInventory().clear();
                p.setGameMode(GameMode.CREATIVE);
                p.setCanPickupItems(false);
            }
        }.runTaskLater(plugin, 10);

        for (BsPlayer target : playerMgr.getPlayers()) {
            if (!target.isDead())
                target.getBukkitPlayer().hidePlayer(p);
        }
    }

    public static boolean isHidden(Player p) {
        return hiddenPlayers.contains(p);
    }
}
