package org.minetweak.permissions;

import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.HashMap;

public class Permissions {
    protected static HashMap<String, ArrayList<String>> permissions = new HashMap<String, ArrayList<String>>();

    /**
     * Adds a permission
     * @param user username for player
     * @param permission permissions node
     * @return if permission was added
     */
    public static boolean addPermission(String user, String permission) {
        ArrayList<String> perms = new ArrayList<String>();
        if (permissions.containsKey(user)) {
            perms.addAll(permissions.get(user));
            if (perms.contains(permission)) {
                return false;
            }
            perms.add(permission);
            permissions.put(user, perms);
            PermissionsLoader.save();
            return true;
        } else {
            perms.add(permission);
            permissions.put(user, perms);
            PermissionsLoader.save();
            return true;
        }
    }

    /**
     * Removes a permission
     * @param user username of player
     * @param permission permission node
     * @return if permission was removed
     */
    public static boolean removePermission(String user, String permission) {
        ArrayList<String> userPerms = permissions.get(user);
        if (userPerms==null) {
            return false;
        } else if (!userPerms.contains(permission)) {
            return false;
        }
        userPerms.remove(permission);
        permissions.put(user, userPerms);
        PermissionsLoader.save();
        return true;
    }

    /**
     * Checks for permission
     * @param user username of player
     * @param permission permission node
     * @return if the player has permission
     */
    public static boolean hasPermission(String user, String permission) {
        ArrayList<String> perms = permissions.get(user);
        return perms != null && (perms.contains("*") || perms.contains(permission)) || MinecraftServer.getServer().getConfigurationManager().getOps().contains(user);
    }

    /**
     * Gets all permissions for player
     * @param user username of player
     * @return ArrayList of permissions
     */
    public static ArrayList<String> getPermissions(String user) {
        return permissions.get(user);
    }

    /**
     * Gets all player permissions
     * @return permissions of everyone
     */
    public static HashMap<String, ArrayList<String>> getPermissions() {
        return permissions;
    }
}
