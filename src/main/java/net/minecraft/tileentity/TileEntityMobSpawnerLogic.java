package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.src.MobSpawnerBaseLogic;
import net.minecraft.utils.weighted.WeightedRandomMinecart;
import net.minecraft.world.World;

class TileEntityMobSpawnerLogic extends MobSpawnerBaseLogic {
    /**
     * The mob spawner we deal with
     */
    final TileEntityMobSpawner mobSpawnerEntity;

    TileEntityMobSpawnerLogic(TileEntityMobSpawner par1TileEntityMobSpawner) {
        this.mobSpawnerEntity = par1TileEntityMobSpawner;
    }

    @Override
    public void func_98267_a(int par1) {
        this.mobSpawnerEntity.worldObj.addBlockEvent(this.mobSpawnerEntity.xCoord, this.mobSpawnerEntity.yCoord, this.mobSpawnerEntity.zCoord, Block.mobSpawner.blockID, par1, 0);
    }

    @Override
    public World getSpawnerWorld() {
        return this.mobSpawnerEntity.worldObj;
    }

    @Override
    public int getSpawnerX() {
        return this.mobSpawnerEntity.xCoord;
    }

    @Override
    public int getSpawnerY() {
        return this.mobSpawnerEntity.yCoord;
    }

    @Override
    public int getSpawnerZ() {
        return this.mobSpawnerEntity.zCoord;
    }

    @Override
    public void setRandomMinecart(WeightedRandomMinecart par1WeightedRandomMinecart) {
        super.setRandomMinecart(par1WeightedRandomMinecart);

        if (this.getSpawnerWorld() != null) {
            this.getSpawnerWorld().markBlockForUpdate(this.mobSpawnerEntity.xCoord, this.mobSpawnerEntity.yCoord, this.mobSpawnerEntity.zCoord);
        }
    }
}
