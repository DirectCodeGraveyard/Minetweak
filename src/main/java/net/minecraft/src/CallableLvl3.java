package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLvl3 implements Callable<String> {
    /**
     * Reference to the World object.
     */
    final World theWorld;

    CallableLvl3(World par1World) {
        this.theWorld = par1World;
    }

    /**
     * Returns the result of the ChunkProvider's makeString
     */
    public String getChunkProvider() {
        return this.theWorld.chunkProvider.makeString();
    }

    @Override
    public String call() {
        return this.getChunkProvider();
    }
}
