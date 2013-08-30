package org.minetweak.thread;

import com.google.common.eventbus.Subscribe;
import net.minecraft.server.MinecraftServer;
import org.minetweak.Minetweak;
import org.minetweak.entity.player.PlayerTracker;
import org.minetweak.event.server.ServerFinishedStartupEvent;
import org.minetweak.permissions.PermissionsLoader;
import org.minetweak.permissions.PlayerWhitelist;
import org.minetweak.permissions.ServerOps;

import java.util.ArrayList;

public class ManagementThread extends Thread {
    private ArrayList<Runnable> runnables = new ArrayList<Runnable>();
    private static ManagementThread instance = new ManagementThread();

    @Override
    public void run() {
        while (true) {
            loadBans();
            loadPerms();
            loadPlayers();
            loadWhitelist();
            loadOps();
            ArrayList<Runnable> temp = new ArrayList<Runnable>(runnables);
            for (Runnable runnable : temp) {
                runnable.run();
                runnables.remove(runnable);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Minetweak.getLogger().logWarningException("Stopping Management Thread", e);
                break;
            }
        }
    }

    /**
     * This loads the bans in the thread, allowing for not-in game changes
     */
    public void loadBans() {
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().loadBanList();
        MinecraftServer.getServer().getConfigurationManager().getBannedIPs().loadBanList();
    }

    /**
     * This loads permissions for the server
     */
    public void loadPerms() {
        PermissionsLoader.load();
    }

    /**
     * Loads the whitelisted players
     */
    public void loadWhitelist() {
        PlayerWhitelist.load();
    }

    /**
     * Loads the player list. Can by used for example, if a user wants a reset
     */
    public void loadPlayers() {
        PlayerTracker.getInstance().loadList();
    }

    /**
     * Loads the Server Ops List
     */
    public void loadOps() {
        ServerOps.load();
    }

    /**
     * Starts the Management Thread on Server Finished Startup
     *
     * @param event event of the subscription
     */
    @Subscribe
    public void serverReadyCallback(ServerFinishedStartupEvent event) {
        Minetweak.info("Starting Management Thread");
        this.setDaemon(true);
        this.start();
    }

    /**
     * Adds a Runnable to the queue of things to run
     *
     * @param runnable the runnable instance
     */
    public void runInThread(Runnable runnable) {
        runnables.add(runnable);
    }

    /**
     * Gets the ManagementThread main instance
     *
     * @return instance
     */
    public static ManagementThread getInstance() {
        return instance;
    }
}