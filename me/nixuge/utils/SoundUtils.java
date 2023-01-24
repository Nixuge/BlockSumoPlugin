package me.nixuge.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtils {
    // ease sound access

    public void playOn(Player player, Sound sound) {
        //TODO: fiddle default volume around
        player.playSound(player.getLocation(), sound, 1, 1);
    }

    public void playOn(Player player, Sound sound, int volume) {
        player.playSound(player.getLocation(), sound, volume, 1);
    }
}
