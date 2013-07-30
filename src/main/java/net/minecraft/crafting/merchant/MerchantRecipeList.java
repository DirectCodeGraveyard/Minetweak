package net.minecraft.crafting.merchant;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.network.packet.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MerchantRecipeList extends ArrayList {
    public MerchantRecipeList() {
    }

    public MerchantRecipeList(NBTTagCompound par1NBTTagCompound) {
        this.readRecipiesFromTags(par1NBTTagCompound);
    }

    /**
     * can par1,par2 be used to in crafting crafting par3
     */
    public MerchantRecipe canRecipeBeUsed(ItemStack par1ItemStack, ItemStack par2ItemStack, int par3) {
        if (par3 > 0 && par3 < this.size()) {
            MerchantRecipe var6 = (MerchantRecipe) this.get(par3);
            return par1ItemStack.itemID == var6.getItemToBuy().itemID && (par2ItemStack == null && !var6.hasSecondItemToBuy() || var6.hasSecondItemToBuy() && par2ItemStack != null && var6.getSecondItemToBuy().itemID == par2ItemStack.itemID) && par1ItemStack.stackSize >= var6.getItemToBuy().stackSize && (!var6.hasSecondItemToBuy() || par2ItemStack.stackSize >= var6.getSecondItemToBuy().stackSize) ? var6 : null;
        } else {
            for (Object o : this) {
                MerchantRecipe recipe = (MerchantRecipe) o;
                if (par1ItemStack.itemID == recipe.getItemToBuy().itemID && par1ItemStack.stackSize >= recipe.getItemToBuy().stackSize && (!recipe.hasSecondItemToBuy() && par2ItemStack == null || recipe.hasSecondItemToBuy() && par2ItemStack != null && recipe.getSecondItemToBuy().itemID == par2ItemStack.itemID && par2ItemStack.stackSize >= recipe.getSecondItemToBuy().stackSize)) {
                    return recipe;
                }
            }

            return null;
        }
    }

    /**
     * checks if there is a recipie for the same ingredients already on the list, and replaces it. otherwise, adds it
     */
    public void addToListWithCheck(MerchantRecipe par1MerchantRecipe) {
        for (int var2 = 0; var2 < this.size(); ++var2) {
            MerchantRecipe var3 = (MerchantRecipe) this.get(var2);

            if (par1MerchantRecipe.hasSameIDsAs(var3)) {
                if (par1MerchantRecipe.hasSameItemsAs(var3)) {
                    this.set(var2, par1MerchantRecipe);
                }

                return;
            }
        }

        this.add(par1MerchantRecipe);
    }

    public void writeRecipiesToStream(DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte((byte) (this.size() & 255));

        for (Object o : this) {
            MerchantRecipe var3 = (MerchantRecipe) o;
            Packet.writeItemStack(var3.getItemToBuy(), par1DataOutputStream);
            Packet.writeItemStack(var3.getItemToSell(), par1DataOutputStream);
            ItemStack var4 = var3.getSecondItemToBuy();
            par1DataOutputStream.writeBoolean(var4 != null);

            if (var4 != null) {
                Packet.writeItemStack(var4, par1DataOutputStream);
            }

            par1DataOutputStream.writeBoolean(var3.func_82784_g());
        }
    }

    public void readRecipiesFromTags(NBTTagCompound par1NBTTagCompound) {
        NBTTagList var2 = par1NBTTagCompound.getTagList("Recipes");

        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
            this.add(new MerchantRecipe(var4));
        }
    }

    public NBTTagCompound getRecipiesAsTags() {
        NBTTagCompound var1 = new NBTTagCompound();
        NBTTagList var2 = new NBTTagList("Recipes");

        for (Object o : this) {
            MerchantRecipe var4 = (MerchantRecipe) o;
            var2.appendTag(var4.writeToTags());
        }

        var1.setTag("Recipes", var2);
        return var1;
    }
}
