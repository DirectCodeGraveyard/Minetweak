package org.minetweak.entity;

import org.minetweak.Server;
import org.minetweak.command.CommandSender;
import org.minetweak.permissions.PermissionNode;

import java.util.ArrayList;

public class Player implements CommandSender {

    private String playerDisplayName;
    private ArrayList<PermissionNode> playerPermissions = new ArrayList<PermissionNode>();

    public Player(String playerDisplayName) {
        this.playerDisplayName = playerDisplayName;
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
     * @return True if the player was kicked successfully
     */
    public boolean kickPlayer() {
        return Server.kickPlayer(playerDisplayName);
    }

    /**
     * Ban the player from the server
     * @return True if the player was banned successfully
     */
    public boolean banPlayer() {
        return Server.banPlayer(playerDisplayName);
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

    }

    @Override
    public String getName() {
        return getDisplayName();
    }
}
