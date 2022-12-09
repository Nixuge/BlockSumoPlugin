package me.nixuge.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import me.nixuge.BlockSumo;
import me.nixuge.PlayerManager;
import me.nixuge.objects.BsPlayer;

public class PlayerUtils {
    private static BlockSumo plugin = BlockSumo.getInstance();
    private static PlayerManager playerMgr = plugin.getGameMgr().getPlayerMgr();
    private static List<Player> hiddenPlayers = new ArrayList<>();

    public static void hidePlayer(Player p) {
        hiddenPlayers.add(p);

        p.getInventory().clear();
        p.setGameMode(GameMode.CREATIVE);

        for (BsPlayer target : playerMgr.getPlayers()) {
            if (!target.isDead())
                target.getBukkitPlayer().hidePlayer(p);
        }
    }

    public static boolean isHidden(Player p) {
        return hiddenPlayers.contains(p);
    }

    @SuppressWarnings("deprecation") // Bukkit.getOnlinePlayers()
    public static void showEveryone() {
        for (Player p : hiddenPlayers) {
            for (Player target : Bukkit.getOnlinePlayers()) {
                target.showPlayer(p);
            }
        }
        hiddenPlayers = new ArrayList<>();
    }
}
