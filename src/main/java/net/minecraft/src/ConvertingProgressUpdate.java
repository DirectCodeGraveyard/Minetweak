package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class ConvertingProgressUpdate implements IProgressUpdate {
    private long field_96245_b;

    /**
     * Reference to the MinecraftServer object.
     */
    final MinecraftServer mcServer;

    public ConvertingProgressUpdate(MinecraftServer par1MinecraftServer) {
        this.mcServer = par1MinecraftServer;
        this.field_96245_b = MinecraftServer.getCurrentMillis();
    }

    /**
     * Shows the 'Saving level' string.
     */
    @Override
    public void displaySavingString(String par1Str) {
    }

    /**
     * Updates the progress bar on the loading screen to the specified amount. Args: loadProgress
     */
    @Override
    public void setLoadingProgress(int par1) {
        if (MinecraftServer.getCurrentMillis() - this.field_96245_b >= 1000L) {
            this.field_96245_b = MinecraftServer.getCurrentMillis();
            this.mcServer.logInfo("Converting... " + par1 + "%");
        }
    }

    /**
     * Displays a string on the loading screen supposed to indicate what is being done currently.
     */
    @Override
    public void displayLoadingString(String par1Str) {
    }
}
