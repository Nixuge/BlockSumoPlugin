package me.nixuge.config.inner;

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
