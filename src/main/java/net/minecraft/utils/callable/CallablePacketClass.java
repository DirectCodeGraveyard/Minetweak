package net.minecraft.utils.callable;

import net.minecraft.server.network.NetServerHandler;
import net.minecraft.server.network.packet.Packet;

import java.util.concurrent.Callable;

public class CallablePacketClass implements Callable<String> {
    final Packet thePacket;

    final NetServerHandler theNetServerHandler;

    public CallablePacketClass(NetServerHandler par1NetServerHandler, Packet par2Packet) {
        this.theNetServerHandler = par1NetServerHandler;
        this.thePacket = par2Packet;
    }

    public String getPacketClass() {
        return this.thePacket.getClass().getCanonicalName();
    }

    @Override
    public String call() {
        return this.getPacketClass();
    }
}
