package me.nixuge.config.InnerConfigs;

import org.bukkit.configuration.ConfigurationSection;

public class GeneralConfig {
    public GeneralConfig(ConfigurationSection conf) {
        language = conf.getString("language");
    }

    private final String language;

    public String getLanguage() {
        return language;
    }
}
