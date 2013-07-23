package net.minecraft.server;

import org.minetweak.config.MinetweakConfig;
import org.minetweak.permissions.PlayerWhitelist;

import java.io.*;

public class DedicatedPlayerList extends ServerConfigurationManager {
    private File opsList;
    private File whiteList;

    public DedicatedPlayerList(DedicatedServer par1DedicatedServer) {
        super(par1DedicatedServer);
        this.opsList = par1DedicatedServer.getFile("ops.txt");
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

    public void setWhiteListEnabled(boolean par1) {
        super.setWhiteListEnabled(par1);
        MinetweakConfig.set("server.whitelist-enabled", "" + par1);
    }

    /**
     * This adds a username to the ops list, then saves the op list
     */
    public void addOp(String par1Str) {
        super.addOp(par1Str);
        this.saveOpsList();
    }

    /**
     * This removes a username from the ops list, then saves the op list
     */
    public void removeOp(String par1Str) {
        super.removeOp(par1Str);
        this.saveOpsList();
    }

    /**
     * Remove the specified player from the whitelist.
     */
    public void removeFromWhitelist(String par1Str) {
        super.removeFromWhitelist(par1Str);
        this.saveWhiteList();
    }

    /**
     * Add the specified player to the white list.
     */
    public void addToWhiteList(String par1Str) {
        super.addToWhiteList(par1Str);
        this.saveWhiteList();
    }

    /**
     * Either does nothing, or calls readWhiteList.
     */
    public void loadWhiteList() {
        this.readWhiteList();
    }

    private void loadOpsList() {
        try {
            this.getOps().clear();
            BufferedReader var1 = new BufferedReader(new FileReader(this.opsList));
            String var2;

            while ((var2 = var1.readLine()) != null) {
                this.getOps().add(var2.trim().toLowerCase());
            }

            var1.close();
        } catch (Exception var3) {
            this.getDedicatedServerInstance().getLogAgent().logWarning("Failed to load operators list: " + var3);
        }
    }

    private void saveOpsList() {
        try {
            PrintWriter var1 = new PrintWriter(new FileWriter(this.opsList, false));

            for (Object o : this.getOps()) {
                String var3 = (String) o;
                var1.println(var3);
            }

            var1.close();
        } catch (Exception var4) {
            this.getDedicatedServerInstance().getLogAgent().logWarning("Failed to save operators list: " + var4);
        }
    }

    private void readWhiteList() {

    }

    private void saveWhiteList() {

    }

    /**
     * Determine if the player is allowed to connect based on current server settings.
     */
    public boolean isAllowedToLogin(String par1Str) {
        par1Str = par1Str.trim().toLowerCase();
        return !MinetweakConfig.getBoolean("server.whitelist-enabled") || this.areCommandsAllowed(par1Str) || PlayerWhitelist.isPlayerWhitelisted(par1Str);
    }

    public DedicatedServer getDedicatedServerInstance() {
        return (DedicatedServer) super.getServerInstance();
    }

    public MinecraftServer getServerInstance() {
        return this.getDedicatedServerInstance();
    }
}
