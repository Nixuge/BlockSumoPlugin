package me.nixuge.runnables;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.PlayerManager;
import me.nixuge.config.Lang;
import me.nixuge.enums.Color;
import me.nixuge.enums.GameState;
import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.maths.IncreasingNumber;
import me.nixuge.reflections.HandleUtils;
import me.nixuge.utils.TextUtils;

public class ScoreboardRunnable extends BukkitRunnable {
    // pretty dirty class
    // but it's technically made of a bunch of string building
    // with conditions, so yeah excepted
    private final BlockSumo main = BlockSumo.getInstance();
    private final GameManager gameMgr = main.getGameMgr();
    private final PlayerManager pManager = gameMgr.getPlayerMgr();
    private List<BsPlayer> orderedPlayerList = new ArrayList<BsPlayer>();
    private List<String> sentPlayers = new ArrayList<String>();
    private boolean isEnded = false;

    private Map<Integer, String> currentSBValues;
    private IncreasingNumber incr;
    private int killsIndex;
    private int pingIndex;

    private int time;

    private int messageTimer;
    private String message;

    private void buildValuesMap() {
        currentSBValues = new HashMap<Integer, String>();
        incr = new IncreasingNumber();
        pingIndex = -1;

        currentSBValues.put(incr.getNumber(), "§a§8§m--------------------");

        _buildFooter();
        _buildPlayerLives();
        _buildTop();

        currentSBValues.put(incr.getNumber(), "§r§8§m--------------------");
    }

    private void _buildFooter() {
        // if bonus spawning, display that
        int nextSpawnTime = gameMgr.getGameRunnable().getNextSpawnTime();
        if (isEnded) {
            currentSBValues.put(incr.getNumber(), Lang.get("scoreboard.gameended"));
            currentSBValues.put(incr.getNumber(), "§r§r§r§r§r§l§r");

        } else if (messageTimer > 0 || nextSpawnTime > -3) {// -3 = 3s after the bonus spawn
            if (messageTimer > 0) {
                currentSBValues.put(incr.getNumber(), message);
                messageTimer--;
            }
            if (nextSpawnTime > -3) {
                String s = nextSpawnTime > 0 ? Lang.get("scoreboard.opspawnin", nextSpawnTime)
                        : Lang.get("scoreboard.opspawnnow");
                currentSBValues.put(incr.getNumber(), s);
            }
            currentSBValues.put(incr.getNumber(), "§r§r§r§r§r§r");
        }
    }

    private void _buildPlayerLives() {
        for (BsPlayer player : orderedPlayerList) {
            if (player.isDead())
                continue;

            int lives = player.getLives();
            boolean isLoggedOn = player.isLoggedOn();
            boolean isTarget = player.getName() == gameMgr.getTargetterRunnable().getTarget();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(isLoggedOn ? player.getColor().getChatColor() : "§7§m§o");
            stringBuilder.append(isTarget ? "§l" : "");
            stringBuilder.append(player.getName());
            stringBuilder.append(": ");
            stringBuilder.append(isLoggedOn ? colorFromLives(lives) : "§7§m§o");
            stringBuilder.append(lives);

            currentSBValues.put(incr.getNumber(), stringBuilder.toString());
        }
        // currentSBValues.put(incr.getNumber(), "§r§r§r");
        // currentSBValues.put(incr.getNumber(), "Player lives:");
    }

    private String colorFromLives(int lives) {
        if (lives >= 6) {
            return Color.GREEN.getChatColor();
        } else if (lives >= 4) {
            return Color.LIME.getChatColor();
        } else if (lives >= 2) {
            return Color.YELLOW.getChatColor();
        }
        return Color.RED.getChatColor(); // lives <= 1
    }

    private void _buildTop() {
        currentSBValues.put(incr.getNumber(), "§r§r§r§r§r");
        pingIndex = incr.getNumber();
        killsIndex = incr.getNumber(); // "kills" line
        currentSBValues.put(incr.getNumber(),
                Lang.get("scoreboard.timer", TextUtils.secondsToMMSS(time)));
    }

    public void setScoreboardToPlayer(BsPlayer bsPlayer) {
        if (bsPlayer.isLoggedOn())
            setScoreboardToPlayer(bsPlayer.getBukkitPlayer(), bsPlayer.getKills());
    }

    public void setScoreboardToPlayer(Player p, int kills) {
        Scoreboard scoreboard = p.getScoreboard();

        // about this:
        // need more testing, but if the scoreboard flickers, it's because
        // i need to remove the old objective after registering the new one
        // however, if I do this, with the way i did it i sometimes got errors
        // bc i used the game timer instead of a timer here
        // BUT tbh for now haven't had flickering issues so leaving it the simple way

        // unregister the old objective
        Objective tmp = scoreboard.getObjective("BlockSumo");
        if (tmp != null)
            tmp.unregister();

        // init a new objective
        Objective objective = scoreboard.registerNewObjective("BlockSumo", "main");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§b§6BlockSumo");

        // build scoreboard from the map
        currentSBValues.forEach((index, string) -> {
            if (string.length() > 16) {
                string = string.substring(0, 16);
            }
            objective.getScore(string).setScore(index);
        });

        // build the missing ping key (different for every player)
        objective.getScore(Lang.get("scoreboard.ping", HandleUtils.getHandleField(p, "ping"))).setScore(pingIndex);

        // same for kills
        objective.getScore(Lang.get("scoreboard.kills", kills)).setScore(killsIndex);

        if (isEnded)
            cancel();
    }

    @SuppressWarnings("deprecation") // Bukkit.getOnlinePlayers()
    @Override
    public void run() {
        // copy the arraylist to order it w/o concurrency errors
        orderedPlayerList = new ArrayList<>(pManager.getPlayers());
        orderedPlayerList.sort(Comparator.comparing(BsPlayer::getLives));

        sentPlayers.clear();

        isEnded = gameMgr.getGameState().equals(GameState.DONE);

        buildValuesMap();

        time = gameMgr.getGameRunnable().getTime();

        for (BsPlayer bsPlayer : orderedPlayerList) {
            sentPlayers.add(bsPlayer.getName());
            setScoreboardToPlayer(bsPlayer);
        }
        // send the scoreboard to specs too
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!sentPlayers.contains(p.getName()))
                setScoreboardToPlayer(p, 0);
        }
    }

    public void addMessage(String message) {
        this.messageTimer = 4;
        this.message = message;
    }
}
