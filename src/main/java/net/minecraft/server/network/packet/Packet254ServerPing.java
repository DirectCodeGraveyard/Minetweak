package net.minecraft.server.network.packet;

import org.minetweak.network.INetworkHandler;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet254ServerPing extends Packet {
    private static final int field_140051_d = (new Packet250CustomPayload()).getPacketId();

    /**
     * Always 1, unless readByte threw an exception.
     */
    public int readSuccessfully;
    public String field_140052_b;
    public int field_140053_c;

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    @Override
    public void readPacketData(DataInput par1DataInput) throws IOException {
        try {
            par1DataInput.readByte();
            par1DataInput.readByte();
            readString(par1DataInput, 255);
            par1DataInput.readShort();
            this.readSuccessfully = par1DataInput.readByte();

            if (this.readSuccessfully >= 73) {
                this.field_140052_b = readString(par1DataInput, 255);
                this.field_140053_c = par1DataInput.readInt();
            }
        } catch (Throwable var3) {
            this.readSuccessfully = 0;
            this.field_140052_b = "";
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    @Override
    public void writePacketData(DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeByte(1);
        par1DataOutput.writeByte(field_140051_d);
        writeString("MC|PingHost", par1DataOutput);
        par1DataOutput.writeShort(3 + 2 * this.field_140052_b.length() + 4);
        par1DataOutput.writeByte(this.readSuccessfully);
        writeString(this.field_140052_b, par1DataOutput);
        par1DataOutput.writeInt(this.field_140053_c);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    @Override
    public void processPacket(INetworkHandler par1NetHandler) {
        par1NetHandler.handleServerPing(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    @Override
    public int getPacketSize() {
        return 3 + this.field_140052_b.length() * 2 + 4;
    }

    public boolean func_140050_d() {
        return this.readSuccessfully == 0;
    }
}
