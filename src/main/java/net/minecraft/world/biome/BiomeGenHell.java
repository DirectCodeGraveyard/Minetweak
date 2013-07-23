package net.minecraft.world.biome;

import net.minecraft.entity.EntityGhast;
import net.minecraft.entity.EntityMagmaCube;
import net.minecraft.entity.EntityPigZombie;
import net.minecraft.src.SpawnListEntry;

public class BiomeGenHell extends BiomeGenBase {
    public BiomeGenHell(int par1) {
        super(par1);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 1, 4, 4));
    }
}
