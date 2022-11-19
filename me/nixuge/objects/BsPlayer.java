package me.nixuge.objects;

import org.bukkit.entity.Player;

import me.nixuge.enums.Color;

public class BsPlayer {
    private final Color color;
    private Kit kit;
    private Player player;
    private Hit lastHit;
    private boolean isLoggedOn;
    private boolean isTarget;
    private int lastExplosionGunFire = 0;
    private int lives = 5;
    private int kills = 0;

    private String name; //avoid calling getBukkitPlayer everytime

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
}
