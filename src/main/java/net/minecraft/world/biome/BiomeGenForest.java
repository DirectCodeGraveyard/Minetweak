package net.minecraft.world.biome;

import net.minecraft.entity.EntityWolf;
import net.minecraft.src.SpawnListEntry;
import net.minecraft.world.gen.world.WorldGenerator;

import java.util.Random;

public class BiomeGenForest extends BiomeGenBase {
    public BiomeGenForest(int par1) {
        super(par1);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 2;
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator getRandomWorldGenForTrees(Random par1Random) {
        return par1Random.nextInt(5) == 0 ? this.worldGeneratorForest : (par1Random.nextInt(10) == 0 ? this.worldGeneratorBigTree : this.worldGeneratorTrees);
    }
}
