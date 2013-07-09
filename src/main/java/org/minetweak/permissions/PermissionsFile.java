package org.minetweak.permissions;

import java.util.ArrayList;

/**
 * Contains Entries for Permissions
 * For use with Gson
 */
public class PermissionsFile {
    public ArrayList<entry> entries = new ArrayList<entry>();
    class entry {
        public String player;
        public String permission;
    }
}
