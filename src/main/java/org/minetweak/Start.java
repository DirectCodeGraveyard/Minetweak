package org.minetweak;

/**
 * Main Class for Minetweak. Used to load Dependencies in the future.
 */
public class Start {
    public static void main(String[] args) {
        Minetweak.info("Starting Minetweak v" + Minetweak.getAPIVersion() + " implementing Minecraft v" + Minetweak.getMinecraftVersion());
        Minetweak.main(args);
    }
}
