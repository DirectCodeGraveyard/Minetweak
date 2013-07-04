package org.minetweak.thread;

import com.google.common.eventbus.Subscribe;
import net.minecraft.server.MinecraftServer;
import org.minetweak.event.server.ServerFinishedStartupEvent;

public class ManagementThread extends Thread {
    @Override
    public void run() {
        while (true) {

            loadBans();
        }
    }

    public void loadBans() {
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().loadBanList();
        MinecraftServer.getServer().getConfigurationManager().getBannedIPs().loadBanList();
    }

    @Subscribe
    public void serverReadyCallback(ServerFinishedStartupEvent event) {
        this.start();
    }
}