package org.minetweak;

/**
 * Main Class for Minetweak. Used to load Dependencies in the future.
 */
public class Start {

    private static Start instance = new Start();

    private boolean quiet;

    /**
     * Starts Minetweak in Console
     *
     * @param args Arguments to Pass
     */
    public static void main(String[] args) {
        instance.start(args);
    }

    public void start(String[] args) {
        start(args, true);
    }

    public void start(String[] args, boolean sayVersion) {
        if (sayVersion) {
            Minetweak.info("Starting Minetweak v" + Minetweak.getAPIVersion() + " implementing Minecraft v" + Minetweak.getMinecraftVersion());
        }
        Minetweak.main(args);
    }

    /**
     * Tells Minetweak to be quiet. Don't log anything!
     *
     * @param quiet is quiet
     */
    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    /**
     * Is Minetweak Quiet
     *
     * @return is quiet
     */
    public boolean isQuiet() {
        return quiet;
    }

    /**
     * Gets the Default Instance of Starter
     *
     * @return Default Start
     */
    public static Start getInstance() {
        return instance;
    }
}
