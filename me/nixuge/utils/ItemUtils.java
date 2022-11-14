package me.nixuge.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemUtils {
    //method overloading at its finest

    public static ItemStack getItemStack(Material material, String itemName, int count, String lore,
            Enchantment enchantment1, int enchantLevel1, Enchantment enchantment2, int enchantLevel2, short durability) {
        
        List<String> loreArr = new ArrayList<>();
        loreArr.add(lore);

        ItemStack item = new ItemStack(material, count);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§r" + itemName);
        meta.setLore(loreArr);
        
        if (enchantment1 != null) {
            meta.addEnchant(enchantment1, enchantLevel1, true);
        }
        if (enchantment2 != null) {
            meta.addEnchant(enchantment2, enchantLevel2, true);
        }
        if (durability != 0) {
            item.setDurability(durability);
        }

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getItemStack(Material material, String itemName, int count, String lore) {
        return getItemStack(material, itemName, count, lore, null, 0, null, 0, (short)0);
    }

    public static ItemStack getItemStack(Material material, String itemName) {
        return getItemStack(material, itemName, 1, "");
    }


    public static ItemStack getItemStackPotion(String itemName, int count, List<String> lore,
            PotionEffectType effectType, int durationTicks, int effectLevel) {
        ItemStack item = new ItemStack(Material.POTION, count);
        PotionMeta meta = (PotionMeta) item.getItemMeta();

        meta.setDisplayName("§r" + itemName);
        meta.setLore(lore);

        meta.setMainEffect(effectType);
        meta.addCustomEffect(new PotionEffect(effectType, durationTicks, effectLevel), false);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getItemStackPotion(String itemName, int count, String lore, PotionEffectType effectType,
            int durationTicks, int effectLevel) {
        List<String> loreArr = new ArrayList<>();
        loreArr.add(lore);
        return getItemStackPotion(itemName, count, loreArr, effectType, durationTicks, effectLevel);
    }

    public static void removeSingleItemPlayerHand(Player p) {
        int amount = p.getItemInHand().getAmount();
        if (amount > 1) {
            p.getItemInHand().setAmount(amount - 1);
        } else {
            p.setItemInHand(null);
        }
    }
}
