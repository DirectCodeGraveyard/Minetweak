package org.minetweak.permissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Permissions {
    protected static HashMap<String, ArrayList<String>> permissions = new HashMap<String, ArrayList<String>>();

    public static boolean doesUserHavePermission(String user, String node) {
        return permissions.containsKey(user) && permissions.get(user).contains(node);
    }

    public static boolean addPermission(String user, String permission) {
        ArrayList<String> perms = new ArrayList<String>();
        if (permissions.containsKey(user)) {
            perms.addAll(permissions.get(user));
            if (perms.contains(permission)) {
                return false;
            }
            perms.add(permission);
            permissions.put(user, perms);
            return true;
        } else {
            perms.add(permission);
            permissions.put(user, perms);
            return true;
        }
    }

    public static boolean removePermission(String user, String permission) {
        ArrayList<String> userPerms = permissions.get(user);
        if (userPerms==null) {
            return false;
        } else if (!userPerms.contains(permission)) {
            return false;
        }
        userPerms.remove(permission);
        permissions.put(user, userPerms);
        return true;
    }

    public static boolean hasPermission(String user, String permission) {
        ArrayList<String> perms = permissions.get(user);
        if (perms==null) {
            return false;
        }
        if (perms.contains("*")) {
            return true;
        } else if (perms.contains(permission)) {
            return true;
        }
        return false;
    }

    public static ArrayList<String> getPlayerPermissions(String user) {
        return permissions.get(user);
    }
}
