package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableServerType implements Callable<String> {
    final DedicatedServer theDedicatedServer;

    CallableServerType(DedicatedServer par1DedicatedServer) {
        this.theDedicatedServer = par1DedicatedServer;
    }

    public String callServerType() {
        return "Dedicated Server (map_server.txt)";
    }

    @Override
    public String call() {
        return this.callServerType();
    }
}
