package me.nixuge.utils;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.ScoreboardManager;

import me.nixuge.BlockSumo;

public class ScoreboardUtils {
    private static ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    public static void resetScoreboards() {
        for (BsPlayer p : BlockSumo.getInstance().getGameMgr().getPlayerMgr().getPlayers()) {
            p.getBukkitPlayer().setScoreboard(scoreboardManager.getNewScoreboard());
        }
    }

    public static ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}
