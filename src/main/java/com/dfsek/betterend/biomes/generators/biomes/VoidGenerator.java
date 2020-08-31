package com.dfsek.betterend.biomes.generators.biomes;

import org.polydev.gaea.terrain2.BiomeTerrain;
import org.polydev.gaea.world.palette.BlockPalette;
import org.polydev.gaea.math.FastNoise;
import org.bukkit.Material;

public class VoidGenerator extends BiomeTerrain {
    private final BlockPalette palette;
    public VoidGenerator() {
        super();
        this.palette = new BlockPalette()
                .add(Material.AIR, 1);
    }

    @Override
    public double getNoise(FastNoise gen, int x, int z) {
        return 0;
    }

    @Override
    public BlockPalette getPalette() {
        return this.palette;
    }
}