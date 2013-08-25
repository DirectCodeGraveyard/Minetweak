package net.minecraft.src;

import net.minecraft.server.DedicatedServer;

public final class ThreadStopDedicatedServer extends Thread {
    final DedicatedServer dedicatedServer;

    public ThreadStopDedicatedServer(DedicatedServer par1DedicatedServer) {
        this.dedicatedServer = par1DedicatedServer;
    }

    public void run() {
        if (dedicatedServer != null) {
            this.dedicatedServer.stopServer();
        }
    }
}