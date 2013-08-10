package net.minecraft.world.structure;

import net.minecraft.world.World;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("UnusedParameters")
public abstract class StructureStart {
    /**
     * List of all StructureComponents that are part of this structure
     */
    protected LinkedList components = new LinkedList();
    protected StructureBoundingBox boundingBox;

    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public LinkedList getComponents() {
        return this.components;
    }

    /**
     * Keeps iterating Structure Pieces and spawning them until the checks tell it to stop
     */
    public void generateStructure(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox) {
        Iterator var4 = this.components.iterator();

        while (var4.hasNext()) {
            StructureComponent var5 = (StructureComponent) var4.next();

            if (var5.getBoundingBox().intersectsWith(par3StructureBoundingBox) && !var5.addComponentParts(par1World, par2Random, par3StructureBoundingBox)) {
                var4.remove();
            }
        }
    }

    /**
     * Calculates total bounding box based on components' bounding boxes and saves it to boundingBox
     */
    protected void updateBoundingBox() {
        this.boundingBox = StructureBoundingBox.getNewBoundingBox();

        for (Object component : this.components) {
            StructureComponent var2 = (StructureComponent) component;
            this.boundingBox.expandTo(var2.getBoundingBox());
        }
    }

    /**
     * offsets the structure Bounding Boxes up to a certain height, typically 63 - 10
     */
    protected void markAvailableHeight(World par1World, Random par2Random, int par3) {
        int var4 = 63 - par3;
        int var5 = this.boundingBox.getYSize() + 1;

        if (var5 < var4) {
            var5 += par2Random.nextInt(var4 - var5);
        }

        int var6 = var5 - this.boundingBox.maxY;
        this.boundingBox.offset(0, var6, 0);

        for (Object component : this.components) {
            StructureComponent var8 = (StructureComponent) component;
            var8.getBoundingBox().offset(0, var6, 0);
        }
    }

    protected void setRandomHeight(World par1World, Random par2Random, int par3, int par4) {
        int var5 = par4 - par3 + 1 - this.boundingBox.getYSize();
        int var10;

        if (var5 > 1) {
            var10 = par3 + par2Random.nextInt(var5);
        } else {
            var10 = par3;
        }

        int var7 = var10 - this.boundingBox.minY;
        this.boundingBox.offset(0, var7, 0);

        for (Object component : this.components) {
            StructureComponent var9 = (StructureComponent) component;
            var9.getBoundingBox().offset(0, var7, 0);
        }
    }

    /**
     * currently only defined for Villages, returns true if Village has more than 2 non-road components
     */
    public boolean isSizeableStructure() {
        return true;
    }
}
