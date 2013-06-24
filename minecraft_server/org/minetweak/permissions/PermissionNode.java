package org.minetweak.permissions;

import org.minetweak.plugins.Plugin;

public class PermissionNode {

    private Plugin permissionPlugin;
    private String permissionNode;

    public PermissionNode(String permissionNode) {
        this.permissionNode = permissionNode;
    }

    public PermissionNode(String permissionNode, Plugin permissionPlugin) {
        this.permissionNode = permissionNode;
        this.permissionPlugin = permissionPlugin;
    }

    public Plugin getPlugin() {
        return permissionPlugin;
    }

    public String getPermissionNode() {
        return permissionNode;
    }

}
