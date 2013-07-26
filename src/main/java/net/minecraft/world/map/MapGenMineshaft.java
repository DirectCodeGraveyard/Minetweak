package net.minecraft.world.map;

import net.minecraft.utils.MathHelper;
import net.minecraft.world.structure.StructureMineshaftStart;
import net.minecraft.world.structure.StructureStart;

import java.util.Map;
import java.util.Map.Entry;

public class MapGenMineshaft extends MapGenStructure {
    private double field_82673_e = 0.01D;

    public MapGenMineshaft() {
    }

    public MapGenMineshaft(Map par1Map) {

        for (Object o : par1Map.entrySet()) {
            Entry var3 = (Entry) o;

            if ((var3.getKey()).equals("chance")) {
                this.field_82673_e = MathHelper.parseDoubleWithDefault((String) var3.getValue(), this.field_82673_e);
            }
        }
    }

    @Override
    public boolean canSpawnStructureAtCoords(int par1, int par2) {
        return this.rand.nextDouble() < this.field_82673_e && this.rand.nextInt(80) < Math.max(Math.abs(par1), Math.abs(par2));
    }

    @Override
    public StructureStart getStructureStart(int par1, int par2) {
        return new StructureMineshaftStart(this.worldObj, this.rand, par1, par2);
    }
}
