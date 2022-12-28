package me.nixuge.enums.items;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.nixuge.config.Lang;
import me.nixuge.utils.ItemUtils;

public enum MiddleItem {
    // honestly, to rework if possible
    // just as if for now to continue development
    BONUS_LIFE(Lang.get("bonusitems.middle.bonuslife.name"),
            ItemUtils.getItemStack(
                    Material.NETHER_STAR,
                    Lang.get("bonusitems.middle.bonuslife.name"),
                    1,
                    Lang.get("bonusitems.middle.bonuslife.lore"))),

    TNTRAIN(Lang.get("bonusitems.middle.tntrain.name"),
            ItemUtils.getItemStack(
                    Material.BLAZE_POWDER,
                    Lang.get("bonusitems.middle.tntrain.name"),
                    1,
                    Lang.get("bonusitems.middle.tntrain.lore"))),

    JUMPBOOST(Lang.get("bonusitems.middle.jumpboost.name"),
            ItemUtils.getItemStackPotion(
                    Lang.get("bonusitems.middle.jumpboost.name"),
                    1,
                    Lang.get("bonusitems.middle.jumpboost.lore"),
                    PotionEffectType.JUMP, 600, 5)),

    MEGA_BAT(Lang.get("bonusitems.middle.megabat.name"),
            ItemUtils.getItemStack(
                    Material.IRON_SWORD,
                    Lang.get("bonusitems.middle.megabat.name"),
                    1,
                    Lang.get("bonusitems.middle.megabat.lore"),
                    Enchantment.KNOCKBACK, 10,
                    Enchantment.FIRE_ASPECT, 5,
                    (short) 250)),

    EXPLOSION_GUN(Lang.get("bonusitems.middle.explosiongun.name"),
            ItemUtils.getItemStack(
                    Material.GOLD_HOE,
                    Lang.get("bonusitems.middle.explosiongun.name"),
                    1,
                    Lang.get("bonusitems.middle.explosiongun.lore")));

    private final String name;
    private final ItemStack itemStack;

    private MiddleItem(String name, ItemStack itemStack) {
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
    public static MiddleItem getRandomItem() {
        return MiddleItem.values()[rand.nextInt(MiddleItem.values().length)];
    }
}
