package me.nixuge.enums.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.nixuge.utils.specific.ItemUtils;

public enum MiddleItem {
    //honestly, to rework if possible
    //just as if for now to continue development
    BONUS_LIFE("Bonus Life", ItemUtils.getItemStack(Material.NETHER_STAR, "Bonus life", 1, "§aRight click to gain a live !")),
    TNTRAIN("Tnt Rain",ItemUtils.getItemStack(Material.BLAZE_POWDER, "§eTNT rain", 1, "§6Right click to spawn a tnt on everyone !")),
    JUMPBOOST("Jump Boost Potion",ItemUtils.getItemStackPotion("Jump Boost Potion", 1, "To go up fast", PotionEffectType.JUMP, 600, 5)),
    MEGA_BAT("Mega Bat",ItemUtils.getItemStack(Material.IRON_SWORD, "Mega bat", 1, "§f§bHITS HARD", Enchantment.KNOCKBACK, 10, Enchantment.FIRE_ASPECT, 5)),
    EXPLOSION_GUN("An","Explosion Gun",ItemUtils.getItemStack(Material.GOLD_HOE, "Epic gun", 1, "§6Right click pointing at a block to send someone in the air !"));

    private final String prefix;
    private final String name;
    private final ItemStack itemStack;

    private MiddleItem(String name, ItemStack itemStack) {
        this.prefix = "A";
        this.name = name;
        this.itemStack = itemStack;
    }

    private MiddleItem(String prefix, String name, ItemStack itemStack) {
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
