package org.minetweak.thread;

import com.google.common.eventbus.Subscribe;
import net.minecraft.server.MinecraftServer;
import org.minetweak.Minetweak;
import org.minetweak.event.server.ServerFinishedStartupEvent;
import org.minetweak.permissions.PermissionsLoader;

public class ManagementThread extends Thread {
    @Override
    public void run() {
        while (true) {
            loadBans();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Minetweak.info("Stopping Management Thread");
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

    @Subscribe
    public void serverReadyCallback(ServerFinishedStartupEvent event) {
        Minetweak.info("Starting Management Thread");
        this.start();
    }
}