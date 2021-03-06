package net.minecraft.entity;

import net.minecraft.entity.attribute.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.DamageSource;
import net.minecraft.utils.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkCoordinates;

import java.util.Calendar;

public class EntityBat extends EntityAmbientCreature {
    /**
     * Coordinates of where the bat spawned.
     */
    private ChunkCoordinates spawnPosition;

    public EntityBat(World par1World) {
        super(par1World);
        this.setSize(0.5F, 0.9F);
        this.setIsBatHanging(true);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) 0);
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
    protected float getSoundVolume() {
        return 0.1F;
    }

    /**
     * Gets the pitch of living sounds in living entities.
     */
    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95F;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound() {
        return this.getIsBatHanging() && this.rand.nextInt(4) != 0 ? null : "mob.bat.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound() {
        return "mob.bat.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound() {
        return "mob.bat.death";
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity par1Entity) {
    }

    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(6.0D);
    }

    public boolean getIsBatHanging() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void setIsBatHanging(boolean par1) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1) {
            this.dataWatcher.updateObject(16, (byte) (var2 | 1));
        } else {
            this.dataWatcher.updateObject(16, (byte) (var2 & -2));
        }
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.getIsBatHanging()) {
            this.motionX = this.motionY = this.motionZ = 0.0D;
            this.posY = (double) MathHelper.floor_double(this.posY) + 1.0D - (double) this.height;
        } else {
            this.motionY *= 0.6000000238418579D;
        }
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();

        if (this.getIsBatHanging()) {
            if (!this.worldObj.isBlockNormalCube(MathHelper.floor_double(this.posX), (int) this.posY + 1, MathHelper.floor_double(this.posZ))) {
                this.setIsBatHanging(false);
                this.worldObj.playAuxSFXAtEntity(null, 1015, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
            } else {
                if (this.rand.nextInt(200) == 0) {
                    this.rotationYawHead = (float) this.rand.nextInt(360);
                }

                if (this.worldObj.getClosestPlayerToEntity(this, 4.0D) != null) {
                    this.setIsBatHanging(false);
                    this.worldObj.playAuxSFXAtEntity(null, 1015, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
                }
            }
        } else {
            if (this.spawnPosition != null && (!this.worldObj.isAirBlock(this.spawnPosition.posX, this.spawnPosition.posY, this.spawnPosition.posZ) || this.spawnPosition.posY < 1)) {
                this.spawnPosition = null;
            }

            if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ) < 4.0F) {
                this.spawnPosition = new ChunkCoordinates((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int) this.posY + this.rand.nextInt(6) - 2, (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
            }

            double var1 = (double) this.spawnPosition.posX + 0.5D - this.posX;
            double var3 = (double) this.spawnPosition.posY + 0.1D - this.posY;
            double var5 = (double) this.spawnPosition.posZ + 0.5D - this.posZ;
            this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.10000000149011612D;
            this.motionY += (Math.signum(var3) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
            this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.10000000149011612D;
            float var7 = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
            float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
            this.moveForward = 0.5F;
            this.rotationYaw += var8;

            if (this.rand.nextInt(100) == 0 && this.worldObj.isBlockNormalCube(MathHelper.floor_double(this.posX), (int) this.posY + 1, MathHelper.floor_double(this.posZ))) {
                this.setIsBatHanging(true);
            }
        }
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    @Override
    protected void fall(float par1) {
    }

    /**
     * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
     * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
     */
    @Override
    protected void updateFallState(double par1, boolean par3) {
    }

    /**
     * Return whether this entity should NOT trigger a pressure plate or a tripwire.
     */
    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        } else {
            if (!this.worldObj.isRemote && this.getIsBatHanging()) {
                this.setIsBatHanging(false);
            }

            return super.attackEntityFrom(par1DamageSource, par2);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.dataWatcher.updateObject(16, par1NBTTagCompound.getByte("BatFlags"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean getCanSpawnHere() {
        int var1 = MathHelper.floor_double(this.boundingBox.minY);

        if (var1 >= 63) {
            return false;
        } else {
            int var2 = MathHelper.floor_double(this.posX);
            int var3 = MathHelper.floor_double(this.posZ);
            int var4 = this.worldObj.getBlockLightValue(var2, var1, var3);
            byte var5 = 4;
            Calendar var6 = this.worldObj.getCurrentDate();

            if ((var6.get(2) + 1 != 10 || var6.get(5) < 20) && (var6.get(2) + 1 != 11 || var6.get(5) > 3)) {
                if (this.rand.nextBoolean()) {
                    return false;
                }
            } else {
                var5 = 7;
            }

            return var4 <= this.rand.nextInt(var5) && super.getCanSpawnHere();
        }
    }
}
