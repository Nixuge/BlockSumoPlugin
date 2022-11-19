package me.nixuge.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import me.nixuge.BlockSumo;
import me.nixuge.config.inner.ExpiringBlockConfig;
import me.nixuge.config.inner.GameConfig;
import me.nixuge.config.inner.GeneralConfig;
import me.nixuge.config.inner.MapConfig;
import me.nixuge.config.inner.TargetConfig;

public class Config {
    // WHY THIS WEIRD FORMAT?
    // main reason: I want to access things using
    // Config.<map/game/etc>.getSomething()
    // and that's about it, hence why those classes are pretty exotic
    // this is the file that handles the actual config management, other classes are
    // mostly parsing

    // HOW OTHER FILES ARE STRUCTURED:
    // 1- All of the fields are set without an assignement as final values
    // 2- The constructor does the job of basically running all of the logic to
    //// parse those values & set them
    // 3- The rest of the class is just getters for easy access

    // would've liked to use inheritance, but unneeded & overcomplicated for this.

    private static BlockSumo plugin = BlockSumo.getInstance();
    private static FileConfiguration fileConf;
    public static ExpiringBlockConfig expiringBlock;
    public static GameConfig game;
    public static MapConfig map;
    public static TargetConfig target;
    public static GeneralConfig general;

    public static void init(FileConfiguration fileConf) {
        Config.fileConf = fileConf;
        expiringBlock = new ExpiringBlockConfig(getFileConfigBlock("expiringblock"));
        game = new GameConfig(getFileConfigBlock("game"));
        map = new MapConfig(getFileConfigBlock("map"));
        target = new TargetConfig(getFileConfigBlock("target"));
        general = new GeneralConfig(getFileConfigBlock("general"));
    }

    public static void saveConfig() {
        fileConf.options().copyDefaults(true);
        plugin.saveConfig();
    }

    public static ConfigurationSection getFileConfigBlock(String str) {
        return fileConf.getConfigurationSection(str);
    }
}
