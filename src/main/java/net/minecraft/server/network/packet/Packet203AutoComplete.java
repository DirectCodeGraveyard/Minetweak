package net.minecraft.server.network.packet;

import org.apache.commons.lang3.StringUtils;
import org.minetweak.network.INetworkHandler;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet203AutoComplete extends Packet {
    /**
     * Sent by the client containing the text to be auto-completed. Sent by the server with possible completions
     * separated by null (two bytes in UTF-16)
     */
    private String text;

    public Packet203AutoComplete() {
    }

    public Packet203AutoComplete(String text) {
        this.text = text;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    @Override
    public void readPacketData(DataInput input) throws IOException {
        this.text = readString(input, 32767);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    @Override
    public void writePacketData(DataOutput output) throws IOException {
        writeString(StringUtils.substring(this.text, 0, 32767), output);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    @Override
    public void processPacket(INetworkHandler netHandler) {
        netHandler.handleAutoComplete(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    @Override
    public int getPacketSize() {
        return 2 + this.text.length() * 2;
    }

    public String getText() {
        return this.text;
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
    public boolean containsSameEntityIDAs(Packet packet) {
        return true;
    }
}
