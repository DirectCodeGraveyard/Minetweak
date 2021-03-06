package net.minecraft.world.biome;

import net.minecraft.entity.EntityWolf;
import net.minecraft.src.SpawnListEntry;
import net.minecraft.world.gen.world.WorldGenTaiga1;
import net.minecraft.world.gen.world.WorldGenTaiga2;
import net.minecraft.world.gen.world.WorldGenerator;

import java.util.Random;

public class BiomeGenTaiga extends BiomeGenBase {
    public BiomeGenTaiga(int par1) {
        super(par1);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 1;
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator getRandomWorldGenForTrees(Random par1Random) {
        return par1Random.nextInt(3) == 0 ? new WorldGenTaiga1() : new WorldGenTaiga2(false);
    }
}
