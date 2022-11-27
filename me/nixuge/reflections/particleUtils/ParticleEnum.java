package me.nixuge.reflections.particleUtils;

public enum ParticleEnum {
    // All particles available as of 1.7
    // Taken from
    // https://github.com/vmarchaud/Alkazia/blob/master/1.7.10/Spigot/src/main/java/net/minecraft/server/PacketPlayOutWorldParticles.java
    EXPLOSION_NORMAL("explode"),
    EXPLOSION_LARGE("largeexplode"),
    EXPLOSION_HUGE("hugeexplosion"),
    FIREWORKS_SPARK("fireworksSpark"),
    WATER_BUBBLE("bubble"),
    WATER_SPLASH("splash"),
    WATER_WAKE("wake"),
    SUSPENDED("suspended"),
    SUSPENDED_DEPTH("depthsuspend"),
    CRIT("crit"),
    CRIT_MAGIC("magicCrit"),
    SMOKE_NORMAL("smoke"),
    SMOKE_LARGE("largesmoke"),
    SPELL("spell"),
    SPELL_INSTANT("instantSpell"),
    SPELL_MOB("mobSpell"),
    SPELL_MOB_AMBIENT("mobSpellAmbient"),
    SPELL_WITCH("witchMagic"),
    DRIP_WATER("dripWater"),
    DRIP_LAVA("dripLava"),
    VILLAGER_ANGRY("angryVillager"),
    VILLAGER_HAPPY("happyVillager"),
    TOWN_AURA("townaura"),
    NOTE("note"),
    PORTAL("portal"),
    ENCHANTMENT_TABLE("enchantmenttable"),
    FLAME("flame"),
    LAVA("lava"),
    FOOTSTEP("footstep"),
    CLOUD("cloud"),
    REDSTONE("reddust"),
    SNOWBALL("snowballpoof"),
    SNOW_SHOVEL("snowshovel"),
    SLIME("slime"),
    HEART("heart"),
    BARRIER("barrier"),
    ICON_CRACK("iconcrack", 2),
    BLOCK_CRACK("blockcrack", 1),
    BLOCK_DUST("blockdust", 1),
    WATER_DROP("droplet"),
    ITEM_TAKE("take"),
    MOB_APPEARANCE("mobappearance");

    public final String name;
    public final int extra;

    ParticleEnum(String name) {
        this(name, 0);
    }

    ParticleEnum(String name, int extra) {
        this.name = name;
        this.extra = extra;
    }
    public String getParticleName() {
        return name;
    }
    public int getExtra() {
        return extra;
    }
}
