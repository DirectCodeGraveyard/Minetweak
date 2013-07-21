package org.minetweak.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.WorldServer;

public class Location {

    private int posX;
    private int posY;
    private int posZ;

    private World world;

    public Location(int posX, int posY, int posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        world = new World(MinecraftServer.getServer().worldServerForDimension(0));
    }

    public Location(int posX, int posY, int posZ, WorldServer worldServer) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        world = new World(worldServer);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosZ() {
        return posZ;
    }

}
