package net.minecraft.utils.callable;

import net.minecraft.server.DedicatedServer;

import java.util.concurrent.Callable;

public class CallableType implements Callable<String> {
    /**
     * Reference to the DecitatedServer object.
     */
    final DedicatedServer theDecitatedServer;

    public CallableType(DedicatedServer par1DedicatedServer) {
        this.theDecitatedServer = par1DedicatedServer;
    }

    public String getType() {
        String var1 = this.theDecitatedServer.getServerModName();
        return !var1.equals("vanilla") ? "Definitely; Server brand changed to \'" + var1 + "\'" : "Unknown (can\'t tell)";
    }

    @Override
    public String call() {
        return this.getType();
    }
}
