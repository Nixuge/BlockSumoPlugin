package me.nixuge.listeners.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.utils.game.BsPlayer;
import me.nixuge.utils.specific.PacketUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class PlayerInteractListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        BlockSumo plugin = BlockSumo.getInstance();
        GameManager gameMgr = plugin.getGameMgr();
        Action action = event.getAction();
        Player p = event.getPlayer();
        BsPlayer bsPlayer = gameMgr.getPlayerMgr().getExistingBsPlayerFromBukkit(p);
        ItemStack item = event.getItem();
        Material material = item.getType();
        
        switch (material) {
            case NETHER_STAR:
                p.sendMessage("§aYou gained a live !");
                bsPlayer.addLive();
                int amount = p.getItemInHand().getAmount();
                if (amount > 1) {
                    p.getItemInHand().setAmount(amount - 1);
                } else {
                    p.setItemInHand(null);
                }
                break;
            case GOLD_HOE:
                Bukkit.broadcastMessage("<TEST2>");
                Location loc = p.getLocation();
                double yaw = loc.getYaw();
                double pitch = loc.getPitch();
                double x = loc.getX();
                double y = loc.getY();
                double z = loc.getZ();
                for (int i=0; i < 20; i++) {
                    x -= Math.sin(Math.toRadians(yaw));
                    z += Math.cos(Math.toRadians(yaw));
                    y -= Math.sin(Math.toRadians(pitch));
                    
                    PacketPlayOutWorldParticles packet = PacketUtils.getParticlePacket(EnumParticle.FIREWORKS_SPARK, new Location(
                        Bukkit.getWorld("world"), x, y+1, z), 0, 0, 0, 10);
                    
                    PacketUtils.sendPacketAllPlayers(packet);
                }

                
                // Bukkit.broadcastMessage(advancementX + " | a" + advancementZ);



                break;
            default:
                break;
        }
    }
}
