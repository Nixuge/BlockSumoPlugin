package me.nixuge.enums.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.nixuge.utils.ItemUtils;

public enum MiddleItem {
    //honestly, to rework if possible
    //just as if for now to continue development
    BONUS_LIFE(ItemUtils.getItemStack(Material.NETHER_STAR, "Bonus life", 1, "§aRight click to gain a live !")),
    TNTRAIN(ItemUtils.getItemStack(Material.BLAZE_POWDER, "§eTNT rain", 1, "§6Right click to spawn a tnt on everyone !")),
    JUMPBOOST(ItemUtils.getItemStackPotion("Stilts", 1, "To go up fast", PotionEffectType.JUMP, 600, 5)),
    MEGA_BAT(ItemUtils.getItemStack(Material.IRON_SWORD, "Mega bat", 1, "§f§bHITS HARD", Enchantment.KNOCKBACK, 10, Enchantment.FIRE_ASPECT, 5, (short)0)),
    EXPLOSION_GUN(ItemUtils.getItemStack(Material.GOLD_HOE, "Epic gun", "§6Right click pointing at a block to send someone in the air !", (short)2));

    private final ItemStack itemStack;

    private MiddleItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
