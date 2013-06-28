package org.minetweak.permissions;

import org.minetweak.plugins.MinetweakPlugin;

public class PermissionNode {

    private MinetweakPlugin permissionPlugin;
    private String permissionNode;

    public PermissionNode(String permissionNode) {
        this.permissionNode = permissionNode;
    }

    public PermissionNode(String permissionNode, MinetweakPlugin permissionPlugin) {
        this.permissionNode = permissionNode;
        this.permissionPlugin = permissionPlugin;
    }

    public MinetweakPlugin getPlugin() {
        return permissionPlugin;
    }

    public String getPermissionNode() {
        return permissionNode;
    }

}
