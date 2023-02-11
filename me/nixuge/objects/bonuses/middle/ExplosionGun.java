package me.nixuge.objects.bonuses.middle;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.nixuge.GameManager;
import me.nixuge.config.Lang;
import me.nixuge.objects.BsPlayer;
import me.nixuge.reflections.packet.HandleParticleSend;
import me.nixuge.reflections.packet.ParticleEnum;

public class ExplosionGun {
    public static void run(GameManager gameMgr, BsPlayer bsPlayer, ItemStack item) {
        Player p = bsPlayer.getBukkitPlayer();

        int timer = gameMgr.getGameRunnable().getTime();
        int lastExplosionGunFire = bsPlayer.getLastExplosionGunFire();
        if (lastExplosionGunFire + 2 > timer) {
            p.sendMessage(Lang.get("bonuses.explosiongunwait"));
            return;
        }

        // values straight from the player
        Location loc = p.getLocation();
        double yaw = loc.getYaw();
        double pitch = loc.getPitch();
        double x = loc.getX();
        double y = loc.getY() + 1.5; //getY = base of feet, +1.5 = head
        double z = loc.getZ();

        // values computed
        double yMinus = Math.sin(Math.toRadians(pitch));
        double yMultiply = getYMultiplyOffset(yMinus);
        double xMinus = Math.sin(Math.toRadians(yaw)) * yMultiply;
        double zPlus = Math.cos(Math.toRadians(yaw)) * yMultiply;

        for (int i = 0; i < 40; i++) {
            x -= xMinus;
            z += zPlus;
            y -= yMinus;

            new HandleParticleSend(ParticleEnum.FIREWORKS_SPARK, x, y, z, 0, 0, 0, 10, null)
                    .sendPacketAllPlayers();

            loc = new Location(gameMgr.getMcMap().getWorld(), x, y, z);
            if (loc.getBlock().getType() != Material.AIR) {
                p.sendMessage(Lang.get("bonuses.explosiongun"));
                p.getWorld().createExplosion(loc, 3f, false);
                bsPlayer.setLastExplosionGunFire(timer);
                switch (item.getDurability()) {
                    case 0:
                        item.setDurability((short) 11);
                        break;
                    case 11:
                        item.setDurability((short) 22);
                        break;
                    default:
                        p.setItemInHand(null);
                        break;
                }
                return;
            }
        }
        p.sendMessage(Lang.get("bonuses.explosiongunbadaim"));
    }
    
    /**
     * This function is used to calculate the multiply offset for X/Z
     * based on how up/down the player is looking.
     * Without those, the most players can shoot at is diagonal.
     * With those, all orientations are fine
     * 
     * @param yMinus determines how many blocks far down the next particle will be
     */
    private static double getYMultiplyOffset(double yMinus) {
        // get the absolute value of yMinus
        double yMultiply = yMinus > 0 ? yMinus : -yMinus;

        // function sqrt(r² - x²)
        // makes a nice quarter cycle
        return Math.sqrt(1 - (yMultiply * yMultiply));
    }
}
