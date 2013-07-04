package org.minetweak.permissions;

import java.util.ArrayList;

public class PermissionsFile {
    public ArrayList<entry> entries = new ArrayList<entry>();
    class entry {
        public String player;
        public String permission;
    }
}
