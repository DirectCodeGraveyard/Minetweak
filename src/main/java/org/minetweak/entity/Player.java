package org.minetweak.entity;

import net.minecraft.entity.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.player.achievement.Achievement;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ban.BanEntry;
import net.minecraft.server.network.NetServerHandler;
import net.minecraft.src.DamageSource;
import net.minecraft.utils.enums.EnumGameType;
import org.minetweak.Minetweak;
import org.minetweak.command.CommandSender;
import org.minetweak.event.player.PlayerJoinEvent;
import org.minetweak.inventory.ContainerInventory;
import org.minetweak.inventory.EnderChest;
import org.minetweak.inventory.InventoryPlayer;
import org.minetweak.permissions.Permissions;
import org.minetweak.permissions.PlayerWhitelist;
import org.minetweak.permissions.ServerOps;
import org.minetweak.server.GameMode;
import org.minetweak.server.Server;
import org.minetweak.world.Location;
import org.minetweak.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the player, and allows you to
 * call many API methods that are buried deep in
 * vanilla code. It implements CommandSender, which allows
 * the player to send commands. It also retrieves the
 * EntityPlayerMP and NetServerHandler for the player
 * when a instance is created for him/her. This gives
 * complete control over the methods inside of both
 * of those classes.
 */
public class Player extends Entity implements CommandSender {

    private String username;
    private boolean isKickable;

    /**
     * Initialize a player
     *
     * @param username Player's username
     */
    public Player(String username) {
        super(MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(username));
        this.username = username;
    }

    /**
     * Initialize a player
     *
     * @param playerMP EntityPlayerMP Instance
     */
    public Player(EntityPlayerMP playerMP) {
        super(playerMP);
        this.username = playerMP.getEntityName();
    }

    /**
     * Register a player into Minetweak
     *
     * @param playerMP EntityPlayerMP Instance
     */
    public static boolean registerPlayer(EntityPlayerMP playerMP) {
        Player targetPlayerInstance = new Player(playerMP);
        String playerUsername = targetPlayerInstance.getName();
        if (Minetweak.isServerLockedDown()) {
            targetPlayerInstance.kickPlayer("This server is currently under lockdown.");
            return false;
        } else {
            if (Minetweak.getPlayers().containsKey(playerUsername)) {
                playerMP.playerNetServerHandler.kickPlayer("There was a problem connecting you to the server");
                return false;
            } else {
                if (PlayerWhitelist.isWhitelistEnabled() && !PlayerWhitelist.isPlayerWhitelisted(playerUsername)) {
                    targetPlayerInstance.kickPlayer("You are not whitelisted on this server!");
                    return false;
                }
                Minetweak.getPlayers().put(playerUsername, targetPlayerInstance);
                Minetweak.getEventBus().post(new PlayerJoinEvent(targetPlayerInstance));
            }
            return true;
        }
    }

    /**
     * Register a player into Minetweak
     *
     * @param playerUsername Player name we are registering
     */
    public static boolean registerPlayer(String playerUsername) {
        return registerPlayer(MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(playerUsername));
    }

    /**
     * Take the target player and unregister them
     *
     * @param playerUsername Player name we marking as offline
     */
    public static void unregisterPlayer(String playerUsername) {
        Minetweak.getPlayers().remove(playerUsername.toLowerCase());
    }

    /**
     * Retrieve the player's "display name", which is the username of the player.
     *
     * @return Player's display name
     */
    public String getDisplayName() {
        return username.toLowerCase();
    }

    /**
     * Kick the player from the server
     */
    public void kickPlayer() {
        kickPlayer("You have been kicked from the server.");
    }

    /**
     * Kick the player from the server, including a kick reason
     *
     * @param kickReason Reason why the player was kicked(will show up on their disconnected screen)
     */
    public void kickPlayer(String kickReason) {
        getNetServerHandler().kickPlayer(kickReason);
    }

    /**
     * Ban the player from the server
     */
    public void banPlayer() {
        banPlayer("You have been banned from the server.");
    }

    /**
     * Ban the player from the server, including a ban reason
     *
     * @param banReason Reason why the player was banned(will show up on their disconnected screen)
     */
    public void banPlayer(String banReason) {
        BanEntry banEntry = new BanEntry(username);
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().put(banEntry);
        getNetServerHandler().kickPlayer(banReason);
    }

