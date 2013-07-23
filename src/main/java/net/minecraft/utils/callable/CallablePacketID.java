package net.minecraft.utils.callable;

import net.minecraft.server.network.NetServerHandler;
import net.minecraft.server.network.packet.Packet;

import java.util.concurrent.Callable;

public class CallablePacketID implements Callable<String> {
    final Packet thePacket;

    final NetServerHandler theNetServerHandler;

    public CallablePacketID(NetServerHandler par1NetServerHandler, Packet par2Packet) {
        this.theNetServerHandler = par1NetServerHandler;
        this.thePacket = par2Packet;
    }

    public String callPacketID() {
        return String.valueOf(this.thePacket.getPacketId());
    }

    @Override
    public String call() {
        return this.callPacketID();
    }
}
