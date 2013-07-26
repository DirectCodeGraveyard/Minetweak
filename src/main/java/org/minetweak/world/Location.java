package org.minetweak.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class Location {

    private int posX;
    private int posY;
    private int posZ;

    private World world;

    public Location(int posX, int posY, int posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.world = MinecraftServer.getServer().worldServerForDimension(0).getWorld();
    }

    public Location(int posX, int posY, int posZ, WorldServer worldServer) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.world = new World(worldServer);
    }

    public Location(int posX, int posY, int posZ, World world) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.world = world;
    }

    /**
     * Gets this locations X Position
     *
     * @return x position
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Gets this locations Y Position
     *
     * @return y position
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Gets this locations Z position
     *
     * @return z position
     */
    public int getPosZ() {
        return posZ;
    }

    /**
     * Gets the World where this location is
     *
     * @return world
     */
    public World getWorld() {
        return world;
    }
}
