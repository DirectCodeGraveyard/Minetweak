package org.minetweak.plugins;

/**
 * The base of all Plugin
 */

public abstract class Plugin {
    public abstract String getName();
    public abstract String getVersion();

    public void onEnable() {

    }

    public void onDisable() {

    }
}
