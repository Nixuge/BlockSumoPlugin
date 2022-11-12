package me.nixuge.enums.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import me.nixuge.utils.ItemUtils;

public enum GlobalItem {
    FIREBALL(ItemUtils.getItemStack(Material.NETHER_STAR, "Fireball", 1, "Explosion")),
    SNOWBALLS(ItemUtils.getItemStack(Material.SNOW_BALL, "Snowball", 8)),
    SMALL_BAT(ItemUtils.getItemStack(Material.WOOD_SWORD, "Small bat", 1, "§7Hits hard but not too much", Enchantment.KNOCKBACK, 1, null, 0, (short)0)),
    TNT(ItemUtils.getItemStack(Material.TNT, "TNT", 1, "§4BOOM"));

    private final ItemStack itemStack;

    private GlobalItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}

