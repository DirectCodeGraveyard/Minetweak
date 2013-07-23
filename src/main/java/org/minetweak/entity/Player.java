package org.minetweak.entity;

import net.minecraft.entity.EntityPlayerMP;
import net.minecraft.utils.enums.EnumGameType;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.NetServerHandler;
import net.minecraft.player.achievement.Achievement;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ban.BanEntry;
import net.minecraft.src.*;
import net.minecraft.utils.chat.ChatMessageComponent;
import org.minetweak.command.CommandSender;
import org.minetweak.inventory.InventoryPlayer;
import org.minetweak.permissions.Permissions;

import java.util.ArrayList;

/**
 * This class represents the player, and allows you to
 * call many API methods that are buried deep in
 * vanilla code. It extends CommandSender, which allows
 * the player to send commands. It also retrieves the
 * EntityPlayerMP and NetServerHandler for the player
 * when a instance is created for him/her. This gives
 * complete control over the methods inside of both
 * of those classes.
 */
public class Player extends Entity implements CommandSender {

    private String playerDisplayName;

    /**
     * Initialize a player
     * @param playerDisplayName Player's username
     */
    public Player(String playerDisplayName) {
        super(MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(playerDisplayName));
        this.playerDisplayName = playerDisplayName;
    }

    /**
     * Initialize a player
     * @param playerMP EntityPlayerMP Instance
     */
    public Player(EntityPlayerMP playerMP) {
        super(playerMP);
        this.playerDisplayName = playerMP.getEntityName();
    }

    /**
     * Retrieve the player's "display name", which is the username of the player.
     * @return Player's display name
     */
    public String getDisplayName() {
        return playerDisplayName.toLowerCase();
    }

    /**
     * Kick the player from the server
     */
    public void kickPlayer() {
        kickPlayer("You have been kicked from the server.");
    }

    /**
     * Kick the player from the server, including a kick reason
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
     * @param banReason Reason why the player was banned(will show up on their disconnected screen)
     */
    public void banPlayer(String banReason) {
        BanEntry banEntry = new BanEntry(playerDisplayName);
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().put(banEntry);
        getNetServerHandler().kickPlayer(banReason);
    }

    /**
     * Give permission to the player
     * @param permissionNode Node of the permission that you want to give the player
     * @return True if the permission was successfully added, and that the permission did not already exist
     */
    protected boolean givePermission(String permissionNode) {
        return Permissions.addPermission(playerDisplayName, permissionNode);
    }

    /**
     * Get the player permissions
     * @return String list of permissions
     */
    public ArrayList<String> getPlayerPermissions() {
        return Permissions.getPermissions(playerDisplayName);
    }

    /**
     * Check to see if this player has a specific permission node
     * @param permissionNode Target permission node
     * @return True if the player has the permission
     */
    public boolean hasPermission(String permissionNode) {
        return Permissions.hasPermission(playerDisplayName, permissionNode);
    }

    /**
     * Send the player a message
     * @param message Target message towards the player
     */
    @Override
    public void sendMessage(String message) {
        /* Old code that doesn't work anymore. getPlayerMP().sendChatToPlayer(message);*/
        getPlayerMP().sendChatToPlayer(new ChatMessageComponent().func_111072_b(message));
    }

    /**
     * Get the player's name, basically is just a method wrap to getDisplayName()
     * @return The player's display name/username
     */
    @Override
    public String getName() {
        return getDisplayName();
    }

    /**
     * Can we kick the player? We sure can.
     * @return True, if the player can be kickable
     */
    @Override
    public boolean isKickable() {
        return true;
    }

    public ItemStack getItemInHand() {
        return getPlayerMP().inventory.getCurrentItem();
    }

    /**
     * Get the instance of EntityPlayerMP for this player
     * @return EntityPlayerMP corresponding class
     */
    public EntityPlayerMP getPlayerMP() {
        return (EntityPlayerMP) entity;
    }

    /**
     * Get the NetServerHandler for this player
     * @return NetServerHandler corresponding class
     */
    public NetServerHandler getNetServerHandler() {
        return getPlayerMP().playerNetServerHandler;
    }

