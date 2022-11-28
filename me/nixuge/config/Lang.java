package me.nixuge.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.nixuge.BlockSumo;

public class Lang {
    private static BlockSumo plugin = BlockSumo.getInstance();
    private static FileConfiguration langCfg;

    public static void setLanguage(String language) {
        // Why make something hard and complicated when you can just use
        // good ol deprecated methods?
        // TBH tho, should find a way to loadConfig from smth else
        // than an InputStream which is deprecated
        InputStream stream = plugin.getResource("languages/" + language.toLowerCase() + ".yml");
        if (stream == null) {
            Bukkit.broadcastMessage("WARNING! Language set incorrectly! Defaulting to \"en\"");
            stream = plugin.getResource("languages/en.yml");
        }

        langCfg = YamlConfiguration.loadConfiguration(stream);
    }

    public static String get(String key, Object... formats) {
        if (langCfg == null)
            return "null langCfg. Please call setLanguage";

        String unformattedString = langCfg.getString(key);
        if (unformattedString == null) {
            Bukkit.broadcastMessage("ERROR WITH KEY " + key + ". Either the plugin or the locale files have a problem.");
            return "null :/";
        }

        return String.format(unformattedString, formats);
    }
}
