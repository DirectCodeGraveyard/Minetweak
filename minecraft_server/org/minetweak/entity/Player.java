package org.minetweak.entity;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.BanEntry;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NetServerHandler;
import org.minetweak.command.CommandSender;
import org.minetweak.permissions.PermissionNode;

import java.util.ArrayList;

public class Player implements CommandSender {

    private String playerDisplayName;
    private ArrayList<PermissionNode> playerPermissions = new ArrayList<PermissionNode>();

    private EntityPlayerMP entityPlayerMP;

    public Player(String playerDisplayName) {
        this.playerDisplayName = playerDisplayName;
        entityPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerEntity(playerDisplayName);
    }

    /**
     * Retrieve the player's "display name", which for now is the username of the player, soon might be a customisable name.
     * @return Player's display name
     */
    public String getDisplayName() {
        return playerDisplayName;
    }

    /**
     * Kick the player from the server
     */
    public void kickPlayer() {
        kickPlayer("You have been kicked from the server.");
    }

    public void kickPlayer(String kickReason) {
        getNetServerHandler().kickPlayer(kickReason);
    }

    /**
     * Ban the player from the server
     */
    public void banPlayer() {
        banPlayer("You have been banned from the server.");
    }

    public void banPlayer(String banReason) {
        BanEntry banEntry = new BanEntry(playerDisplayName);
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().put(banEntry);
        getNetServerHandler().kickPlayer(banReason);
    }

    /**
     * Give permission to the player
     * @param permissionNode Node of the permission that you want to give the player
     * @return
     */
    protected boolean givePermission(String permissionNode) {
        if (!playerPermissions.contains(permissionNode)) {
            playerPermissions.add(new PermissionNode(permissionNode));
            return true;
        }
        return false;
    }

    public Object[] getPlayerPermissions() {
        return playerPermissions.toArray();
    }

    public boolean hasPermission(String permissionNode) {
        for (PermissionNode p : playerPermissions) {
            if (p.getPermissionNode() == permissionNode) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void sendMessage(String message) {
        getPlayerMP().sendChatToPlayer(message);
    }

    @Override
    public String getName() {
        return getDisplayName();
    }

    @Override
    public boolean isKickable() {
        return true;
    }

    public EntityPlayerMP getPlayerMP() {
        return entityPlayerMP;
    }

    public NetServerHandler getNetServerHandler() {
        return entityPlayerMP.playerNetServerHandler;
    }

    public boolean isOperator() {
        return MinecraftServer.getServer().getConfigurationManager().getOps().contains(playerDisplayName);
    }

}
