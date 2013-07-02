package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Packet44UpdateAttributes extends Packet
{
    private int field_111005_a;
    private final Map field_111004_b = new HashMap();

    public Packet44UpdateAttributes() {}

    public Packet44UpdateAttributes(int par1, Collection par2Collection)
    {
        this.field_111005_a = par1;
        Iterator var3 = par2Collection.iterator();

        while (var3.hasNext())
        {
            AttributeInstance var4 = (AttributeInstance)var3.next();
            this.field_111004_b.put(var4.func_111123_a().func_111108_a(), Double.valueOf(var4.func_111126_e()));
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInput par1DataInput) throws IOException
    {
        this.field_111005_a = par1DataInput.readInt();
        int var2 = par1DataInput.readInt();

        for (int var3 = 0; var3 < var2; ++var3)
        {
            this.field_111004_b.put(readString(par1DataInput, 64), Double.valueOf(par1DataInput.readDouble()));
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutput par1DataOutput) throws IOException
    {
        par1DataOutput.writeInt(this.field_111005_a);
        par1DataOutput.writeInt(this.field_111004_b.size());
        Iterator var2 = this.field_111004_b.entrySet().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();
            writeString((String)var3.getKey(), par1DataOutput);
            par1DataOutput.writeDouble(((Double)var3.getValue()).doubleValue());
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_110773_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 8 + this.field_111004_b.size() * 24;
    }
}
