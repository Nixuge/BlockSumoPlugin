package me.nixuge.utils;

import me.nixuge.BlockSumo;

public class TextUtils {
    public static void broadcastGame(String message) {
        BlockSumo.getInstance().getGameMgr().getPlayerMgr().getPlayers().forEach(
                (p) -> p.getBukkitPlayer().sendMessage(message));
    }

    public static String secondsToMMSS(int seconds) {
        return String.format("%02d:%02d", (seconds / 60) % 60, seconds % 60);
    }
}
