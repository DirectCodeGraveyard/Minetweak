package net.minecraft.server.network.packet;

import net.minecraft.server.network.NetHandler;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet255KickDisconnect extends Packet {
    /**
     * Displayed to the client when the connection terminates.
     */
    public String reason;

    public Packet255KickDisconnect() {
    }

    public Packet255KickDisconnect(String par1Str) {
        this.reason = par1Str;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    @Override
    public void readPacketData(DataInput par1DataInput) throws IOException {
        this.reason = readString(par1DataInput, 256);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    @Override
    public void writePacketData(DataOutput par1DataOutput) throws IOException {
        writeString(this.reason, par1DataOutput);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    @Override
    public void processPacket(NetHandler par1NetHandler) {
        par1NetHandler.handleKickDisconnect(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    @Override
    public int getPacketSize() {
        return this.reason.length();
    }

    /**
     * only false for the abstract Packet class, all real packets return true
     */
    @Override
    public boolean isRealPacket() {
        return true;
    }

    /**
     * eg return packet30entity.entityId == entityId; WARNING : will throw if you compare a packet to a different packet
     * class
     */
    @Override
    public boolean containsSameEntityIDAs(Packet par1Packet) {
        return true;
    }
}
