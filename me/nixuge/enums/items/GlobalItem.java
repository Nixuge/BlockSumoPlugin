package me.nixuge.enums.items;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import me.nixuge.config.Lang;
import me.nixuge.utils.ItemUtils;

public enum GlobalItem {
    FIREBALL(Lang.get("bonusitems.normal.fireball.name"),
            ItemUtils.getItemStack(
                    Material.FIREBALL,
                    Lang.get("bonusitems.normal.fireball.name"),
                    1,
                    Lang.get("bonusitems.normal.fireball.lore"))),

    SNOWBALLS(Lang.get("bonusitems.normal.snowballs.name"),
            ItemUtils.getItemStack(
                    Material.SNOW_BALL,
                    Lang.get("bonusitems.normal.snowballs.name"),
                    8,
                    Lang.get("bonusitems.normal.snowballs.lore"))),

    SMALL_BAT(Lang.get("bonusitems.normal.smallbat.name"),
            ItemUtils.getItemStack(
                    Material.WOOD_SWORD,
                    Lang.get("bonusitems.normal.smallbat.name"),
                    1,
                    Lang.get("bonusitems.normal.smallbat.lore"),
                    Enchantment.KNOCKBACK, 1,
                    null, 0,
                    (short) 59)),

    TNT(Lang.get("bonusitems.normal.tnt.name"),
            ItemUtils.getItemStack(
                    Material.TNT,
                    Lang.get("bonusitems.normal.tnt.name"),
                    1,
                    Lang.get("bonusitems.normal.tnt.lore"))),

    BOUNCE_FEATHER(Lang.get("bonusitems.normal.bouncyfeather.name"),
            ItemUtils.getItemStack(
                    Material.FEATHER,
                    Lang.get("bonusitems.normal.bouncyfeather.name"),
                    1,
                    Lang.get("bonusitems.normal.bouncyfeather.lore")));

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

    private static Random rand = new Random();
    public static GlobalItem getRandomItem() {
        return GlobalItem.values()[rand.nextInt(GlobalItem.values().length)];
    }
}
