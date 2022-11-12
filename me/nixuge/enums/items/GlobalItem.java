package me.nixuge.enums.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import me.nixuge.utils.specific.ItemUtils;

public enum GlobalItem {
    FIREBALL("fireball", ItemUtils.getItemStack(Material.FIREBALL, "Fireball", 1, "Explosion")),
    SNOWBALLS("some","snowballs", ItemUtils.getItemStack(Material.SNOW_BALL, "Snowball", 8)),
    SMALL_BAT("small bat", ItemUtils.getItemStack(Material.WOOD_SWORD, "Small bat", 1, "§fHits hard but not too much", Enchantment.KNOCKBACK, 1, null, 0)),
    TNT("TNT",ItemUtils.getItemStack(Material.TNT, "TNT", 1, "§4BOOM"));

    //TODO: FIREBALL LAUNCH

    private final String prefix;
    private final String name;
    private final ItemStack itemStack;

    private GlobalItem(String name, ItemStack itemStack) {
        this.prefix = "a";
        this.name = name;
        this.itemStack = itemStack;
    }

    private GlobalItem(String prefix, String name, ItemStack itemStack) {
        this.prefix = prefix;
        this.name = name;
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    public String getName() {
        return name;
    }
    public String getPrefix() {
        return prefix;
    }
}