    /**
     * Check to see if this player has operator powers
     * @return True if the player is an op
     */
    public boolean isOperator() {
        return MinecraftServer.getServer().getConfigurationManager().getOps().contains(playerDisplayName.toLowerCase());
    }

    /**
     * Op the player
     */
    public void opPlayer() {
        MinecraftServer.getServer().getConfigurationManager().addOp(playerDisplayName.toLowerCase());
    }

    /**
     * Deop the player
     */
    public void deopPlayer() {
        MinecraftServer.getServer().getConfigurationManager().removeOp(playerDisplayName.toLowerCase());
    }

    /**
     * Kill the player
     */
    public void killPlayer() {
        getPlayerMP().attackEntityFrom(DamageSource.outOfWorld, 10000);
    }

    /**
     * Check if the player is sleeping in a bed.
     * @return True if the player is sleeping
     */
    public boolean isPlayerSleeping() {
        return getNetServerHandler().playerEntity.isPlayerSleeping();
    }

    /**
     * Set the gamemode of the player.
     * @param gameType Gametype from EnumGameType
     */
    public void setGameMode(EnumGameType gameType) {
        getPlayerMP().setGameType(gameType);
    }

    /**
     * Set the speed on ground.
     * @param speedFloat Speed parameter
     */
    public void setSpeedOnGround(float speedFloat) {
        getPlayerMP().setAIMoveSpeed(speedFloat);
    }

    /**
     * Retrieve the speed on ground.
     * @return Speed on ground
     */
    public float getSpeedOnGround() {
        return getPlayerMP().getAIMoveSpeed();
    }

    /**
     * Retrieve the player's health.
     * @return Player health
     */
    public float getPlayerHealth() {
        return getPlayerMP().prevHealth;
    }

    /**
     * Set the player's health
     * @param health Health level for set
     */
    public void setPlayerHealth(float health) {
        getPlayerMP().setEntityHealth(health);
        getPlayerMP().setPlayerHealthUpdated();
    }

    /**
     * Retrieve the player's hunger level.
     * @return Hunger bar level
     */
    public int getPlayerHunger() {
        return getPlayerMP().getFoodStats().getFoodLevel();
    }

    /**
     * Set the player's hunger level.
     * @param level Level to set hunger at
     */
    public void setPlayerHunger(int level) {
        getPlayerMP().getFoodStats().setFoodLevel(level);
    }

    /**
     * Give the player a potion effect. Uses the vanilla class: PotionEffect to process effects.
     * Might be moved to a separate class like PotionEffect, but with documentation, unlike Vanilla's.
     * @param potionEffect the potion effect
     */
    public void addPotionEffect(PotionEffect potionEffect) {
        getPlayerMP().addPotionEffect(potionEffect);
    }

    /**
     * Award the player an achievement, they might deserve it.
     * @param awardedAchievement Achievement awarded, retrieve one from AchievementList
     */
    public void awardAchievement(Achievement awardedAchievement) {
        getPlayerMP().triggerAchievement(awardedAchievement);
    }

    /**
     * Gets the inventory of the Player
     * @return inventory of player
     */
    public InventoryPlayer getInventory() {
        return new InventoryPlayer(getPlayerMP().inventory);
    }

    /**
     * Gets the inventory of the Player's ender chest
     * @return player's ender chest
     */
    public InventoryEnderChest getEnderChest() {
        return getPlayerMP().getInventoryEnderChest();
    }

    @Override
    public void sendMessage(String[] messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    /**
     * Is the Player flying?
     * @return True if the player is flying
     */
    public boolean isFlying() {
        return getPlayerMP().capabilities.isFlying;
    }

    /**
     * Can the Player fly?
     * @return True if the player can fly
     */
    public boolean canFly() {
        return getPlayerMP().capabilities.allowFlying;
    }

    /**
     * Is the Player in creative mode?
     * @return True if the player is in creative mode
     */
    public boolean isInCreativeMode() {
        return getPlayerMP().capabilities.isCreativeMode;
    }
}
