package me.nixuge.utils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.nixuge.BlockSumo;
// import net.md_5.bungee.api.ChatMessageType;
// import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class TextUtils {
    // NOTE: 1.8 ONLY, TO BE REMADE FOR OTHER VERSIONS W THE GENERIC
    public static void sendActionText1_8(Player player, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    // other versions (commented for now as this is in testing)
    // public static void sendActionText (Player player, String message){
    // player.spigot().sendMessage(TextComponent.fromLegacyText(message),
    // ChatMessageType.ACTION_BAR);
    // }

    public static void broadcastGame(String message) {
        BlockSumo.getInstance().getGameMgr().getPlayerMgr().getPlayers().forEach(
                (p) -> p.getBukkitPlayer().sendMessage(message));
    }

    public static String secondsToMMSS(int seconds) {
        return String.format("%02d:%02d", (seconds / 60) % 60, seconds % 60);
    }
}
