package me.nixuge.enums.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.nixuge.config.Lang;
import me.nixuge.utils.ItemUtils;

public enum MiddleItem {
    // honestly, to rework if possible
    // just as if for now to continue development
    BONUS_LIFE(Lang.get("bonusItems.middle.bonusLife.name"),
            ItemUtils.getItemStack(
                    Material.NETHER_STAR,
                    Lang.get("bonusItems.middle.bonusLife.name"),
                    1,
                    Lang.get("bonusItems.middle.bonusLife.lore"))),

    TNTRAIN(Lang.get("bonusItems.middle.tntRain.name"),
            ItemUtils.getItemStack(
                    Material.BLAZE_POWDER,
                    Lang.get("bonusItems.middle.tntRain.name"),
                    1,
                    Lang.get("bonusItems.middle.tntRain.lore"))),

    JUMPBOOST(Lang.get("bonusItems.middle.jumpBoost.name"),
            ItemUtils.getItemStackPotion(
                    Lang.get("bonusItems.middle.jumpBoost.name"),
                    1,
                    Lang.get("bonusItems.middle.jumpBoost.lore"),
                    PotionEffectType.JUMP, 600, 5)),

    MEGA_BAT(Lang.get("bonusItems.middle.megaBat.name"),
            ItemUtils.getItemStack(
                    Material.IRON_SWORD,
                    Lang.get("bonusItems.middle.megaBat.name"),
                    1,
                    Lang.get("bonusItems.middle.megaBat.lore"),
                    Enchantment.KNOCKBACK, 10,
                    Enchantment.FIRE_ASPECT, 5,
                    (short) 250)),

    EXPLOSION_GUN(Lang.get("bonusItems.middle.explosionGun.name"),
            ItemUtils.getItemStack(
                    Material.GOLD_HOE,
                    Lang.get("bonusItems.middle.explosionGun.name"),
                    1,
                    Lang.get("bonusItems.middle.explosionGun.lore")));

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

}
