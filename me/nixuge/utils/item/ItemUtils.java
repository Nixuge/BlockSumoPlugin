package me.nixuge.utils.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemUtils {
    // method overloading at its finest

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
            /*
             * weird note:
             * for some reason on 1.12 (& especially 1.11+ apparently) this causes a
             * java.util.concurrent.ExecutionException: java.lang.AssertionError: TRAP
             * when setting an empty item in hand (sometimes it does, sometimes no)
             * not fatal & runs just fine but still, to fix if possible
             * see
             * https://www.spigotmc.org/threads/error-executing-task-java-util-concurrent-
             * executionexception-trap.282031/
             * and
             * https://hub.spigotmc.org/jira/browse/SPIGOT-2977
             */
            p.setItemInHand(null);
        }
    }

    public static ItemStack setUnbreakable(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
