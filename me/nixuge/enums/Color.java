package me.nixuge.enums;

import java.util.Random;

import org.bukkit.DyeColor;

public enum Color {
    DARK_RED("§4", DyeColor.RED),
    RED("§c", DyeColor.RED),
    ORANGE("§6", DyeColor.ORANGE),
    YELLOW("§e", DyeColor.YELLOW),
    GREEN("§2", DyeColor.GREEN),
    LIME("§a", DyeColor.LIME),
    AQUA("§b", DyeColor.LIGHT_BLUE),
    BLUE("§9", DyeColor.BLUE),
    PINK("§d", DyeColor.PINK),
    MAGENTA("§5", DyeColor.MAGENTA);

    private final String chatColor;
    private final DyeColor dyeColor;

    private Color(String chatColor, DyeColor dyeColor) {
        this.chatColor = chatColor;
        this.dyeColor = dyeColor;
    }

    public String getChatColor() {
        return chatColor;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }
    
    @SuppressWarnings("deprecation")
    public byte getWoolByteColor() {
        return dyeColor.getWoolData();
    }

    private static Random rand = new Random();

    public static Color getRandomColor() {
        return Color.values()[rand.nextInt(Color.values().length)];
    }

    @SuppressWarnings("deprecation")
    public static Color getFromWoolData(byte woolData) {
        for (Color color : Color.values()) {
            if (color.getDyeColor().getWoolData() == woolData) {
                return color;
            }
        }
        return null;
    }
}
