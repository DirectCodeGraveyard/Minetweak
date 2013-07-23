package net.minecraft.server.network.packet;

import java.util.HashMap;
import java.util.Map;

public class PacketCount {
    /**
     * If false, countPacket does nothing
     */
    public static boolean allowCounting = true;

    /**
     * A count of the total number of each packet sent grouped by IDs.
     */
    private static final Map<Number, Long> packetCountForID = new HashMap<Number, Long>();

    /**
     * A count of the total size of each packet sent grouped by IDs.
     */
    private static final Map<Number, Long> sizeCountForID = new HashMap<Number, Long>();

    /**
     * Used to make threads queue to add packets
     */
    private static final Object lock = new Object();

    public static void countPacket(int par0, long par1) {
        if (allowCounting) {
            Object var3 = lock;

            synchronized (lock) {
                if (packetCountForID.containsKey(par0)) {
                    packetCountForID.put(par0, packetCountForID.get(par0) + 1L);
                    sizeCountForID.put(par0, sizeCountForID.get(par0) + par1);
                } else {
                    packetCountForID.put(par0, 1L);
                    sizeCountForID.put(par0, par1);
                }
            }
        }
    }
}
