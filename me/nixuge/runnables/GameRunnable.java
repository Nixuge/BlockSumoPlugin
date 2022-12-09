package me.nixuge.runnables;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.BlockSumo;
import me.nixuge.config.Lang;
import me.nixuge.enums.items.GlobalItem;
import me.nixuge.enums.items.MiddleItem;
import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.McMap;
import me.nixuge.reflections.packet.HandleParticleSend;
import me.nixuge.reflections.packet.ParticleEnum;
import me.nixuge.runnables.particle.MiddleParticleRunnable;
import me.nixuge.utils.TextUtils;
import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class GameRunnable extends BukkitRunnable {

    private final BlockSumo plugin = BlockSumo.getInstance();
    private final Random rand = new Random();

    private int time = 0;
    private int previousLastMiddleBonusSpawn = 39;
    private int lastMiddleBonusSpawn = 40;
    private int lastGlobalBonusSpawn = 1;

    @Override
    public void run() {
        manageMiddleBonus();
        manageGlobalBonus();

        if (time == 1140) {
            TextUtils.broadcastGame(Lang.get("game.ending.endingoneminute"));
        } else if (time >= 1200) {
            plugin.getGameMgr().endGame();
        } else if (time > 1190) {
            TextUtils.broadcastGame(Lang.get("game.ending.endingxseconds", (1200 - time)));
        }

        time++;
        previousLastMiddleBonusSpawn = lastMiddleBonusSpawn;
        lastMiddleBonusSpawn++;
        lastGlobalBonusSpawn++;
    }

    private void manageGlobalBonus() {
        // if (willBonusSpawn(lastGlobalBonusSpawn, .00125))
        if (willBonusSpawn(lastGlobalBonusSpawn)) {
            Logger.log(LogLevel.DEBUG, String.format("Global bonus spawn (time %ds)", time));
            lastGlobalBonusSpawn = 0;
            spawnGlobalBonus();
        }
    }

    private void manageMiddleBonus() {
        if (lastMiddleBonusSpawn == -10) {
            TextUtils.broadcastGame(Lang.get("bonuses.opintenseconds"));

        } else if (lastMiddleBonusSpawn == 0) {
            spawnMiddleBonus();

        } else if (lastMiddleBonusSpawn > 0 && willBonusSpawn(lastMiddleBonusSpawn)) {
            Logger.log(LogLevel.DEBUG, String.format("Middle bonus spawn (time %ds)", time));
            lastMiddleBonusSpawn = -15;
            MiddleParticleRunnable run = new MiddleParticleRunnable(300);
            run.runTaskTimer(plugin, 1, 1);
        }
    }

    private boolean willBonusSpawn(int lastSpawn) {
        return willBonusSpawn(lastSpawn, .0025);
    }

    private boolean willBonusSpawn(int lastSpawn, double multiplier) {
        int randomPercent = rand.nextInt(100);
        // totally random formula
        double neededPercent = ((multiplier * (lastSpawn * lastSpawn)) - .2);
        return neededPercent > randomPercent;
    }

    private void spawnMiddleBonus() {
        Location center = plugin.getGameMgr().getMcMap().getCenter();

        new HandleParticleSend(ParticleEnum.FIREWORKS_SPARK,
                center.getX(), center.getY(), center.getZ(),
                .7, .5, .7, 50, null)
                .sendPacketAllPlayers();

        MiddleItem item = MiddleItem.values()[rand.nextInt(MiddleItem.values().length)];
        ItemStack stack = item.getItemStack();
        TextUtils.broadcastGame(Lang.get("bonuses.opspawn", item.getName()));

        McMap map = plugin.getGameMgr().getMcMap();
        map.getWorld().dropItemNaturally(map.getCenter(), stack);
    }

    private void spawnGlobalBonus() {
        GlobalItem item = GlobalItem.values()[rand.nextInt(GlobalItem.values().length)];
        ItemStack stack = item.getItemStack();

        for (BsPlayer p : plugin.getGameMgr().getPlayerMgr().getPlayers()) {
            plugin.getGameMgr().getScoreboardRunnable().addMessage(Lang.get("scoreboard.gotitem", item.getName()));
            TextUtils.broadcastGame(Lang.get("bonuses.normalspawn", item.getName()));
            p.getBukkitPlayer().getInventory().addItem(stack);
        }
    }

    public int getNextSpawnTime() {
        return -previousLastMiddleBonusSpawn;
    }

    public int getTime() {
        return time;
    }
}
