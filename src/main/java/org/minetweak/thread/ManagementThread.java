package org.minetweak.thread;

import com.google.common.eventbus.Subscribe;
import net.minecraft.server.MinecraftServer;
import org.minetweak.Minetweak;
import org.minetweak.event.server.ServerFinishedStartupEvent;
import org.minetweak.permissions.PermissionsLoader;

import java.util.ArrayList;

public class ManagementThread extends Thread {
    private ArrayList<Runnable> runnables = new ArrayList<Runnable>();
    private static ManagementThread instance;
    @Override
    public void run() {
        while (true) {
            loadBans();
            loadPerms();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Minetweak.info("Stopping Management Thread");
                break;
            }
            for (Runnable runnable : runnables) {
                runnable.run();
                runnables.remove(runnable);
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
        PermissionsLoader.save();
    }

    @Subscribe
    public void serverReadyCallback(ServerFinishedStartupEvent event) {
        Minetweak.info("Starting Management Thread");
        this.start();
    }

    public void runInThread(Runnable runnable) {
        runnables.add(runnable);
    }

    public static ManagementThread getInstance() {
        return instance;
    }
}