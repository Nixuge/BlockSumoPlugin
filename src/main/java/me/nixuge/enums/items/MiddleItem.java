package me.nixuge.enums.items;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.nixuge.config.Lang;
import me.nixuge.utils.item.ItemBuilder;

public enum MiddleItem {
    BONUS_LIFE(Lang.get("bonusitems.middle.bonuslife.name"),
        new ItemBuilder(Material.NETHER_STAR)
            .itemName(Lang.get("bonusitems.middle.bonuslife.name"))
            .addLoreLine(Lang.get("bonusitems.middle.bonuslife.lore"))
            .build()
    ),

    TNTRAIN(Lang.get("bonusitems.middle.tntrain.name"),
        new ItemBuilder(Material.BLAZE_POWDER)
            .itemName(Lang.get("bonusitems.middle.tntrain.name"))
            .addLoreLine(Lang.get("bonusitems.middle.tntrain.lore"))
            .build()
    ),

    JUMPBOOST(Lang.get("bonusitems.middle.jumpboost.name"),
        new ItemBuilder(PotionEffectType.JUMP)
            .itemName(Lang.get("bonusitems.middle.jumpboost.name"))
            .addLoreLine(Lang.get("bonusitems.middle.jumpboost.lore"))
            .addPotionEffect(PotionEffectType.JUMP, 600, 5)
            .build()

    ),
    MEGA_BAT(Lang.get("bonusitems.middle.megabat.name"),
        new ItemBuilder(Material.IRON_SWORD)
            .itemName(Lang.get("bonusitems.middle.megabat.name"))
            .addLoreLine(Lang.get("bonusitems.middle.megabat.lore"))
            .addEnchant(Enchantment.KNOCKBACK, 10)
            .addEnchant(Enchantment.FIRE_ASPECT, 5)
            .durability(250)
            .build()
    ),

    EXPLOSION_GUN(Lang.get("bonusitems.middle.explosiongun.name"),
        new ItemBuilder(Material.GOLD_HOE)
            .itemName(Lang.get("bonusitems.middle.explosiongun.name"))
            .addLoreLine(Lang.get("bonusitems.middle.explosiongun.lore"))
            .build()
    );

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
