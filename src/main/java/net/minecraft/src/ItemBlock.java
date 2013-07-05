package net.minecraft.src;

<<<<<<< HEAD
import org.minetweak.Minetweak;
import org.minetweak.event.block.BlockPlacedEvent;
=======
import org.minetweak.event.block.MineTweakBlockState;
>>>>>>> 1d673b3fd0896baa81a590dc93554e19915831a3

public class ItemBlock extends Item {
    /**
     * The block ID of the Block associated with this ItemBlock
     */
    private int blockID;

    public ItemBlock(int par1) {
        super(par1);
        this.blockID = par1 + 256;
    }

    /**
     * Returns the blockID for this Item
     */
    public int getBlockID() {
        return this.blockID;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        int var11 = par3World.getBlockId(par4, par5, par6);
        final int clickedX = par4, clickedY = par5, clickedZ = par6;

        if (var11 == Block.snow.blockID && (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1) {
            par7 = 1;
        } else if (var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID) {
            if (par7 == 0) {
                --par5;
            }

            if (par7 == 1) {
                ++par5;
            }

            if (par7 == 2) {
                --par6;
            }

            if (par7 == 3) {
                ++par6;
            }

            if (par7 == 4) {
                --par4;
            }

            if (par7 == 5) {
                ++par4;
            }
        }

        if (par1ItemStack.stackSize == 0) {
            return false;
        } else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        } else if (par5 == 255 && Block.blocksList[this.blockID].blockMaterial.isSolid()) {
            return false;
        } else if (par3World.canPlaceEntityOnSide(this.blockID, par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack)) {
            Block var12 = Block.blocksList[this.blockID];
            int var13 = this.getMetadata(par1ItemStack.getItemDamage());
            int var14 = Block.blocksList[this.blockID].onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, var13);

<<<<<<< HEAD
            if (par3World.setBlock(par4, par5, par6, this.blockID, var14, 3)) {
                if (par3World.getBlockId(par4, par5, par6) == this.blockID) {
                    Minetweak.getEventBus().post(new BlockPlacedEvent(Block.blocksList[this.blockID], par2EntityPlayer, par4, par5, par6));
                    Block.blocksList[this.blockID].onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer, par1ItemStack);
                    Block.blocksList[this.blockID].onPostBlockPlaced(par3World, par4, par5, par6, var14);
                }

                par3World.playSoundEffect((double) ((float) par4 + 0.5F), (double) ((float) par5 + 0.5F), (double) ((float) par6 + 0.5F), var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
                --par1ItemStack.stackSize;
            }

            return true;
=======
>>>>>>> 1d673b3fd0896baa81a590dc93554e19915831a3
        } else {
            return false;
        }
    }

    public static boolean processBlockPlace(final World world, final EntityPlayer entityplayer, final ItemStack itemstack, final int x, final int y, final int z, final int id, final int data, final int clickedX, final int clickedY, final int clickedZ)
    {
        org.minetweak.event.block.BlockState blockstate = MineTweakBlockState.getBlockState(world, x, y, z);
        world.callingPlaceEvent = true;
        world.setBlock(x, y, z, id, data, 2);
        org.minetweak.event.block.BlockPlaceEvent event = org.minetweak.event.helper.MineTweakEventFactory.callBlockPlaceEvent(world, entityplayer, blockstate, clickedX, clickedY, clickedZ);

        if (event.isCancelled() || !event.canBuild())
        {
            blockstate.update(true, false);
            world.callingPlaceEvent = false;
            return false;
        }

        world.callingPlaceEvent = false;
        int newId = world.getBlockId(x, y, z);
        int newData = world.getBlockMetadata(x, y, z);
        Block block = Block.blocksList[newId];

        if (block != null && !(block instanceof BlockContainer))   // Containers get placed automatically
        {
            block.onBlockAdded(world, x, y, z);
        }

        world.notifyBlockChange(x, y, z, newId);

        // Skulls don't get block data applied to them
        if (block != null && block != Block.skull)
        {
            block.onBlockPlacedBy(world, x, y, z, entityplayer, itemstack);
            block.onPostBlockPlaced(world, x, y, z, newData);
            world.playSoundEffect((double)((float) x + 0.5F), (double)((float) y + 0.5F), (double)((float) z + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
        }

        if (itemstack != null)
        {
            --itemstack.stackSize;
        }

        return true;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return Block.blocksList[this.blockID].getUnlocalizedName();
    }

    /**
     * Returns the unlocalized name of this item.
     */
    public String getUnlocalizedName() {
        return Block.blocksList[this.blockID].getUnlocalizedName();
    }
}
