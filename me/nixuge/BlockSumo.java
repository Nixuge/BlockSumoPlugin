package me.nixuge;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.nixuge.commands.KitCommand;
import me.nixuge.commands.dev.EndCommand;
import me.nixuge.commands.dev.StartCommand;
import me.nixuge.commands.dev.TestCommand;
import me.nixuge.commands.dev.TestCommand2;
import me.nixuge.config.Config;
import me.nixuge.config.Lang;
import me.nixuge.listeners.global.ItemSpawnDropListener;
import me.nixuge.listeners.global.RandomChangeListener;
import me.nixuge.runnables.LobbyRunnable;
import me.nixuge.utils.ScoreboardUtils;
import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class BlockSumo extends JavaPlugin {

    // TODO: add sounds

    // TODO: proper game end (fireworks & other)
    // TODO: only select spawn if no player around

    // NOT WORKING IN 1.7:
    // Logging
    // "BlockExplodeEvent"
    // particles

    private static BlockSumo main;

    public static BlockSumo getInstance() {
        return main;
    }

    private GameManager gameManager;

    public GameManager getGameMgr() {
        return gameManager;
    }

    private PluginManager pluginManager;

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    private String mcRawVersion;

    public String getMcRawVersion() {
        return mcRawVersion;
    }

    private String mcVersion;

    public String getMcVersion() {
        return mcVersion;
    }
    public Float getMcVersionFloat() {
        return Float.parseFloat(mcVersion);
    }

    public boolean is1_7() {
        return mcVersion.equals("1.7");
    }

    private LobbyRunnable lobbyRunnable;

    private void versionCheck() {
        mcRawVersion = getServer().getBukkitVersion().split("-")[0];
        Logger.log(LogLevel.DEBUG, "Detected raw MC version: " + mcRawVersion);

        String[] arr = mcRawVersion.split(Pattern.quote("."));
        arr = Arrays.copyOfRange(arr, 0, 2);
        mcVersion = String.join(".", arr);
        Logger.log(LogLevel.DEBUG, "Detected main MC version: " + mcVersion);

        String[] testedMcVersions = { "1.8", "1.9" };
        if (Arrays.stream(testedMcVersions).anyMatch(v -> v == mcVersion)) {
            Logger.log(LogLevel.WARNING, "Your MC version (" + mcVersion + ") hasn't been tested officially. " +
                    "The plugin will be using either default NMS calls or methods based on a wide version range. " +
                    "Some things may (and will probably) break. Use at your own risk.");
        }
    }

    public void init() {
        // set vars & config (order is important)
        main = this;

        saveDefaultConfig();

        Config.init(getConfig());

        Lang.setLanguage(Config.general.getLanguage());

        pluginManager = getServer().getPluginManager();
        gameManager = new GameManager();

        // start runtime here
        lobbyRunnable = new LobbyRunnable();
        lobbyRunnable.runTaskTimer(this, 20, 20);

        // add players instead of kicking
        for (Player player : Bukkit.getOnlinePlayers()) {
            gameManager.getPlayerMgr().addPlayer(player);
        }

        // reset scoreboard
        ScoreboardUtils.resetScoreboards();

        Logger.log(LogLevel.DEBUG, "Done calling init()");
    }

    @Override
    public void onEnable() {
        versionCheck();
        init();

        getCommand("end_blocksumo").setExecutor(new EndCommand());
        getCommand("start_blocksumo").setExecutor(new StartCommand());
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("testcommand").setExecutor(new TestCommand());
        getCommand("test2").setExecutor(new TestCommand2());

        // those remaining here because they're always needed
        pluginManager.registerEvents(new RandomChangeListener(), this);
        pluginManager.registerEvents(new ItemSpawnDropListener(), this);

        Logger.log("Done loading plugin !");
    }
}