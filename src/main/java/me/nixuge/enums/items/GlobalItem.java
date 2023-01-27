package me.nixuge.enums.items;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import me.nixuge.config.Lang;
import me.nixuge.utils.item.ItemBuilder;

public enum GlobalItem {
    FIREBALL(Lang.get("bonusitems.normal.fireball.name"),
        new ItemBuilder(Material.FIREBALL)
            .itemName(Lang.get("bonusitems.normal.fireball.name"))
            .addLoreLine(Lang.get("bonusitems.normal.fireball.lore"))
            .build()
    ),

    SNOWBALLS(Lang.get("bonusitems.normal.snowballs.name"),
        new ItemBuilder(Material.SNOW_BALL)
            .itemName(Lang.get("bonusitems.normal.snowballs.name"))
            .addLoreLine(Lang.get("bonusitems.normal.snowballs.lore"))
            .count(8)
            .build()
    ),

    SMALL_BAT(Lang.get("bonusitems.normal.smallbat.name"),
        new ItemBuilder(Material.WOOD_SWORD)
            .itemName(Lang.get("bonusitems.normal.smallbat.name"))
            .addLoreLine(Lang.get("bonusitems.normal.smallbat.lore"))
            .addEnchant(Enchantment.KNOCKBACK, 1)
            .durability(59)
            .build()
    ),

    TNT(Lang.get("bonusitems.normal.tnt.name"),
        new ItemBuilder(Material.TNT)
            .itemName(Lang.get("bonusitems.normal.tnt.name"))
            .addLoreLine(Lang.get("bonusitems.normal.tnt.lore"))
            .build()
    ),

    BOUNCE_FEATHER(Lang.get("bonusitems.normal.bouncyfeather.name"),
        new ItemBuilder(Material.FEATHER)
            .itemName(Lang.get("bonusitems.normal.bouncyfeather.name"))
            .addLoreLine(Lang.get("bonusitems.normal.bouncyfeather.lore"))
            .build()
    );

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
