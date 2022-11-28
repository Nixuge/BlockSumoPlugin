package me.nixuge.reflections;

import me.nixuge.BlockSumo;
import me.nixuge.enums.Color;
import me.nixuge.reflections.particleUtils.ParticleEnum;
import me.nixuge.reflections.particleUtils.ParticleUtils1_7;
import me.nixuge.reflections.particleUtils.ParticleUtils1_8;
import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class ParticleUtils extends ReflectionUtils {
    // Note for this one:
    // currently not using reflections for these classes
    // this means however that you need to import both
    // spigot 1.7 & 1.8 to build w/o errors
    // idk if i'm gonna change that, since those 2 versions are the only ones
    // to work their way anyways

    private static BlockSumo main = BlockSumo.getInstance();
    private static String mcVersion = main.getMcVersion();
    // private static Float mcVersionF = main.getMcVersionFloat();

    public static void sendParticlePacket(ParticleEnum particle, double x, double y, double z,
            double xPlus, double yPlus, double zPlus, int count) {
        sendParticlePacket(particle, x, y, z, xPlus, yPlus, zPlus, count, null, null);
    }

    public static void sendParticlePacket(ParticleEnum particle, double x, double y, double z,
            double xPlus, double yPlus, double zPlus, int count, int[] data, Color color) {
        switch (mcVersion) {
            case "1.8":
                ParticleUtils1_8.sendPacketAllPlayers(
                        ParticleUtils1_8.getParticlePacket(particle, x, y, z, xPlus, yPlus, zPlus, count, data));
                break;

            case "1.7":
                ParticleUtils1_7.sendPacketAllPlayers(
                        ParticleUtils1_7.getParticlePacket(particle, x, y, z, color, count));
                break;

            default:
                // TODO: check for other versions
                Logger.logBC(LogLevel.ERROR, "UNKNOWN MC VERSION: " + mcVersion);
                break;
        }

    }
}
