package org.minetweak.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class Location {

    private double posX;
    private double posY;
    private double posZ;

    private World world;

    public Location(double posX, double posY, double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.world = MinecraftServer.getServer().worldServerForDimension(0).getWorld();
    }

    public Location(double posX, double posY, double posZ, WorldServer worldServer) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.world = new World(worldServer);
    }

    public Location(double posX, double posY, double posZ, World world) {
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
    public double getPosX() {
        return posX;
    }

    /**
     * Gets this locations Y Position
     *
     * @return y position
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Gets this locations Z position
     *
     * @return z position
     */
    public double getPosZ() {
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

    /**
     * Gets this locations X position
     * @return integer of X position
     */
    public int getIntX() {
        return (int) posX;
    }

    /**
     * Gets this locations Y position
     * @return integer of Y position
     */
    public int getIntY() {
        return (int) posY;
    }

    /**
     * Gets this locations Z position
     * @return integer of Z position
     */
    public int getIntZ() {
        return (int) posZ;
    }
}
