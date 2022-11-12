package me.nixuge.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardTest {
    public ScoreboardTest() {
        run();
    }
    
    private void run() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");
            //Setting where to display the scoreboard/objective (either SIDEBAR, PLAYER_LIST or BELOW_NAME)
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        //Setting the display name of the scoreboard/objective
        objective.setDisplayName("BlockSumo");

        Score score = objective.getScore("§r§8§m--------------------");
        score.setScore(42);

        objective.getScore("§8§m--------------------").setScore(0);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(board);
        }
    }
}
