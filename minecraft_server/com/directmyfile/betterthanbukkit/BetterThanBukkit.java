package com.directmyfile.betterthanbukkit;

import net.minecraft.server.MinecraftServer;

public class BetterThanBukkit {

    private static String minecraftVersion = "1.5.2";
    private static String serverVersion = "0.0.1";

    private static boolean isServerDoneLoading = false;

    public static void main(String[] args) {
        System.out.println("Success is very tasty.");
        System.out.println("BetterThanMinecraft v" + getServerVersion() + " using Minecraft v" + getMinecraftVersion());
        MinecraftServer.main(args);
    }

    public static String getMinecraftVersion() {
        return minecraftVersion;
    }

    public static String getServerVersion() {
        return serverVersion;
    }

    public static boolean isServerDoneLoading() {
        return isServerDoneLoading;
    }

    public static void setServerDoneLoading() {
        isServerDoneLoading = true;
    }

}
