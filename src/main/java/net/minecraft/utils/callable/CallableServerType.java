package net.minecraft.utils.callable;

import net.minecraft.server.DedicatedServer;

import java.util.concurrent.Callable;

public class CallableServerType implements Callable<String> {
    final DedicatedServer theDedicatedServer;

    public CallableServerType(DedicatedServer par1DedicatedServer) {
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
