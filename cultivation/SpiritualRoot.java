package net.mcreator.sword.cultivation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.util.RandomSource;

public enum SpiritualRoot {
    NONE("无灵根", 0, 0),
    METAL("金灵根",1, 1.2),
    WOOD("木灵根", 1, 1.1),
    WATER("水灵根", 1, 1.15),
    FIRE("火灵根", 1, 1.25),
    EARTH("土灵根", 1, 1.1),
    METAL_WOOD("金木双灵根", 2, 1.3),
    METAL_WATER("金水双灵根", 2, 1.35),
    METAL_FIRE("金火双灵根", 2, 1.45),
    METAL_EARTH("金土双灵根", 2, 1.3),
    WOOD_WATER("木水双灵根", 2, 1.25),
    WOOD_FIRE("木火双灵根", 2, 1.35),
    WOOD_EARTH("木土双灵根", 2, 1.2),
    WATER_FIRE("水火双灵根", 2, 1.4),
    WATER_EARTH("水土双灵根", 2, 1.25),
    FIRE_EARTH("火土双灵根", 2, 1.35),
    METAL_WOOD_WATER("金木水三灵根", 3, 1.4),
    METAL_WOOD_FIRE("金木火三灵根", 3, 1.5),
    METAL_WOOD_EARTH("金木土三灵根", 3, 1.4),
    METAL_WATER_FIRE("金水火三灵根", 3, 1.55),
    METAL_WATER_EARTH("金水土三灵根", 3, 1.45),
    METAL_FIRE_EARTH("金火土三灵根", 3, 1.5),
    WOOD_WATER_FIRE("木水火三灵根", 3, 1.45),
    WOOD_WATER_EARTH("木水土三灵根", 3, 1.35),
    WOOD_FIRE_EARTH("木火土三灵根", 3, 1.45),
    WATER_FIRE_EARTH("水火土三灵根", 3, 1.5),
    METAL_WOOD_WATER_FIRE("金木水火四灵根", 4, 1.6),
    METAL_WOOD_WATER_EARTH("金木水土四灵根", 4, 1.5),
    METAL_WOOD_FIRE_EARTH("金木火土四灵根", 4, 1.55),
    METAL_WATER_FIRE_EARTH("金水火土四灵根", 4, 1.6),
    WOOD_WATER_FIRE_EARTH("木水火土四灵根", 4, 1.55),
    METAL_WOOD_WATER_FIRE_EARTH("金木水火土五灵根", 5, 1.7);

    private final String displayName;
    private final int rootCount;
    private final double expMultiplier;

    SpiritualRoot(String displayName, int rootCount, double expMultiplier) {
        this.displayName = displayName;
        this.rootCount = rootCount;
        this.expMultiplier = expMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getRootCount() {
        return rootCount;
    }

    public double getExpMultiplier() {
        return expMultiplier;
    }

    public static SpiritualRoot randomRoot(RandomSource random) {
        int roll = random.nextInt(100);
        if (roll < 5) return METAL;
        if (roll < 10) return WOOD;
        if (roll < 15) return WATER;
        if (roll < 20) return FIRE;
        if (roll < 25) return EARTH;
        if (roll < 35) return METAL_WOOD;
        if (roll < 45) return METAL_WATER;
        if (roll < 50) return METAL_FIRE;
        if (roll < 55) return METAL_EARTH;
        if (roll < 60) return WOOD_WATER;
        if (roll < 65) return WOOD_FIRE;
        if (roll < 70) return WOOD_EARTH;
        if (roll < 75) return WATER_FIRE;
        if (roll < 80) return WATER_EARTH;
        if (roll < 85) return FIRE_EARTH;
        if (roll < 88) return METAL_WOOD_WATER;
        if (roll < 91) return METAL_WOOD_FIRE;
        if (roll < 93) return METAL_WOOD_EARTH;
        if (roll < 95) return METAL_WATER_FIRE;
        if (roll < 97) return METAL_WATER_EARTH;
        if (roll < 99) return METAL_FIRE_EARTH;
        if (roll < 100) return WOOD_WATER_FIRE;
        return NONE;
    }

    public static List<SpiritualRoot> getSingleRoots() {
        List<SpiritualRoot> roots = new ArrayList<>();
        roots.add(METAL);
        roots.add(WOOD);
        roots.add(WATER);
        roots.add(FIRE);
        roots.add(EARTH);
        return roots;
    }

    public boolean hasElement(SpiritualRoot element) {
        return this.displayName.contains(element.displayName.substring(0, 1));
    }
}
