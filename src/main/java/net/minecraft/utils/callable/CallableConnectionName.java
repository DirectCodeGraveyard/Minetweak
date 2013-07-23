package net.minecraft.utils.callable;

import net.minecraft.server.network.NetServerHandler;
import net.minecraft.server.network.NetworkListenThread;

import java.util.concurrent.Callable;

public class CallableConnectionName implements Callable<String> {
    final NetServerHandler field_111201_a;

    final NetworkListenThread field_111200_b;

    public CallableConnectionName(NetworkListenThread par1NetworkListenThread, NetServerHandler par2NetServerHandler) {
        this.field_111200_b = par1NetworkListenThread;
        this.field_111201_a = par2NetServerHandler;
    }

    public String func_111199_a() {
        return this.field_111201_a.toString();
    }

    @Override
    public String call() {
        return this.func_111199_a();
    }
}
