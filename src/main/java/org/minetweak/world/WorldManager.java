package org.minetweak.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import java.util.Hashtable;

public class WorldManager {
    private static Hashtable<Integer, WorldServer> worldServers = new Hashtable<Integer, WorldServer>();
    private static int worldServerCount = -1;

    public static void addWorldServer(WorldServer server) {
        worldServers.put(worldServerCount++, server);
        MinecraftServer.getServer().worldServers = worldServers.values().toArray(new WorldServer[worldServers.size()]);
    }

    public static World getWorld(int id) {
        WorldServer server = worldServers.get(id);
        if (server == null) {
            return null;
        }
        return server.getWorld();
    }
}
