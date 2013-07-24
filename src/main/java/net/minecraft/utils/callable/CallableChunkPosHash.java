package net.minecraft.utils.callable;

import net.minecraft.world.chunk.ChunkCoordIntPair;
import net.minecraft.world.map.MapGenStructure;

import java.util.concurrent.Callable;

public class CallableChunkPosHash implements Callable<String> {
    final int field_85165_a;

    final int field_85163_b;

    final MapGenStructure theMapStructureGenerator;

    public CallableChunkPosHash(MapGenStructure par1MapGenStructure, int par2, int par3) {
        this.theMapStructureGenerator = par1MapGenStructure;
        this.field_85165_a = par2;
        this.field_85163_b = par3;
    }

    public String callChunkPositionHash() {
        return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(this.field_85165_a, this.field_85163_b));
    }

    @Override
    public String call() {
        return this.callChunkPositionHash();
    }
}
