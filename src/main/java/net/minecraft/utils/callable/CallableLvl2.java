package net.minecraft.utils.callable;

import net.minecraft.world.World;

import java.util.concurrent.Callable;

public class CallableLvl2 implements Callable<String> {
    /**
     * Reference to the World object.
     */
    final World theWorld;

    public CallableLvl2(World par1World) {
        this.theWorld = par1World;
    }

    /**
     * Returns the size and contents of the player entity list.
     */
    public String getPlayerEntities() {
        return this.theWorld.playerEntities.size() + " total; " + this.theWorld.playerEntities.toString();
    }

    @Override
    public String call() {
        return this.getPlayerEntities();
    }
}
