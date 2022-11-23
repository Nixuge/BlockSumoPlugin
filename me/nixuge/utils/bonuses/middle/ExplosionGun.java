package me.nixuge.utils.bonuses.middle;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.nixuge.GameManager;
import me.nixuge.config.Lang;
import me.nixuge.objects.BsPlayer;
import me.nixuge.utils.PacketUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

//1.8 1_8 only

public class ExplosionGun {
    public static void run(GameManager gameMgr, BsPlayer bsPlayer, ItemStack item) {
        Player p = bsPlayer.getBukkitPlayer();

        int timer = gameMgr.getGameRunnable().getTime();
        int lastExplosionGunFire = bsPlayer.getLastExplosionGunFire();
        if (lastExplosionGunFire + 2 > timer) {
            p.sendMessage(Lang.get("bonuses.explosiongunwait"));
            return;
        }

        Location loc = p.getLocation();
        double yaw = loc.getYaw();
        double pitch = loc.getPitch();
        double x = loc.getX();
        double y = loc.getY() + 1;
        double z = loc.getZ();
        for (int i=0; i < 40; i++) {
            x -= Math.sin(Math.toRadians(yaw));
            z += Math.cos(Math.toRadians(yaw));
            y -= Math.sin(Math.toRadians(pitch));

            PacketPlayOutWorldParticles packet = PacketUtils.getParticlePacket(EnumParticle.FIREWORKS_SPARK, new Location(
                p.getWorld(), x, y, z), 0, 0, 0, 10);
            
            PacketUtils.sendPacketAllPlayers(packet);

            loc = new Location(gameMgr.getMcMap().getWorld(), x, y, z); 
            if (loc.getBlock().getType() != Material.AIR) {
                p.sendMessage(Lang.get("bonuses.explosiongun"));
                p.getWorld().createExplosion(loc, 3f, false);
                bsPlayer.setLastExplosionGunFire(timer);
                switch (item.getDurability()) {
                    case 0:
                        item.setDurability((short)11);
                        break;
                    case 11:
                        item.setDurability((short)22);
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
}
