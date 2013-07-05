package org.minetweak.permissions;

import org.minetweak.plugins.PluginLoader;

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
            PermissionsLoader.save();
            return true;
        } else {
            perms.add(permission);
            permissions.put(user, perms);
            PermissionsLoader.save();
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
        PermissionsLoader.save();
        return true;
    }

    public static boolean hasPermission(String user, String permission) {
        ArrayList<String> perms = permissions.get(user);
        if (perms==null) {
            return false;
        }
        for (String perm : perms) {
            if (permission.equals(perm)) {
                return true;
            } else if (perm.equals("*")) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> getPermissions(String user) {
        return permissions.get(user);
    }

    public static HashMap<String, ArrayList<String>> getPermissions() {
        return permissions;
    }
}
