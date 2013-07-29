package net.minecraft.server;

import org.minetweak.config.MinetweakConfig;
import org.minetweak.permissions.PlayerWhitelist;
import org.minetweak.permissions.ServerOps;

public class DedicatedPlayerList extends ServerConfigurationManager {

    public DedicatedPlayerList(DedicatedServer par1DedicatedServer) {
        super(par1DedicatedServer);
        this.viewDistance = par1DedicatedServer.getIntProperty("view-distance", 10);
        this.maxPlayers = par1DedicatedServer.getIntProperty("max-players", 20);
        this.getBannedPlayers().setListActive(true);
        this.getBannedIPs().setListActive(true);
        this.getBannedPlayers().loadBanList();
        this.getBannedPlayers().saveToFileWithHeader();
        this.getBannedIPs().loadBanList();
        this.getBannedIPs().saveToFileWithHeader();
        this.loadOpsList();
        this.saveOpsList();
    }

    @Override
    public void setWhiteListEnabled(boolean par1) {
        super.setWhiteListEnabled(par1);
        MinetweakConfig.set("server.whitelist-enabled", String.valueOf(par1));
    }

    /**
     * This adds a username to the ops list, then saves the op list
     */
    @Override
    public void addOp(String par1Str) {
        super.addOp(par1Str);
        this.saveOpsList();
    }

    /**
     * This removes a username from the ops list, then saves the op list
     */
    @Override
    public void removeOp(String par1Str) {
        super.removeOp(par1Str);
        this.saveOpsList();
    }

    /**
     * Remove the specified player from the whitelist.
     */
    @Override
    public void removeFromWhitelist(String par1Str) {
        super.removeFromWhitelist(par1Str);
        this.saveWhiteList();
    }

    /**
     * Add the specified player to the white list.
     */
    @Override
    public void addToWhiteList(String par1Str) {
        super.addToWhiteList(par1Str);
        this.saveWhiteList();
    }

    /**
     * Either does nothing, or calls readWhiteList.
     */
    @Override
    public void loadWhiteList() {
        this.readWhiteList();
    }

    private void loadOpsList() {
        ServerOps.load();
    }

    private void saveOpsList() {
        ServerOps.save();
    }

    private void readWhiteList() {

    }

    private void saveWhiteList() {

    }

    /**
     * Determine if the player is allowed to connect based on current server settings.
     */
    @Override
    public boolean isAllowedToLogin(String par1Str) {
        par1Str = par1Str.trim().toLowerCase();
        return !MinetweakConfig.getBoolean("server.whitelist-enabled") || this.areCommandsAllowed(par1Str) || PlayerWhitelist.isPlayerWhitelisted(par1Str);
    }

    public DedicatedServer getDedicatedServerInstance() {
        return (DedicatedServer) super.getServerInstance();
    }

    @Override
    public MinecraftServer getServerInstance() {
        return this.getDedicatedServerInstance();
    }
}
