package net.minecraft.world.structure;

import net.minecraft.block.Block;
import net.minecraft.world.component.ComponentScatteredFeaturePieces2;

import java.util.Random;

public class StructureScatteredFeatureStones extends StructurePieceBlockSelector {
    private StructureScatteredFeatureStones() {
    }

    /**
     * picks Block Ids and Metadata (Silverfish)
     */
    public void selectBlocks(Random par1Random, int par2, int par3, int par4, boolean par5) {
        if (par1Random.nextFloat() < 0.4F) {
            this.selectedBlockId = Block.cobblestone.blockID;
        } else {
            this.selectedBlockId = Block.cobblestoneMossy.blockID;
        }
    }

    public StructureScatteredFeatureStones(ComponentScatteredFeaturePieces2 par1ComponentScatteredFeaturePieces2) {
        this();
    }
}
