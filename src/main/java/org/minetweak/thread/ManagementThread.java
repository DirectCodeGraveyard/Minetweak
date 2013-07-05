package org.minetweak.thread;

import com.google.common.eventbus.Subscribe;
import net.minecraft.server.MinecraftServer;
import org.minetweak.Minetweak;
import org.minetweak.event.server.ServerFinishedStartupEvent;

public class ManagementThread extends Thread {
    @Override
    public void run() {
        while (true) {
            loadBans();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Minetweak.info("Stopping Management Thread");
                break;
            }
        }
    }

    public void loadBans() {
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().loadBanList();
        MinecraftServer.getServer().getConfigurationManager().getBannedIPs().loadBanList();
    }

    @Subscribe
    public void serverReadyCallback(ServerFinishedStartupEvent event) {
        Minetweak.info("Starting Management Thread");
        this.start();
    }
}