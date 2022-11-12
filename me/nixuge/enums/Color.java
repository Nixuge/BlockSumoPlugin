package me.nixuge.enums;

import org.bukkit.DyeColor;

@SuppressWarnings("deprecation")
public enum Color {
    DARK_RED("§4", DyeColor.RED.getWoolData()),
    RED("§c", DyeColor.RED.getWoolData()),
    ORANGE("§6", DyeColor.ORANGE.getWoolData()),
    YELLOW("§e", DyeColor.YELLOW.getWoolData());


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
}