    /**
     * Give permission to the player
     *
     * @param permissionNode Node of the permission that you want to give the player
     * @return True if the permission was successfully added, and that the permission did not already exist
     */
    protected boolean givePermission(String permissionNode) {
        return Permissions.addPermission(username, permissionNode);
    }

    /**
     * Get the player permissions
     *
     * @return String list of permissions
     */
    public ArrayList<String> getPlayerPermissions() {
        return Permissions.getPermissions(username);
    }

    /**
     * Check to see if this player has a specific permission node
     *
     * @param permissionNode Target permission node
     * @return True if the player has the permission
     */
    @Override
    public boolean hasPermission(String permissionNode) {
        return Permissions.hasPermission(username, permissionNode);
    }

    /**
     * Send the player a message
     *
     * @param message Target message towards the player
     */
    @Override
    public void sendMessage(String message) {
        /* Old code that doesn't work anymore. getPlayerMP().sendChatToPlayer(message);*/
        getPlayerMP().addChatMessage(message);
    }

    /**
     * Get the player's name, basically is just a method wrap to getDisplayName()
     *
     * @return The player's display name/username
     */
    @Override
    public String getName() {
        return getDisplayName();
    }

    /**
     * Check if its possible to kick this player.
     *
     * @return True, if the player can be kicked
     */
    @Override
    public boolean isKickable() {
        return isKickable;
    }

    /**
     * Check if this is a player, not a console.
     *
     * @return True if this sender is a player
     */
    @Override
    public boolean isPlayer() {
        return true;
    }

    /**
     * Get the item stack currently held by the player
     *
     * @return ItemStack player is currently holding
     */
    public ItemStack getItemInHand() {
        return getPlayerMP().inventory.getCurrentItem();
    }

    /**
     * Get the instance of EntityPlayerMP for this player
     *
     * @return EntityPlayerMP corresponding class
     */
    public EntityPlayerMP getPlayerMP() {
        return (EntityPlayerMP) entity;
    }

    /**
     * Get the NetServerHandler for this player
     *
     * @return NetServerHandler corresponding class
     */
    public NetServerHandler getNetServerHandler() {
        return getPlayerMP().playerNetServerHandler;
    }

    /**
     * Check to see if this player has operator powers
     *
     * @return True if the player is an op
     */
    public boolean isOperator() {
        return ServerOps.isPlayerOp(username);
    }

    /**
     * Op this player
     */
    public void opPlayer() {
        Server.opPlayer(username);
    }

    /**
     * Deop this player
     */
    public void deopPlayer() {
        Server.deopPlayer(username);
    }

    /**
     * Kill the player
     */
    public void killPlayer() {
        getPlayerMP().attackEntityFrom(DamageSource.outOfWorld, 10000);
    }

    /**
     * Check if the player is sleeping in a bed.
     *
     * @return True if the player is sleeping
     */
    public boolean isPlayerSleeping() {
        return getPlayerMP().isPlayerSleeping();
    }

    /**
     * Set the gamemode of the player.
     *
     * @param gameMode GameMode
     */
    public void setGameMode(GameMode gameMode) {
        getPlayerMP().setGameType(EnumGameType.getByID(gameMode.getID()));
    }

    /**
     * Set the speed on ground.
     *
     * @param speedFloat Speed parameter
     */
    public void setSpeedOnGround(float speedFloat) {
        getPlayerMP().setAIMoveSpeed(speedFloat);
    }

    /**
     * Retrieve the speed on ground.
     *
     * @return Speed on ground
     */
    public float getSpeedOnGround() {
        return getPlayerMP().getAIMoveSpeed();
    }

    /**
     * Retrieve the player's health.
     *
     * @return Player health
     */
    public float getPlayerHealth() {
        return getPlayerMP().prevHealth;
    }

    /**
     * Set the player's health
     *
     * @param health Health level for set
     */
    public void setPlayerHealth(float health) {
        getPlayerMP().setEntityHealth(health);
        getPlayerMP().setPlayerHealthUpdated();
    }

    /**
     * Retrieve the player's hunger level.
     *
     * @return Hunger bar level
     */
    public int getPlayerHunger() {
        return getPlayerMP().getFoodStats().getFoodLevel();
    }

    /**
     * Set the player's hunger level.
     *
     * @param level Level to set hunger at
     */
    public void setPlayerHunger(int level) {
        getPlayerMP().getFoodStats().setFoodLevel(level);
    }

