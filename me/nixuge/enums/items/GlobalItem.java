package me.nixuge.enums.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import me.nixuge.utils.ItemUtils;

public enum GlobalItem {
    FIREBALL("fireball", ItemUtils.getItemStack(Material.FIREBALL, "Fireball", 1, "Explosion")),
    SNOWBALLS("snowballs", ItemUtils.getItemStack(Material.SNOW_BALL, "Snowball", 8, "Snowboule")),
    SMALL_BAT("small bat", ItemUtils.getItemStack(Material.WOOD_SWORD, "Small bat", 1, "§fHits hard but not too much", Enchantment.KNOCKBACK, 1, null, 0, (short)59)),
    TNT("TNT", ItemUtils.getItemStack(Material.TNT, "TNT", 1, "§4BOOM")),
    BOUNCE_FEATHER("Bouncy feather", ItemUtils.getItemStack(Material.FEATHER, "Bouncy feather", 1, "Goes wooosh"));

    private final String name;
    private final ItemStack itemStack;

    private GlobalItem(String name, ItemStack itemStack) {
        this.name = name;
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    public String getName() {
        return name;
    }
}

