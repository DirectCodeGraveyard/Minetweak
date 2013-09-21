package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.crafting.merchant.IMerchant;
import net.minecraft.crafting.merchant.MerchantRecipe;
import net.minecraft.crafting.merchant.MerchantRecipeList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.attribute.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.src.DamageSource;
import net.minecraft.src.IMob;
import net.minecraft.src.INpc;
import net.minecraft.utils.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkCoordinates;
import org.minetweak.util.Tuple;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EntityVillager extends EntityAgeable implements IMerchant, INpc {
    private int randomTickDivider;
    private boolean isMating;
    private boolean isPlaying;
    Village villageObj;

    /**
     * This villager's current customer.
     */
    private EntityPlayer buyingPlayer;

    /**
     * Initialises the MerchantRecipeList.java
     */
    private MerchantRecipeList buyingList;
    private int timeUntilReset;

    /**
     * addDefaultEquipmentAndRecipes is called if this is true
     */
    private boolean needsInitialization;
    private int wealth;

    /**
     * Last player to trade with this villager, used for agressivness.
     */
    private String lastBuyingPlayer;
    private boolean field_82190_bM;
    private float field_82191_bN;

    /**
     * Selling list of Villagers items.
     */
    public static final Map<Integer, Tuple> villagersSellingList = new HashMap<Integer, Tuple>();

    /**
     * Selling list of Blacksmith items.
     */
    public static final Map<Integer, Tuple> blacksmithSellingList = new HashMap<Integer, Tuple>();

    public EntityVillager(World par1World) {
        this(par1World, 0);
    }

    public EntityVillager(World par1World, int par2) {
        super(par1World);
        this.setProfession(par2);
        this.setSize(0.6F, 1.8F);
        this.getNavigator().setBreakDoors(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(1, new EntityAITradePlayer(this));
        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTwardsRestriction(this, 0.6D));
        this.tasks.addTask(6, new EntityAIVillagerMate(this));
        this.tasks.addTask(7, new EntityAIFollowGolem(this));
        this.tasks.addTask(8, new EntityAIPlay(this, 0.32D));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityVillager.class, 5.0F, 0.02F));
        this.tasks.addTask(9, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    }

    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5D);
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled() {
        return true;
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void updateAITick() {
        if (--this.randomTickDivider <= 0) {
            this.worldObj.villageCollectionObj.addVillagerPosition(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);

            if (this.villageObj == null) {
                this.func_110177_bN();
            } else {
                ChunkCoordinates var1 = this.villageObj.getCenter();
                this.func_110171_b(var1.posX, var1.posY, var1.posZ, (int) ((float) this.villageObj.getVillageRadius() * 0.6F));

                if (this.field_82190_bM) {
                    this.field_82190_bM = false;
                    this.villageObj.func_82683_b(5);
                }
            }
        }

        if (!this.isTrading() && this.timeUntilReset > 0) {
            --this.timeUntilReset;

            if (this.timeUntilReset <= 0) {
                if (this.needsInitialization) {
                    if (this.buyingList.size() > 1) {

                        for (Object aBuyingList : this.buyingList) {

                            if (((MerchantRecipe) aBuyingList).func_82784_g()) {
                                ((MerchantRecipe) aBuyingList).func_82783_a(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                            }
                        }
                    }

                    this.addDefaultEquipmentAndRecipies(1);
                    this.needsInitialization = false;

                    if (this.villageObj != null && this.lastBuyingPlayer != null) {
                        this.worldObj.setEntityState(this, (byte) 14);
                        this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
                    }
                }

                this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
            }
        }

        super.updateAITick();
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        boolean var3 = var2 != null && var2.itemID == Item.monsterPlacer.itemID;

        if (!var3 && this.isEntityAlive() && !this.isTrading() && !this.isChild()) {
            if (!this.worldObj.isRemote) {
                this.setCustomer(par1EntityPlayer);
                par1EntityPlayer.displayGUIMerchant(this, this.getCustomNameTag());
            }

            return true;
        } else {
            return super.interact(par1EntityPlayer);
        }
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Profession", this.getProfession());
        par1NBTTagCompound.setInteger("Riches", this.wealth);

        if (this.buyingList != null) {
            par1NBTTagCompound.setCompoundTag("Offers", this.buyingList.getRecipiesAsTags());
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setProfession(par1NBTTagCompound.getInteger("Profession"));
        this.wealth = par1NBTTagCompound.getInteger("Riches");

        if (par1NBTTagCompound.hasKey("Offers")) {
            NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Offers");
            this.buyingList = new MerchantRecipeList(var2);
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn() {
        return false;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound() {
        return this.isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound() {
        return "mob.villager.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound() {
        return "mob.villager.death";
    }

    public void setProfession(int par1) {
        this.dataWatcher.updateObject(16, par1);
    }

    public int getProfession() {
        return this.dataWatcher.getWatchableObjectInt(16);
    }

    public boolean isMating() {
        return this.isMating;
    }

    public void setMating(boolean par1) {
        this.isMating = par1;
    }

    public void setPlaying(boolean par1) {
        this.isPlaying = par1;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setRevengeTarget(EntityLivingBase par1EntityLivingBase) {
        super.setRevengeTarget(par1EntityLivingBase);

        if (this.villageObj != null && par1EntityLivingBase != null) {
            this.villageObj.addOrRenewAgressor(par1EntityLivingBase);

            if (par1EntityLivingBase instanceof EntityPlayer) {
                byte var2 = -1;

                if (this.isChild()) {
                    var2 = -3;
                }

                this.villageObj.setReputationForPlayer(((EntityPlayer) par1EntityLivingBase).getCommandSenderName(), var2);

                if (this.isEntityAlive()) {
                    this.worldObj.setEntityState(this, (byte) 13);
                }
            }
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource par1DamageSource) {
        if (this.villageObj != null) {
            Entity var2 = par1DamageSource.getEntity();

            if (var2 != null) {
                if (var2 instanceof EntityPlayer) {
                    this.villageObj.setReputationForPlayer(((EntityPlayer) var2).getCommandSenderName(), -2);
                } else if (var2 instanceof IMob) {
                    this.villageObj.endMatingSeason();
                }
            } else if (var2 == null) {
                EntityPlayer var3 = this.worldObj.getClosestPlayerToEntity(this, 16.0D);

                if (var3 != null) {
                    this.villageObj.endMatingSeason();
                }
            }
        }

        super.onDeath(par1DamageSource);
    }

    public void setCustomer(EntityPlayer par1EntityPlayer) {
        this.buyingPlayer = par1EntityPlayer;
    }

    public EntityPlayer getCustomer() {
        return this.buyingPlayer;
    }

    public boolean isTrading() {
        return this.buyingPlayer != null;
    }

    public void useRecipe(MerchantRecipe par1MerchantRecipe) {
        par1MerchantRecipe.incrementToolUses();
        this.livingSoundTime = -this.getTalkInterval();
        this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());

        if (par1MerchantRecipe.hasSameIDsAs((MerchantRecipe) this.buyingList.get(this.buyingList.size() - 1))) {
            this.timeUntilReset = 40;
            this.needsInitialization = true;

            if (this.buyingPlayer != null) {
                this.lastBuyingPlayer = this.buyingPlayer.getCommandSenderName();
            } else {
                this.lastBuyingPlayer = null;
            }
        }

        if (par1MerchantRecipe.getItemToBuy().itemID == Item.emerald.itemID) {
            this.wealth += par1MerchantRecipe.getItemToBuy().stackSize;
        }
    }

    public void func_110297_a_(ItemStack par1ItemStack) {
        if (!this.worldObj.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20) {
            this.livingSoundTime = -this.getTalkInterval();

            if (par1ItemStack != null) {
                this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
            } else {
                this.playSound("mob.villager.no", this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }

    public MerchantRecipeList getRecipes(EntityPlayer par1EntityPlayer) {
        if (this.buyingList == null) {
            this.addDefaultEquipmentAndRecipies(1);
        }

        return this.buyingList;
    }

    private float func_82188_j(float par1) {
        float var2 = par1 + this.field_82191_bN;
        return var2 > 0.9F ? 0.9F - (var2 - 0.9F) : var2;
    }

    /**
     * based on the villagers profession add items, equipment, and recipies adds par1 random items to the list of things
     * that the villager wants to buy. (at most 1 of each wanted type is added)
     */
    private void addDefaultEquipmentAndRecipies(int par1) {
        if (this.buyingList != null) {
            this.field_82191_bN = MathHelper.sqrt_float((float) this.buyingList.size()) * 0.2F;
        } else {
            this.field_82191_bN = 0.0F;
        }

        MerchantRecipeList var2;
        var2 = new MerchantRecipeList();
        int var6;
        label50:

        switch (this.getProfession()) {
            case 0:
                addMerchantItem(var2, Item.wheat.itemID, this.rand, this.func_82188_j(0.9F));
                addMerchantItem(var2, Block.cloth.blockID, this.rand, this.func_82188_j(0.5F));
                addMerchantItem(var2, Item.chickenRaw.itemID, this.rand, this.func_82188_j(0.5F));
                addMerchantItem(var2, Item.fishCooked.itemID, this.rand, this.func_82188_j(0.4F));
                addBlacksmithItem(var2, Item.bread.itemID, this.rand, this.func_82188_j(0.9F));
                addBlacksmithItem(var2, Item.melon.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.appleRed.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.cookie.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.shears.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.flintAndSteel.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.chickenCooked.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.arrow.itemID, this.rand, this.func_82188_j(0.5F));

                if (this.rand.nextFloat() < this.func_82188_j(0.5F)) {
                    var2.add(new MerchantRecipe(new ItemStack(Block.gravel, 10), new ItemStack(Item.emerald), new ItemStack(Item.flint.itemID, 4 + this.rand.nextInt(2), 0)));
                }

                break;

            case 1:
                addMerchantItem(var2, Item.paper.itemID, this.rand, this.func_82188_j(0.8F));
                addMerchantItem(var2, Item.book.itemID, this.rand, this.func_82188_j(0.8F));
                addMerchantItem(var2, Item.writtenBook.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Block.bookShelf.blockID, this.rand, this.func_82188_j(0.8F));
                addBlacksmithItem(var2, Block.glass.blockID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.compass.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.pocketSundial.itemID, this.rand, this.func_82188_j(0.2F));

                if (this.rand.nextFloat() < this.func_82188_j(0.07F)) {
                    Enchantment var8 = Enchantment.field_92090_c[this.rand.nextInt(Enchantment.field_92090_c.length)];
                    int var10 = MathHelper.getRandomIntegerInRange(this.rand, var8.getMinLevel(), var8.getMaxLevel());
                    ItemStack var11 = Item.enchantedBook.func_92111_a(new EnchantmentData(var8, var10));
                    var6 = 2 + this.rand.nextInt(5 + var10 * 10) + 3 * var10;
                    var2.add(new MerchantRecipe(new ItemStack(Item.book), new ItemStack(Item.emerald, var6), var11));
                }

                break;

            case 2:
                addBlacksmithItem(var2, Item.eyeOfEnder.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.expBottle.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.redstone.itemID, this.rand, this.func_82188_j(0.4F));
                addBlacksmithItem(var2, Block.glowStone.blockID, this.rand, this.func_82188_j(0.3F));
                int[] var3 = new int[]{Item.swordIron.itemID, Item.swordDiamond.itemID, Item.plateIron.itemID, Item.plateDiamond.itemID, Item.axeIron.itemID, Item.axeDiamond.itemID, Item.pickaxeIron.itemID, Item.pickaxeDiamond.itemID};
                int var5 = var3.length;
                var6 = 0;

                while (true) {
                    if (var6 >= var5) {
                        break label50;
                    }

                    int var7 = var3[var6];

                    if (this.rand.nextFloat() < this.func_82188_j(0.05F)) {
                        var2.add(new MerchantRecipe(new ItemStack(var7, 1, 0), new ItemStack(Item.emerald, 2 + this.rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(this.rand, new ItemStack(var7, 1, 0), 5 + this.rand.nextInt(15))));
                    }

                    ++var6;
                }

            case 3:
                addMerchantItem(var2, Item.coal.itemID, this.rand, this.func_82188_j(0.7F));
                addMerchantItem(var2, Item.ingotIron.itemID, this.rand, this.func_82188_j(0.5F));
                addMerchantItem(var2, Item.ingotGold.itemID, this.rand, this.func_82188_j(0.5F));
                addMerchantItem(var2, Item.diamond.itemID, this.rand, this.func_82188_j(0.5F));
                addBlacksmithItem(var2, Item.swordIron.itemID, this.rand, this.func_82188_j(0.5F));
                addBlacksmithItem(var2, Item.swordDiamond.itemID, this.rand, this.func_82188_j(0.5F));
                addBlacksmithItem(var2, Item.axeIron.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.axeDiamond.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.pickaxeIron.itemID, this.rand, this.func_82188_j(0.5F));
                addBlacksmithItem(var2, Item.pickaxeDiamond.itemID, this.rand, this.func_82188_j(0.5F));
                addBlacksmithItem(var2, Item.shovelIron.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.shovelDiamond.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.hoeIron.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.hoeDiamond.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.bootsIron.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.bootsDiamond.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.helmetIron.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.helmetDiamond.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.plateIron.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.plateDiamond.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.legsIron.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.legsDiamond.itemID, this.rand, this.func_82188_j(0.2F));
                addBlacksmithItem(var2, Item.bootsChain.itemID, this.rand, this.func_82188_j(0.1F));
                addBlacksmithItem(var2, Item.helmetChain.itemID, this.rand, this.func_82188_j(0.1F));
                addBlacksmithItem(var2, Item.plateChain.itemID, this.rand, this.func_82188_j(0.1F));
                addBlacksmithItem(var2, Item.legsChain.itemID, this.rand, this.func_82188_j(0.1F));
                break;

            case 4:
                addMerchantItem(var2, Item.coal.itemID, this.rand, this.func_82188_j(0.7F));
                addMerchantItem(var2, Item.porkRaw.itemID, this.rand, this.func_82188_j(0.5F));
                addMerchantItem(var2, Item.beefRaw.itemID, this.rand, this.func_82188_j(0.5F));
                addBlacksmithItem(var2, Item.saddle.itemID, this.rand, this.func_82188_j(0.1F));
                addBlacksmithItem(var2, Item.plateLeather.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.bootsLeather.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.helmetLeather.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.legsLeather.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.porkCooked.itemID, this.rand, this.func_82188_j(0.3F));
                addBlacksmithItem(var2, Item.beefCooked.itemID, this.rand, this.func_82188_j(0.3F));
        }

        if (var2.isEmpty()) {
            addMerchantItem(var2, Item.ingotGold.itemID, this.rand, 1.0F);
        }

        Collections.shuffle(var2);

        if (this.buyingList == null) {
            this.buyingList = new MerchantRecipeList();
        }

        for (int var9 = 0; var9 < par1 && var9 < var2.size(); ++var9) {
            this.buyingList.addToListWithCheck((MerchantRecipe) var2.get(var9));
        }
    }

    /**
     * each recipie takes a random stack from villagerStockList and offers it for 1 emerald
     */
    private static void addMerchantItem(MerchantRecipeList par0MerchantRecipeList, int par1, Random par2Random, float par3) {
        if (par2Random.nextFloat() < par3) {
            par0MerchantRecipeList.add(new MerchantRecipe(getRandomSizedStack(par1, par2Random), Item.emerald));
        }
    }

    private static ItemStack getRandomSizedStack(int par0, Random par1Random) {
        return new ItemStack(par0, getRandomCountForItem(par0, par1Random), 0);
    }

    /**
     * default to 1, and villagerStockList contains a min/max amount for each index
     */
    private static int getRandomCountForItem(int par0, Random par1Random) {
        Tuple var2 = villagersSellingList.get(par0);
        return var2 == null ? 1 : ((Integer) var2.getFirst() >= (Integer) var2.getSecond() ? (Integer) var2.getFirst() : (Integer) var2.getFirst() + par1Random.nextInt((Integer) var2.getSecond() - (Integer) var2.getFirst()));
    }

    private static void addBlacksmithItem(MerchantRecipeList par0MerchantRecipeList, int par1, Random par2Random, float par3) {
        if (par2Random.nextFloat() < par3) {
            int var4 = getRandomCountForBlacksmithItem(par1, par2Random);
            ItemStack var5;
            ItemStack var6;

            if (var4 < 0) {
                var5 = new ItemStack(Item.emerald.itemID, 1, 0);
                var6 = new ItemStack(par1, -var4, 0);
            } else {
                var5 = new ItemStack(Item.emerald.itemID, var4, 0);
                var6 = new ItemStack(par1, 1, 0);
            }

            par0MerchantRecipeList.add(new MerchantRecipe(var5, var6));
        }
    }

    private static int getRandomCountForBlacksmithItem(int par0, Random par1Random) {
        Tuple var2 = blacksmithSellingList.get(par0);
        return var2 == null ? 1 : ((Integer) var2.getFirst() >= (Integer) var2.getSecond() ? (Integer) var2.getFirst() : (Integer) var2.getFirst() + par1Random.nextInt((Integer) var2.getSecond() - (Integer) var2.getFirst()));
    }

    public EntityLivingData updateLivingData(EntityLivingData par1EntityLivingData) {
        par1EntityLivingData = super.updateLivingData(par1EntityLivingData);
        this.setProfession(this.worldObj.rand.nextInt(5));
        return par1EntityLivingData;
    }

    public void func_82187_q() {
        this.field_82190_bM = true;
    }

    public EntityVillager func_90012_b(EntityAgeable par1EntityAgeable) {
        EntityVillager var2 = new EntityVillager(this.worldObj);
        var2.updateLivingData(null);
        return var2;
    }

    public boolean func_110164_bC() {
        return false;
    }

    public EntityAgeable createChild(EntityAgeable par1EntityAgeable) {
        return this.func_90012_b(par1EntityAgeable);
    }

    static {
        villagersSellingList.put(Item.coal.itemID, new Tuple(16, 24));
        villagersSellingList.put(Item.ingotIron.itemID, new Tuple(8, 10));
        villagersSellingList.put(Item.ingotGold.itemID, new Tuple(8, 10));
        villagersSellingList.put(Item.diamond.itemID, new Tuple(4, 6));
        villagersSellingList.put(Item.paper.itemID, new Tuple(24, 36));
        villagersSellingList.put(Item.book.itemID, new Tuple(11, 13));
        villagersSellingList.put(Item.writtenBook.itemID, new Tuple(1, 1));
        villagersSellingList.put(Item.enderPearl.itemID, new Tuple(3, 4));
        villagersSellingList.put(Item.eyeOfEnder.itemID, new Tuple(2, 3));
        villagersSellingList.put(Item.porkRaw.itemID, new Tuple(14, 18));
        villagersSellingList.put(Item.beefRaw.itemID, new Tuple(14, 18));
        villagersSellingList.put(Item.chickenRaw.itemID, new Tuple(14, 18));
        villagersSellingList.put(Item.fishCooked.itemID, new Tuple(9, 13));
        villagersSellingList.put(Item.seeds.itemID, new Tuple(34, 48));
        villagersSellingList.put(Item.melonSeeds.itemID, new Tuple(30, 38));
        villagersSellingList.put(Item.pumpkinSeeds.itemID, new Tuple(30, 38));
        villagersSellingList.put(Item.wheat.itemID, new Tuple(18, 22));
        villagersSellingList.put(Block.cloth.blockID, new Tuple(14, 22));
        villagersSellingList.put(Item.rottenFlesh.itemID, new Tuple(36, 64));
        blacksmithSellingList.put(Item.flintAndSteel.itemID, new Tuple(3, 4));
        blacksmithSellingList.put(Item.shears.itemID, new Tuple(3, 4));
        blacksmithSellingList.put(Item.swordIron.itemID, new Tuple(7, 11));
        blacksmithSellingList.put(Item.swordDiamond.itemID, new Tuple(12, 14));
        blacksmithSellingList.put(Item.axeIron.itemID, new Tuple(6, 8));
        blacksmithSellingList.put(Item.axeDiamond.itemID, new Tuple(9, 12));
        blacksmithSellingList.put(Item.pickaxeIron.itemID, new Tuple(7, 9));
        blacksmithSellingList.put(Item.pickaxeDiamond.itemID, new Tuple(10, 12));
        blacksmithSellingList.put(Item.shovelIron.itemID, new Tuple(4, 6));
        blacksmithSellingList.put(Item.shovelDiamond.itemID, new Tuple(7, 8));
        blacksmithSellingList.put(Item.hoeIron.itemID, new Tuple(4, 6));
        blacksmithSellingList.put(Item.hoeDiamond.itemID, new Tuple(7, 8));
        blacksmithSellingList.put(Item.bootsIron.itemID, new Tuple(4, 6));
        blacksmithSellingList.put(Item.bootsDiamond.itemID, new Tuple(7, 8));
        blacksmithSellingList.put(Item.helmetIron.itemID, new Tuple(4, 6));
        blacksmithSellingList.put(Item.helmetDiamond.itemID, new Tuple(7, 8));
        blacksmithSellingList.put(Item.plateIron.itemID, new Tuple(10, 14));
        blacksmithSellingList.put(Item.plateDiamond.itemID, new Tuple(16, 19));
        blacksmithSellingList.put(Item.legsIron.itemID, new Tuple(8, 10));
        blacksmithSellingList.put(Item.legsDiamond.itemID, new Tuple(11, 14));
        blacksmithSellingList.put(Item.bootsChain.itemID, new Tuple(5, 7));
        blacksmithSellingList.put(Item.helmetChain.itemID, new Tuple(5, 7));
        blacksmithSellingList.put(Item.plateChain.itemID, new Tuple(11, 15));
        blacksmithSellingList.put(Item.legsChain.itemID, new Tuple(9, 11));
        blacksmithSellingList.put(Item.bread.itemID, new Tuple(-4, -2));
        blacksmithSellingList.put(Item.melon.itemID, new Tuple(-8, -4));
        blacksmithSellingList.put(Item.appleRed.itemID, new Tuple(-8, -4));
        blacksmithSellingList.put(Item.cookie.itemID, new Tuple(-10, -7));
        blacksmithSellingList.put(Block.glass.blockID, new Tuple(-5, -3));
        blacksmithSellingList.put(Block.bookShelf.blockID, new Tuple(3, 4));
        blacksmithSellingList.put(Item.plateLeather.itemID, new Tuple(4, 5));
        blacksmithSellingList.put(Item.bootsLeather.itemID, new Tuple(2, 4));
        blacksmithSellingList.put(Item.helmetLeather.itemID, new Tuple(2, 4));
        blacksmithSellingList.put(Item.legsLeather.itemID, new Tuple(2, 4));
        blacksmithSellingList.put(Item.saddle.itemID, new Tuple(6, 8));
        blacksmithSellingList.put(Item.expBottle.itemID, new Tuple(-4, -1));
        blacksmithSellingList.put(Item.redstone.itemID, new Tuple(-4, -1));
        blacksmithSellingList.put(Item.compass.itemID, new Tuple(10, 12));
        blacksmithSellingList.put(Item.pocketSundial.itemID, new Tuple(10, 12));
        blacksmithSellingList.put(Block.glowStone.blockID, new Tuple(-3, -1));
        blacksmithSellingList.put(Item.porkCooked.itemID, new Tuple(-7, -5));
        blacksmithSellingList.put(Item.beefCooked.itemID, new Tuple(-7, -5));
        blacksmithSellingList.put(Item.chickenCooked.itemID, new Tuple(-8, -6));
        blacksmithSellingList.put(Item.eyeOfEnder.itemID, new Tuple(7, 11));
        blacksmithSellingList.put(Item.arrow.itemID, new Tuple(-12, -8));
    }
}