    /**
     * Give the player a potion effect. Uses the vanilla class: PotionEffect to process effects.
     * Might be moved to a separate class like PotionEffect, but with documentation, unlike Vanilla's.
     *
     * @param potionEffect the potion effect
     */
    public void addPotionEffect(PotionEffect potionEffect) {
        getPlayerMP().addPotionEffect(potionEffect);
    }

    /**
     * Award the player an achievement.
     *
     * @param awardedAchievement Achievement awarded, retrieve one from AchievementList
     */
    public void awardAchievement(Achievement awardedAchievement) {
        getPlayerMP().triggerAchievement(awardedAchievement);
    }

    /**
     * Gets the inventory of the Player
     *
     * @return inventory of player
     */
    public InventoryPlayer getInventory() {
        return new InventoryPlayer(getPlayerMP().inventory);
    }

    /**
     * Gets the inventory of the Player's ender chest
     *
     * @return player's ender chest
     */
    public EnderChest getEnderChest() {
        return new EnderChest(this);
    }

    @Override
    public void sendMessage(String[] messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    @Override
    public void sendMessage(List<String> messages) {
        sendMessage(messages.toArray(new String[messages.size()]));
    }

    /**
     * Is the Player flying?
     *
     * @return True if the player is flying
     */
    public boolean isFlying() {
        return getPlayerMP().capabilities.isFlying;
    }

    /**
     * Can the Player fly?
     *
     * @return True if the player can fly
     */
    public boolean canFly() {
        return getPlayerMP().capabilities.allowFlying;
    }

    /**
     * Is the Player in creative mode?
     *
     * @return True if the player is in creative mode
     */
    public boolean isInCreativeMode() {
        return getPlayerMP().getGameType().isCreative();
    }

    /**
     * Is the Player in adventure mode?
     *
     * @return True if the player is in adventure mode
     */
    public boolean isInAdventureMode() {
        return getPlayerMP().getGameType().isAdventure();
    }

    /**
     * Is the Player in Survival mode?
     *
     * @return True if the player is in survival mode
     */
    public boolean isInSurvivalMode() {
        return !(getPlayerMP().getGameType().isCreative()) && !(getPlayerMP().getGameType().isAdventure());
    }

    /**
     * Teleport the player to the position
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public void teleportToPosition(double x, double y, double z) {
        getPlayerMP().mountEntity(null);
        getPlayerMP().setPositionAndUpdate(x, y, z);
    }

    /**
     * Teleports the player to the Given Location
     *
     * @param location the location instance
     */
    public void teleportToPosition(Location location) {
        teleportToPosition(location.getPosX(), location.getPosY(), location.getPosZ());
    }

    /**
     * Gets the X Coordinate of player Position
     *
     * @return X Coordinate
     */
    public double getX() {
        return getPlayerMP().posX;
    }

    /**
     * Gets the Y Coordinate of player Position
     *
     * @return Y Coordinate
     */
    public double getY() {
        return getPlayerMP().posY;
    }

    /**
     * Gets the Z Coordinate of player Position
     *
     * @return Z Coordinate
     */
    public double getZ() {
        return getPlayerMP().posZ;
    }

    /**
     * Gets the player's Gamemode
     *
     * @return gamemode
     */
    public GameMode getGameMode() {
        return GameMode.getByID(getPlayerMP().getGameType().getID());
    }

    /**
     * Gets the Current World the player is in
     *
     * @return current world
     */
    public World getCurrentWorld() {
        return getPlayerMP().worldObj.getWorld();
    }

    /**
     * Shows an ContainerInventory to a Player
     *
     * @param inventory inventory to show
     */
    public void showInventory(ContainerInventory inventory) {
        getPlayerMP().displayGUIChest(inventory.getMCInventory());
    }

    /**
     * Gets the Players Current Location
     *
     * @return location
     */
    public Location getLocation() {
        return new Location(getX(), getY(), getZ(), getCurrentWorld());
    }

    /**
     * Closes the Current GUI
     */
    public void closeGUI() {
        getPlayerMP().closeContainer();
    }

    @Override
    public String toString() {
        return username;
    }

    /**
     * Toggles the Kickable status
     *
     * @param kickable is kickable
     */
    public boolean setKickable(boolean kickable) {
        return this.isKickable = kickable;
    }
}