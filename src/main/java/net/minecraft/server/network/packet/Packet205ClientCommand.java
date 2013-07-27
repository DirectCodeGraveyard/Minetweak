package net.minecraft.server.network.packet;

import net.minecraft.server.network.NetHandler;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet205ClientCommand extends Packet {
    /**
     * 0 sent to a netLoginHandler starts the server, 1 sent to NetServerHandler forces a respawn
     */
    public int forceRespawn;

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    @Override
    public void readPacketData(DataInput par1DataInput) throws IOException {
        this.forceRespawn = par1DataInput.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    @Override
    public void writePacketData(DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeByte(this.forceRespawn & 255);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    @Override
    public void processPacket(NetHandler par1NetHandler) {
        par1NetHandler.handleClientCommand(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    @Override
    public int getPacketSize() {
        return 1;
    }
}
