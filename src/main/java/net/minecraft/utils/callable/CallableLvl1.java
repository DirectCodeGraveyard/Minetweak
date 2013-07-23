package net.minecraft.utils.callable;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.concurrent.Callable;

public class CallableLvl1 implements Callable<String> {
    final int field_85179_a;

    /**
     * Reference to the World object.
     */
    final World theWorld;

    public CallableLvl1(World par1World, int par2) {
        this.theWorld = par1World;
        this.field_85179_a = par2;
    }

    public String getWorldEntitiesAsString() {
        try {
            return String.format("ID #%d (%s // %s)", this.field_85179_a, Block.blocksList[this.field_85179_a].getUnlocalizedName(), Block.blocksList[this.field_85179_a].getClass().getCanonicalName());
        } catch (Throwable var2) {
            return "ID #" + this.field_85179_a;
        }
    }

    @Override
    public String call() {
        return this.getWorldEntitiesAsString();
    }
}
