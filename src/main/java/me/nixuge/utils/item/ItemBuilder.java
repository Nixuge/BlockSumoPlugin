package me.nixuge.utils.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

import java.util.Map.Entry;

public class ItemBuilder {
    private final Material material;
    private String itemName;
    private short durability;
    private int count = 1;
    private boolean unbreakable = false;
    private List<String> lore = new ArrayList<>();
    private Map<Enchantment, Integer> enchantments = new HashMap<>();

    // tbh could've maybe moved the potion part to another class
    // and made them both inherit the same base,
    // but it's not that huge of a mess so staying here
    // 2DO: look at potion color support (seems to need NMS on 1.8)
    private boolean isPotion = false;
    private PotionEffectType potionMainEffectType;
    private List<PotionEffect> potionEffects;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(PotionEffectType potionMainEffect) {
        this.material = Material.POTION;
        this.potionMainEffectType = potionMainEffect;
        this.isPotion = true;
        this.potionEffects = new ArrayList<>();
    }

    public ItemBuilder itemName(String itemName) {
        // §r to remove the default italic effect
        this.itemName = "§r" + itemName;
        return this;
    }

    public ItemBuilder count(int count) {
        this.count = count;
        return this;
    }

    public ItemBuilder durability(int durability) {
        durability((short) durability);
        return this;
    }

    public ItemBuilder durability(short durability) {
        this.durability = durability;
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder addLoreLine(String lore) {
        this.lore.add(lore);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder addPotionEffect(PotionEffectType effectType, int durationTicks, int effectLevel) {
        if (this.isPotion) {
            this.potionEffects.add(
                    new PotionEffect(effectType, durationTicks, effectLevel));
        } else {
            Logger.log(LogLevel.WARNING, "Item builder API: tried to add an effect to a non-potion item.");
        }
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(this.material, this.count);
        if (durability != 0) {
            item.setDurability(durability);
        }
        

        ItemMeta meta = item.getItemMeta();
        if (this.unbreakable) {
            meta.spigot().setUnbreakable(true);
        }
        if (this.itemName != null) {
            meta.setDisplayName(itemName);
        }
        if (this.lore.size() > 0) {
            meta.setLore(lore);
        }
        if (this.enchantments.size() > 0) {
            for (Entry<Enchantment, Integer> entry : this.enchantments.entrySet()) {
                // last boolean just bypasses the level restriction limits
                meta.addEnchant(
                        entry.getKey(), entry.getValue(), true);
            }
        }

        item.setItemMeta(meta);

        if (this.isPotion)
            setItemPotionMeta(item);

        return item;
    }

    private void setItemPotionMeta(ItemStack item) {
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

        potionMeta.setMainEffect(this.potionMainEffectType);

        if (this.potionEffects.size() > 0) {
            for (PotionEffect effect : this.potionEffects) {
                potionMeta.addCustomEffect(effect, true);
            }
        }

        item.setItemMeta(potionMeta);
    }
}
