package me.nixuge.enums;

import java.util.Random;

import org.bukkit.DyeColor;

@SuppressWarnings("deprecation")
public enum Color {
    DARK_RED("§4", DyeColor.RED.getWoolData()),
    RED("§c", DyeColor.RED.getWoolData()),
    ORANGE("§6", DyeColor.ORANGE.getWoolData()),
    YELLOW("§e", DyeColor.YELLOW.getWoolData()),
    GREEN("§2", DyeColor.GREEN.getWoolData()),
    LIME("§a", DyeColor.LIME.getWoolData()),
    AQUA("§b", DyeColor.LIGHT_BLUE.getWoolData()),
    BLUE("§9", DyeColor.BLUE.getWoolData()),
    PINK("§d", DyeColor.PINK.getWoolData()),
    MAGENTA("§5", DyeColor.MAGENTA.getWoolData());

    private final String chatColor;
    private final byte byteColor;

    private Color(String chatColor, byte byteColor) {
        this.chatColor = chatColor;
        this.byteColor = byteColor;
    }

    public String getChatColor() {
        return chatColor;
    }
    public byte getByteColor() {
        return byteColor;
    }

    private static Random rand = new Random();
    public static Color getRandomColor() {
        return Color.values()[rand.nextInt(Color.values().length)];
    }
}
