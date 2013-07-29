package org.minetweak.network;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface IPacket {
    public int getPacketSize();

    public void processPacket(INetworkHandler handler);

    public void readPacketData(DataInput in) throws IOException;

    public void writePacketData(DataOutput out) throws IOException;
}
