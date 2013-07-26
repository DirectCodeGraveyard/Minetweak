package net.minecraft.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.GameRules;
import net.minecraft.utils.enums.EnumGameType;

public class DerivedWorldInfo extends WorldInfo {
    /**
     * Instance of WorldInfo.
     */
    private final WorldInfo theWorldInfo;

    public DerivedWorldInfo(WorldInfo par1WorldInfo) {
        this.theWorldInfo = par1WorldInfo;
    }

    /**
     * Gets the NBTTagCompound for the worldInfo
     */
    @Override
    public NBTTagCompound getNBTTagCompound() {
        return this.theWorldInfo.getNBTTagCompound();
    }

    /**
     * Creates a new NBTTagCompound for the world, with the given NBTTag as the "Player"
     */
    @Override
    public NBTTagCompound cloneNBTCompound(NBTTagCompound par1NBTTagCompound) {
        return this.theWorldInfo.cloneNBTCompound(par1NBTTagCompound);
    }

    /**
     * Returns the seed of current world.
     */
    @Override
    public long getSeed() {
        return this.theWorldInfo.getSeed();
    }

    /**
     * Returns the x spawn position
     */
    @Override
    public int getSpawnX() {
        return this.theWorldInfo.getSpawnX();
    }

    /**
     * Return the Y axis spawning point of the player.
     */
    @Override
    public int getSpawnY() {
        return this.theWorldInfo.getSpawnY();
    }

    /**
     * Returns the z spawn position
     */
    @Override
    public int getSpawnZ() {
        return this.theWorldInfo.getSpawnZ();
    }

    @Override
    public long getWorldTotalTime() {
        return this.theWorldInfo.getWorldTotalTime();
    }

    /**
     * Get current world time
     */
    @Override
    public long getWorldTime() {
        return this.theWorldInfo.getWorldTime();
    }

    /**
     * Returns the player's NBTTagCompound to be loaded
     */
    @Override
    public NBTTagCompound getPlayerNBTTagCompound() {
        return this.theWorldInfo.getPlayerNBTTagCompound();
    }

    @Override
    public int getDimension() {
        return this.theWorldInfo.getDimension();
    }

    /**
     * Get current world name
     */
    @Override
    public String getWorldName() {
        return this.theWorldInfo.getWorldName();
    }

    /**
     * Returns the save version of this world
     */
    @Override
    public int getSaveVersion() {
        return this.theWorldInfo.getSaveVersion();
    }

    /**
     * Returns true if it is thundering, false otherwise.
     */
    @Override
    public boolean isThundering() {
        return this.theWorldInfo.isThundering();
    }

    /**
     * Returns the number of ticks until next thunderbolt.
     */
    @Override
    public int getThunderTime() {
        return this.theWorldInfo.getThunderTime();
    }

    /**
     * Returns true if it is raining, false otherwise.
     */
    @Override
    public boolean isRaining() {
        return this.theWorldInfo.isRaining();
    }

    /**
     * Return the number of ticks until rain.
     */
    @Override
    public int getRainTime() {
        return this.theWorldInfo.getRainTime();
    }

    /**
     * Gets the GameType.
     */
    @Override
    public EnumGameType getGameType() {
        return this.theWorldInfo.getGameType();
    }

    @Override
    public void incrementTotalWorldTime(long par1) {
    }

    /**
     * Set current world time
     */
    @Override
    public void setWorldTime(long par1) {
    }

    /**
     * Sets the spawn zone position. Args: x, y, z
     */
    @Override
    public void setSpawnPosition(int par1, int par2, int par3) {
    }

    @Override
    public void setWorldName(String par1Str) {
    }

    /**
     * Sets the save version of the world
     */
    @Override
    public void setSaveVersion(int par1) {
    }

    /**
     * Sets whether it is thundering or not.
     */
    @Override
    public void setThundering(boolean par1) {
    }

    /**
     * Defines the number of ticks until next thunderbolt.
     */
    @Override
    public void setThunderTime(int par1) {
    }

    /**
     * Sets whether it is raining or not.
     */
    @Override
    public void setRaining(boolean par1) {
    }

    /**
     * Sets the number of ticks until rain.
     */
    @Override
    public void setRainTime(int par1) {
    }

    /**
     * Get whether the map features (e.g. strongholds) generation is enabled or disabled.
     */
    @Override
    public boolean isMapFeaturesEnabled() {
        return this.theWorldInfo.isMapFeaturesEnabled();
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    @Override
    public boolean isHardcoreModeEnabled() {
        return this.theWorldInfo.isHardcoreModeEnabled();
    }

    @Override
    public WorldType getTerrainType() {
        return this.theWorldInfo.getTerrainType();
    }

    @Override
    public void setTerrainType(WorldType par1WorldType) {
    }

    /**
     * Returns true if commands are allowed on this World.
     */
    @Override
    public boolean areCommandsAllowed() {
        return this.theWorldInfo.areCommandsAllowed();
    }

    /**
     * Returns true if the World is initialized.
     */
    @Override
    public boolean isInitialized() {
        return this.theWorldInfo.isInitialized();
    }

    /**
     * Sets the initialization status of the World.
     */
    @Override
    public void setServerInitialized(boolean par1) {
    }

    /**
     * Gets the GameRules class Instance.
     */
    @Override
    public GameRules getGameRulesInstance() {
        return this.theWorldInfo.getGameRulesInstance();
    }
}
