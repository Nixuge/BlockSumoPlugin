package me.nixuge.listeners.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.PlayerManager;
import me.nixuge.utils.game.BsPlayer;
import me.nixuge.utils.specific.PacketUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class PlayerInteractListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        BlockSumo plugin = BlockSumo.getInstance();
        GameManager gameMgr = plugin.getGameMgr();
        PlayerManager pManager = gameMgr.getPlayerMgr();
        // Action action = event.getAction();
        Player p = event.getPlayer();
        World world = p.getWorld();
        BsPlayer bsPlayer = pManager.getExistingBsPlayerFromBukkit(p);
        ItemStack item = event.getItem();
        if (item == null) return;
        Material material = item.getType();
        

        if (material == null) return;
        switch (material) {
            case NETHER_STAR:
                p.sendMessage("§aYou gained a life !");
                bsPlayer.addLive();
                int amount = p.getItemInHand().getAmount();
                if (amount > 1) {
                    p.getItemInHand().setAmount(amount - 1);
                } else {
                    p.setItemInHand(null);
                }
                break;
            case GOLD_HOE:
                Location loc = p.getLocation();
                double yaw = loc.getYaw();
                double pitch = loc.getPitch();
                double x = loc.getX();
                double y = loc.getY();
                double z = loc.getZ();
                for (int i=0; i < 40; i++) {
                    x -= Math.sin(Math.toRadians(yaw));
                    z += Math.cos(Math.toRadians(yaw));
                    y -= Math.sin(Math.toRadians(pitch));

                    PacketPlayOutWorldParticles packet = PacketUtils.getParticlePacket(EnumParticle.FIREWORKS_SPARK, new Location(
                        Bukkit.getWorld("world"), x, y+1, z), 0, 0, 0, 10);
                    
                    PacketUtils.sendPacketAllPlayers(packet);

                    loc = new Location(gameMgr.getMcMap().getWorld(), x, y, z); 
                    if (loc.getBlock().getType() != Material.AIR) {
                        p.sendMessage("§4BOOM!");
                        gameMgr.getMcMap().getWorld().createExplosion(loc, 3, false);
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
                p.sendMessage("bad aim");
                break;
            
            case BLAZE_POWDER:
                p.setItemInHand(null);
                for (BsPlayer innerBsPlayer : pManager.getPlayers()) {
                    if (innerBsPlayer == bsPlayer) return;
                    Location innerLoc = innerBsPlayer.getBukkitPlayer().getLocation();

                    TNTPrimed tnt = (TNTPrimed) world.spawnEntity(new Location(world, innerLoc.getX(), 100, innerLoc.getZ()) , EntityType.PRIMED_TNT);
                    tnt.setYield(3f); //reduce tnt size
                    tnt.setFuseTicks(80);
                    return;
                }
                break;

            default:
                break;
        }
    }
}
