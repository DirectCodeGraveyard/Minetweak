package net.minecraft.server.network.packet;

import net.minecraft.src.CryptManager;
import org.minetweak.network.INetworkHandler;

import javax.crypto.SecretKey;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.PrivateKey;

public class Packet252SharedKey extends Packet {
    private byte[] sharedSecret = new byte[0];
    private byte[] verifyToken = new byte[0];

    /**
     * Secret AES key decrypted from sharedSecret via the server's private RSA key
     */
    private SecretKey sharedKey;

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInput par1DataInput) throws IOException {
        this.sharedSecret = readBytesFromStream(par1DataInput);
        this.verifyToken = readBytesFromStream(par1DataInput);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutput par1DataOutput) throws IOException {
        writeByteArray(par1DataOutput, this.sharedSecret);
        writeByteArray(par1DataOutput, this.verifyToken);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetworkHandler par1NetHandler) {
        par1NetHandler.handleSharedKey(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize() {
        return 2 + this.sharedSecret.length + 2 + this.verifyToken.length;
    }

    /**
     * Return secretKey, decrypting it from the sharedSecret byte array if needed
     */
    public SecretKey getSharedKey(PrivateKey par1PrivateKey) {
        return par1PrivateKey == null ? this.sharedKey : (this.sharedKey = CryptManager.decryptSharedKey(par1PrivateKey, this.sharedSecret));
    }

    /**
     * Return the secret AES sharedKey (used by client only)
     */
    public SecretKey getSharedKey() {
        return this.getSharedKey(null);
    }

    /**
     * Return verifyToken, decrypting it with the server's RSA private key
     */
    public byte[] getVerifyToken(PrivateKey par1PrivateKey) {
        return par1PrivateKey == null ? this.verifyToken : CryptManager.decryptData(par1PrivateKey, this.verifyToken);
    }
}
