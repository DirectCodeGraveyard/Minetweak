package net.minecraft.server.network.packet;

import net.minecraft.entity.attribute.AttributeInstance;
import net.minecraft.entity.attribute.AttributeModifier;
import org.minetweak.network.INetworkHandler;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

public class Packet44UpdateAttributes extends Packet {
    private int field_111005_a;
    private final List<Packet44UpdateAttributesSnapshot> field_111004_b = new ArrayList<Packet44UpdateAttributesSnapshot>();

    public Packet44UpdateAttributes() {
    }

    public Packet44UpdateAttributes(int par1, Collection par2Collection) {
        this.field_111005_a = par1;

        for (Object aPar2Collection : par2Collection) {
            AttributeInstance var4 = (AttributeInstance) aPar2Collection;
            this.field_111004_b.add(new Packet44UpdateAttributesSnapshot(this, var4.func_111123_a().func_111108_a(), var4.func_111125_b(), var4.func_111122_c()));
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInput par1DataInput) throws IOException {
        this.field_111005_a = par1DataInput.readInt();
        int var2 = par1DataInput.readInt();

        for (int var3 = 0; var3 < var2; ++var3) {
            String var4 = readString(par1DataInput, 64);
            double var5 = par1DataInput.readDouble();
            ArrayList<AttributeModifier> var7 = new ArrayList<AttributeModifier>();
            short var8 = par1DataInput.readShort();

            for (int var9 = 0; var9 < var8; ++var9) {
                UUID var10 = new UUID(par1DataInput.readLong(), par1DataInput.readLong());
                var7.add(new AttributeModifier(var10, "Unknown synced attribute modifier", par1DataInput.readDouble(), par1DataInput.readByte()));
            }

            this.field_111004_b.add(new Packet44UpdateAttributesSnapshot(this, var4, var5, var7));
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutput par1DataOutput) throws IOException {
        par1DataOutput.writeInt(this.field_111005_a);
        par1DataOutput.writeInt(this.field_111004_b.size());
        Iterator<Packet44UpdateAttributesSnapshot> var2 = this.field_111004_b.iterator();

        while (var2.hasNext()) {
            Packet44UpdateAttributesSnapshot var3 = var2.next();
            writeString(var3.func_142040_a(), par1DataOutput);
            par1DataOutput.writeDouble(var3.func_142041_b());
            par1DataOutput.writeShort(var3.func_142039_c().size());

            for (Object o : var3.func_142039_c()) {
                AttributeModifier var5 = (AttributeModifier) o;
                par1DataOutput.writeLong(var5.func_111167_a().getMostSignificantBits());
                par1DataOutput.writeLong(var5.func_111167_a().getLeastSignificantBits());
                par1DataOutput.writeDouble(var5.func_111164_d());
                par1DataOutput.writeByte(var5.func_111169_c());
            }
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetworkHandler par1NetHandler) {
        par1NetHandler.handleUpdateAttributes(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize() {
        return 8 + this.field_111004_b.size() * 24;
    }
}
