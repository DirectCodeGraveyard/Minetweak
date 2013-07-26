package net.minecraft.world.structure;

final class StructureStrongholdPieceWeight2 extends StructureStrongholdPieceWeight {
    StructureStrongholdPieceWeight2(Class par1Class, int par2, int par3) {
        super(par1Class, par2, par3);
    }

    @Override
    public boolean canSpawnMoreStructuresOfType(int par1) {
        return super.canSpawnMoreStructuresOfType(par1) && par1 > 4;
    }
}
