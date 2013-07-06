package org.minetweak.permissions;

import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.HashMap;

public class Permissions {
    protected static HashMap<String, ArrayList<String>> permissions = new HashMap<String, ArrayList<String>>();

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
        return perms != null && (perms.contains("*") || perms.contains(permission)) || MinecraftServer.getServer().getConfigurationManager().getOps().contains(user);
    }

    public static ArrayList<String> getPermissions(String user) {
        return permissions.get(user);
    }

    public static HashMap<String, ArrayList<String>> getPermissions() {
        return permissions;
    }
}
