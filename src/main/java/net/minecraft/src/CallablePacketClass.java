package net.minecraft.src;

import java.util.concurrent.Callable;

class CallablePacketClass implements Callable<String> {
    final Packet thePacket;

    final NetServerHandler theNetServerHandler;

    CallablePacketClass(NetServerHandler par1NetServerHandler, Packet par2Packet) {
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
