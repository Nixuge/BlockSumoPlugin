package me.nixuge.runnables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.PlayerManager;
import me.nixuge.config.Config;
import me.nixuge.config.Lang;
import me.nixuge.objects.BsPlayer;
import me.nixuge.utils.TextUtils;

public class TargetterRunnable extends BukkitRunnable {

    private BlockSumo plugin = BlockSumo.getInstance();
    private GameManager gameMgr = plugin.getGameMgr();
    private PlayerManager playerMgr = gameMgr.getPlayerMgr();
    private GameRunnable gameRunnable = gameMgr.getGameRunnable();

    // probably a better way to do that
    private Map<String, List<Integer>> lastPlayerYs = new HashMap<>();
    private Map<String, Double> averages;
    private String highest;

    private String currentTarget;
    private String lastTarget;
    private int lastTargetTime = -500;

    @Override
    public void run() {
        checkCalculateAverages();
        playerBecomesTarget();
    }

    public void checkCalculateAverages() {
        averages = new HashMap<>();

        for (BsPlayer player : playerMgr.getPlayers()) {
            List<Integer> playerYs = lastPlayerYs.get(player.getName());
            if (playerYs == null)
                playerYs = new ArrayList<>(); // LinkedList?

            if (playerYs.size() >= Config.target.getMaxValuesStored())
                playerYs.remove(0);

            if (player.isLoggedOn()) {
                playerYs.add(player.getBukkitPlayer().getLocation().getBlockY());
            }

            if (player.isLoggedOn() && playerYs.size() >= Config.target.getMinValuesNeeded()) {
                averages.put(player.getName(),
                        playerYs.stream().mapToInt(i -> i).average().orElse(0));
            }

            lastPlayerYs.put(player.getName(), playerYs);
        }
    }

    public void playerBecomesTarget() {
        if (currentTarget != null ||
                gameRunnable.getTime() < lastTargetTime + Config.target.getMinTimeBetweenTargets())
            return;

        highest = null;
        for (String p : averages.keySet()) {
            if ((highest == null || averages.get(p) > averages.get(highest)) &&
                    (Config.target.allowSameTargetMultipleTimes() || p != lastTarget)) {
                highest = p;
            }
        }

        Double avrhigh = averages.get(highest);
        if (avrhigh != null && avrhigh >= Config.target.getMinYAverage()) {
            String coloredName = highest;
            BsPlayer bsP = playerMgr.getBsPlayerFromName(highest);
            if (bsP != null)
                coloredName = bsP.getColoredName();

            TextUtils.broadcastGame(Lang.get("targetter.newtarget", coloredName));
            lastTargetTime = gameRunnable.getTime();
            currentTarget = highest;
        }
    }

    public String getTarget() {
        return currentTarget;
    }

    public void resetTarget() {
        lastTarget = currentTarget;
        currentTarget = null;
    }

    public void resetPlayer(String playerName) {
        lastPlayerYs.put(playerName, null);
    }
}
