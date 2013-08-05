package org.minetweak.network;

import net.minecraft.server.network.packet.*;

import java.util.HashMap;
import java.util.Hashtable;

public class PacketManager {
    private static Hashtable<Integer, IPacket> packets = new Hashtable<Integer, IPacket>();
    private static int idCount = 200;

    /**
     * A useless registration system that will NOT work.
     * @param packet Packet
     */
    public static void registerPacket(IPacket packet) {
        net.minecraft.server.network.packet.Packet.packetIdToClassMap.addKey(idCount++, packet);
    }
}
