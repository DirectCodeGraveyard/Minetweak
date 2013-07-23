package net.minecraft.world.anvil;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.ChunkCoordIntPair;

class AnvilChunkLoaderPending {
    public final ChunkCoordIntPair chunkCoordinate;
    public final NBTTagCompound nbtTags;

    public AnvilChunkLoaderPending(ChunkCoordIntPair par1ChunkCoordIntPair, NBTTagCompound par2NBTTagCompound) {
        this.chunkCoordinate = par1ChunkCoordIntPair;
        this.nbtTags = par2NBTTagCompound;
    }
}
