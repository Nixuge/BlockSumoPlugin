package me.nixuge.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import me.nixuge.BlockSumo;

public class Config {
    //not using an interface bc static thingys cant be inherited
    //TODO: write the config system
    //the kit config was moved on the "objects/Kit.java" file

    //note:
    //do all the parsing here (or at least in the "config" folder, with this as a "config main"
    //& other files are specified config)
    //and have all of the logic to transform the yml into objects here
    //so you can just call for example TargetConfig.getTargetHeight
    private static BlockSumo plugin = BlockSumo.getInstance();
    private static FileConfiguration fileConf;

    public static void setFileConfig(FileConfiguration fileConf) {
        Config.fileConf = fileConf;
    }

    public static void enable() {
        fileConf.options().copyDefaults(true);
        plugin.saveConfig();
    }

    public FileConfiguration getFileConfig() {
        return fileConf;
    }

    public static void getSpawns() {
        Bukkit.broadcastMessage(GameConfig.getMaxPlayers() + "");
        MapConfig.getCenterBlock();
    }
    public static ConfigurationSection getFileConfigBlock(String str) {
        return fileConf.getConfigurationSection(str);
    }
}
