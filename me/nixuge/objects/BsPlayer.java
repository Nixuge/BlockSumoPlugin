package me.nixuge.objects;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nixuge.enums.Color;

public class BsPlayer {
    private final Color color;
    private Kit kit;
    private Player player;
    private Hit lastHit;
    private boolean isLoggedOn;
    private boolean isTarget = false;
    private int lastExplosionGunFire = 0;
    private int lives = 5;
    private int kills = 0;
    private Zombie offlineEntity;

    private String name; // avoid calling getBukkitPlayer everytime

    public BsPlayer(Player player, Color color) {
        this.player = player;
        this.isLoggedOn = true;
        this.color = color;
        this.name = player.getName();
    }

    public BsPlayer(Player player) {
        this.player = player;
        this.isLoggedOn = true;
        this.color = Color.getRandomColor();
    }

    public void setBukkitPlayer(Player player) {
        // Bukkit player object gets changed on relog, so need this
        this.player = player;
    }

    public Player getBukkitPlayer() {
        return player;
    }

    public void setIsLoggedOn(boolean isLoggedOn) {
        this.isLoggedOn = isLoggedOn;
        // either tp the player to the zombie or summon the zombie
        // to replace the player
        if (isLoggedOn) {
            if (offlineEntity == null)
                return;
            player.teleport(offlineEntity.getLocation());
            offlineEntity.remove();
            offlineEntity = null;
        } else {
            spawnOfflineEntity(player.getLocation());
        }
    }

    public void spawnOfflineEntity(Location location) {
        if (offlineEntity != null)
            offlineEntity.remove();

        offlineEntity = (Zombie) player.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        offlineEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999999, 255, false));
        offlineEntity.setCustomName(player.getName());
        offlineEntity.setRemoveWhenFarAway(false);
    }

    public boolean isLoggedOn() {
        return isLoggedOn;
    }

    public void addLive() {
        this.lives++;
    }

    public void removeLive() {
        this.lives--;
    }

    public int getLives() {
        return lives;
    }

    public void addKill() {
        this.kills++;
    }

    public int getKills() {
        return kills;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public Kit getKit() {
        return kit;
    }

    public void setLastHit(Hit hit) {
        this.lastHit = hit;
    }

    public Hit getLastHit() {
        return lastHit;
    }

    public boolean getIsTarget() {
        return isTarget;
    }

    public void setIsTarget(boolean isTarget) {
        this.isTarget = isTarget;
    }

    public Color getColor() {
        return color;
    }

    public boolean isDead() {
        return lives < 1;
    }

    public int getLastExplosionGunFire() {
        return lastExplosionGunFire;
    }

    public void setLastExplosionGunFire(int lastExplosionGunFire) {
        this.lastExplosionGunFire = lastExplosionGunFire;
    }

    public String getName() {
        return name;
    }

    public String getColoredName() {
        return color.getChatColor() + name;
    }

    public String getColoredBoldName() {
        return color.getChatColor() + "§l" + name;
    }
}
