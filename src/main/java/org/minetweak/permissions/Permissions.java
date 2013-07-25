package org.minetweak.permissions;

import java.util.ArrayList;
import java.util.HashMap;

public class Permissions {
    protected static HashMap<String, ArrayList<String>> permissions = new HashMap<String, ArrayList<String>>();

    /**
     * Adds a permission
     *
     * @param user username for player
     * @param permission permissions node
     * @return if permission was added
     */
    public static boolean addPermission(String user, String permission) {
        user = user.toLowerCase();
        ArrayList<String> perms = new ArrayList<String>();
        if (permissions.containsKey(user)) {
            if (perms.contains(permission)) {
                return false;
            }
            perms.addAll(getPermissions(user));
        }
        perms.add(permission);
        permissions.put(user, perms);
        PermissionsLoader.save();
        return true;
    }

    /**
     * Removes a permission
     *
     * @param user       username of player
     * @param permission permission node
     * @return if permission was removed
     */
    public static boolean removePermission(String user, String permission) {
        user = user.toLowerCase();
        ArrayList<String> userPerms = getPermissions(user);
        if (userPerms == null) {
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
     *
     * @param user       username of player
     * @param permission permission node
     * @return if the player has permission
     */
    public static boolean hasPermission(String user, String permission) {
        user = user.toLowerCase();
        ArrayList<String> perms = getPermissions(user);
        if (perms == null) {
            return false;
        } else if (perms.contains("*")) {
            return true;
        } else if (ServerOps.isPlayerOp(user)) {
            return true;
        } else if (perms.contains(permission)) {
            return true;
        }
        return false;
    }

    /**
     * Gets all permissions for player
     *
     * @param user username of player
     * @return ArrayList of permissions
     */
    public static ArrayList<String> getPermissions(String user) {
        user = user.toLowerCase();
        return permissions.get(user);
    }

    /**
     * Gets all player permissions
     *
     * @return permissions of everyone
     */
    public static HashMap<String, ArrayList<String>> getPermissions() {
        return permissions;
    }

    /**
     * Set Permissions Map
     */
    protected static void setPermissions(HashMap<String, ArrayList<String>> permissions) {
        Permissions.permissions = permissions;
    }
}
