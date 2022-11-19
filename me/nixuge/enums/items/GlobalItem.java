package me.nixuge.enums.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import me.nixuge.config.Lang;
import me.nixuge.utils.ItemUtils;

public enum GlobalItem {
    FIREBALL(Lang.get("bonusItems.normal.fireball.name"),
            ItemUtils.getItemStack(
                    Material.FIREBALL,
                    Lang.get("bonusItems.normal.fireball.name"),
                    1,
                    Lang.get("bonusItems.normal.fireball.lore"))),

    SNOWBALLS(Lang.get("bonusItems.normal.snowballs.name"),
            ItemUtils.getItemStack(
                    Material.SNOW_BALL,
                    Lang.get("bonusItems.normal.snowballs.name"),
                    8,
                    Lang.get("bonusItems.normal.snowballs.lore"))),

    SMALL_BAT(Lang.get("bonusItems.normal.smallBat.name"),
            ItemUtils.getItemStack(
                    Material.WOOD_SWORD,
                    Lang.get("bonusItems.normal.smallBat.name"),
                    1,
                    Lang.get("bonusItems.normal.smallBat.lore"),
                    Enchantment.KNOCKBACK, 1,
                    null, 0,
                    (short) 59)),

    TNT(Lang.get("bonusItems.normal.tnt.name"),
            ItemUtils.getItemStack(
                    Material.TNT,
                    Lang.get("bonusItems.normal.tnt.name"),
                    1,
                    Lang.get("bonusItems.normal.tnt.lore"))),

    BOUNCE_FEATHER(Lang.get("bonusItems.normal.bouncyFeather.name"),
            ItemUtils.getItemStack(
                    Material.FEATHER,
                    Lang.get("bonusItems.normal.bouncyFeather.name"),
                    1,
                    Lang.get("bonusItems.normal.bouncyFeather.lore")));

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
