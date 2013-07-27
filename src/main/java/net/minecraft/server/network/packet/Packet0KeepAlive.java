package net.minecraft.server.network.packet;

import net.minecraft.server.network.NetHandler;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet0KeepAlive extends Packet {
    public int randomId;

    public Packet0KeepAlive() {
    }

    public Packet0KeepAlive(int par1) {
        this.randomId = par1;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    @Override
    public void processPacket(NetHandler par1NetHandler) {
        par1NetHandler.handleKeepAlive(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    @Override
    public void readPacketData(DataInput par1DataInput) throws IOException {
        this.randomId = par1DataInput.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    @Override
    public void writePacketData(DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeInt(this.randomId);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    @Override
    public int getPacketSize() {
        return 4;
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

    /**
     * If this returns true, the packet may be processed on any thread; otherwise it is queued for the main thread to
     * handle.
     */
    @Override
    public boolean canProcessAsync() {
        return true;
    }
}
