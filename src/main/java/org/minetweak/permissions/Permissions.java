package org.minetweak.permissions;

import java.util.ArrayList;
import java.util.HashMap;

public class Permissions {
    protected static HashMap<String, ArrayList<String>> permissions = new HashMap<String, ArrayList<String>>();

    public static boolean doesUserHavePermission(String user, String node) {
        return permissions.containsKey(user) && permissions.get(user).contains(node);
    }

    public static void addPermission(String user, String permission) {
        if (permissions.containsKey(user)) {
            ArrayList<String> perms = new ArrayList<String>();
            perms.add(permission);
            permissions.put(user, perms);
        }
    }
}
