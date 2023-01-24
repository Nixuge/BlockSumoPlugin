package me.nixuge.config.inner;

import org.bukkit.configuration.ConfigurationSection;

import me.nixuge.config.ConfigPart;

public class GeneralConfig extends ConfigPart {
    public GeneralConfig(ConfigurationSection conf) {
        language = getString(conf, "language", "en");
    }

    private final String language;

    public String getLanguage() {
        return language;
    }
}
