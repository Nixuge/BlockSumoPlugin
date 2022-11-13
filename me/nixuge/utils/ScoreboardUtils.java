package me.nixuge.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;

import me.nixuge.BlockSumo;
import me.nixuge.objects.BsPlayer;

public class ScoreboardUtils {
    private static ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    public static void resetScoreboard(Player p) {
        p.setScoreboard(scoreboardManager.getNewScoreboard());
    }

    public static void resetScoreboards() {
        for (BsPlayer p : BlockSumo.getInstance().getGameMgr().getPlayerMgr().getPlayers()) {
            resetScoreboard(p.getBukkitPlayer());
        }
    }

    public static ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}
