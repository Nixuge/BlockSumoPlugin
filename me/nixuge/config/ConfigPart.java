package me.nixuge.config;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public abstract class ConfigPart {
    // Bit of a verbose class
    // but useful for easy access
    // NOTE: using a private/protected conf instead of having as a param
    // isn't that good of an idea
    // -> need error checking at the start of every method to make sure its there
    // -> not good for like the "MapConfig" class where the used conf changes

    protected int getInt(ConfigurationSection conf, String key, int defaultVal) {
        int value;
        try {
            value = conf.getInt(key);
        } catch (Exception e) {
            Logger.log(LogLevel.WARNING, "Invalid key \"" + key + "\" (int). Please check your config. Defaulted to \"" + defaultVal + "\"");
            value = defaultVal;
        }
        return value;
    }

    protected String getString(ConfigurationSection conf, String key, String defaultVal) {
        String value;
        try {
            value = conf.getString(key);
        } catch (Exception e) {
            Logger.log(LogLevel.WARNING, "Invalid key \"" + key + "\" (String). Please check your config. Defaulted to \"" + defaultVal + "\"");
            value = defaultVal;
        }
        return value;
    }

    protected boolean getBoolean(ConfigurationSection conf, String key, boolean defaultVal) {
        boolean value;
        try {
            value = conf.getBoolean(key);
        } catch (Exception e) {
            Logger.log(LogLevel.WARNING, "Invalid value for key \"" + key + "\" (boolean). Please check your config. Defaulted to \"" + defaultVal + "\"");
            value = defaultVal;
        }
        return value;
    }


    protected List<String> getStringList(ConfigurationSection conf, String key, List<String> defaultVal) {
        List<String> value;
        try {
            value = conf.getStringList(key);
        } catch (Exception e) {
            Logger.log(LogLevel.WARNING, "Invalid key \"" + key + "\" (String list). Please check your config. Defaulted to \"" + defaultVal + "\"");
            value = defaultVal;
        }
        return value;
    }
}
